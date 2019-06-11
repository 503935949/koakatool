package com.supercode.koakatool.attendance.application;

import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.query.UserQuery;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class PostponeTask implements Callable<List<AttendanceDomain>> {

    private AttendanceDao dao;


    private AttendanceQuery query;
    private List<String> dys;
    private UserDao userDao;

    public  final SimpleDateFormat DEF_FORMAT = new SimpleDateFormat(AttendanceAL.DEFAULT_FORMAT);

    public static final SimpleDateFormat DEF_FTIME = new SimpleDateFormat(AttendanceAL.DEFAULT_FORTIME);



    public PostponeTask(AttendanceDao dao, AttendanceQuery query, List<String> dys,UserDao userDao){
        this.dao= dao;
        this.query=query;
        this.dys=dys;
        this.userDao=userDao;

    }

    @Override
    public List<AttendanceDomain> call() throws Exception {
        LogUtils.info(Thread.currentThread().getName()+"开始处理 "+dys );
        List<AttendanceDomain> ret = new ArrayList<>();
        for(int t=0;t<dys.size();t++){
            //查询dys[t] 开始的当天签到日志。遍历查询员工们的考勤
            List<UserDomain> users = userDao.findByWhereForList(new UserQuery());
            query.setDateStr(dys.get(t));
            for(UserDomain user:users){
                String jobid = user.getJob_number();
                query.setPersonalNr(jobid);
                List<AttendanceDomain> attds = dao.getSignInsByWhere(query);//dys[t] 签到的考勤

                for(AttendanceDomain domain:attds){
                    AttendanceDomain todayDomain1New = domain;
                    AttendanceDomain todayDomain2New = dao.getSignBackByWhere(domain.getPersonalNr(),dys.get(t));
                    //查询前一天信息 domain.getPersonalNr()  dys.get(t-1) logtype=2(签到)的
                    if(null==domain.getDateStr()||"".equals(domain.getDateStr()))
                        continue;
                    String yesstartDate = getYestodays(DEF_FORMAT.parse(domain.getDateStr()),
                            1,AttendanceAL.DEFAULT_FORMAT);

                        //前一天上班
                        //查询.domain.getPersonalNr()   dys.get(t) logtype=1(签到)的
                        AttendanceDomain todayDomain1 = domain.clone();
                        //判断当天  dys[t] 是否有签到:start
                        if(null!=todayDomain1) {
                            //当天  dys[t] 是有签到,计算延时并重新赋值
                            boolean isOverNight  = DateUtil4Timeslecte.DateCompare(todayDomain1.getTimeStr(),
                                    AttendanceAL.OVER_START,AttendanceAL.DEFAULT_FORTIME)<=0;
                            boolean signBackOK  = DateUtil4Timeslecte.DateCompare(todayDomain1.getTimeStr(),
                                    AttendanceAL.OVER_BACK,AttendanceAL.DEFAULT_FORTIME)==1;
                            if(isOverNight&&signBackOK){
                                //认定dys[t-1]加班一晚，则制造今天补偿数据
                                todayDomain1New = AttendanceAL.makeDefalutInfo(todayDomain1,dys.get(t),AttendanceAL.DEFAULT_START,"1");
                                todayDomain2New = AttendanceAL.makeDefalutInfo(todayDomain1,dys.get(t),AttendanceAL.DEFAULT_END,"2");

                            }else{
                                AttendanceDomain yesDomain = dao.getSignBackByWhere(domain.getPersonalNr(),yesstartDate);
                                if(null!=yesDomain
                                        && (!StringUtils.isEmpty(yesDomain.getTimeStr()))
                                        && (!StringUtils.isEmpty(yesDomain.getTimestamp()))
                                ) {
                                    //认定正常上班，判断是否昨天加班到20.30以后
                                    boolean isOvertime = DateUtil4Timeslecte.DateCompare(yesDomain.getTimeStr(),
                                            AttendanceAL.OVER_END, AttendanceAL.DEFAULT_FORTIME) == 1;
                                    //判断是否使用延时
                                    boolean usOvertime = DateUtil4Timeslecte.DateCompare(domain.getTimeStr(),
                                            AttendanceAL.DEFAULT_START, AttendanceAL.DEFAULT_FORTIME) == 1;
                                    if ((isOverNight||isOvertime)&&usOvertime) {
                                        //时间晚于20.30则，修改 dys[t]的签到数据
                                        String endtime = yesDomain.getTimeStr();
                                        if(isOverNight){
                                            endtime = todayDomain1.getTimeStr();
                                        }
                                        //计算加班工时
                                        Long overTimes = DateUtil4Timeslecte.DateDifference(endtime,
                                                AttendanceAL.OVER_END, AttendanceAL.DEFAULT_FORTIME);
                                        Long timestamp = Long.parseLong(todayDomain1.getTimestamp());
                                        Date timeNumDate = new Date(timestamp*1000-overTimes );
                                        String changeTimestamp = timeNumDate.getTime()/1000 + "";
                                        String changeTime = DEF_FTIME.format(timeNumDate);
                                        todayDomain1New.setTimestamp(changeTimestamp);
                                        todayDomain1New.setTimeStr(changeTime);
                                    }
                                }
                            }
                        }
                        //判断当天  dys[t] 是否有签到:end


//                    }
                    //将 todayDomain1New todayDomain2New 插入延时计算表中
                    if(null!=todayDomain1New){
                        todayDomain1New.setLogtype("1");
                        ret.add(todayDomain1New);
                        //dao.insertPostpone(todayDomain1New);
                    }
                    if(null!=todayDomain2New){
                        todayDomain2New.setLogtype("2");
                        ret.add(todayDomain2New);
                        //dao.insertPostpone(todayDomain2New);
                    }
                }
            }
        }
        LogUtils.info(Thread.currentThread().getName()+"完成 处理："+ret.size()+"条信息");
        return ret;
    }

    /**
     * 获得昨天的日期
     * @param today
     * @param format
     * @return
     */
    public String getYestodays(Date today,int num,String format){
        Calendar calendar = Calendar.getInstance();
        //此时打印它获取的是系统当前时间
        // 设置到指定日期
        calendar.setTime(today);
        calendar.add(Calendar.DATE, -num);    //得到前num天
        return new SimpleDateFormat(format).format(calendar.getTime());
    }

    public static AttendanceDomain makeDefalutInfo(AttendanceDomain oldInfo,String setDateStr,String setTimeStr,String logtype){
        AttendanceDomain newInfo = oldInfo.clone();
        newInfo.setDateStr(setDateStr);
        newInfo.setTimeStr(setTimeStr);
        String timestamps  = null;
        try {
            SimpleDateFormat DEF_FDATE = new SimpleDateFormat(
                    AttendanceAL.DEFAULT_FORMAT+AttendanceAL.DEFAULT_FORTIME);
            timestamps  = DEF_FDATE.parse(setDateStr+setTimeStr).getTime()/1000+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newInfo.setTimestamp(timestamps);
        newInfo.setLogtype(logtype);
        return newInfo;
    }
}
