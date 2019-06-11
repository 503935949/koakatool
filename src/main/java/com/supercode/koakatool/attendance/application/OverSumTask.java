package com.supercode.koakatool.attendance.application;

import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.domain.OvertimeDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.query.UserQuery;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

public class OverSumTask implements Callable<List<OvertimeDomain>> {

    private AttendanceDao dao;


    private AttendanceQuery query;
    private List<String> dys;
    private UserDao userDao;

    public  final SimpleDateFormat DEF_FORMAT = new SimpleDateFormat(AttendanceAL.DEFAULT_FORMAT);

    public  final SimpleDateFormat DEF_FTIME = new SimpleDateFormat(AttendanceAL.DEFAULT_FORTIME);



    public OverSumTask(AttendanceDao dao, AttendanceQuery query, List<String> dys, UserDao userDao){
        this.dao= dao;
        this.query=query;
        this.dys=dys;
        this.userDao=userDao;

    }

    @Override
    public List<OvertimeDomain> call() throws Exception {
        LogUtils.info(Thread.currentThread().getName()+"开始处理 "+dys );
        List<OvertimeDomain> reta = new ArrayList<>();

        for(int t=0;t<dys.size();t++){
            int todaynums =  dao.countTodayPersons(dys.get(t));

            //查询dys[t] 开始的当天签到日志。遍历查询员工们的考勤
            List<UserDomain> users = userDao.findByWhereForList(new UserQuery());
            boolean workDay = false;
            if(todaynums*4>=users.size()){
                workDay = true;
            }
            for(UserDomain user:users){
                String jobid = user.getJob_number();

                AttendanceDomain todayDomain1New = null;
                query.setPersonalNr(jobid);
                query.setDateStr(dys.get(t));
                List<AttendanceDomain> attds1 = dao.getSignInsByWhere(query);//dys[t] 签到的考勤
                if(null!=attds1&&attds1.size()>0){
                    todayDomain1New = attds1.get(0);
                    //判断是否熬夜
                }else{
                    //今日没有上班
                    continue;
                }
                //获得当天的签退记录
                AttendanceDomain todayDomain2New = dao.getSignBackByWhere(jobid,dys.get(t));
                //第二天早上考勤
                AttendanceDomain todayDomain3New = null;
                String tomorrowDate = getYestodays(DEF_FORMAT.parse(dys.get(t)),
                        -1,AttendanceAL.DEFAULT_FORMAT);
                query.setDateStr(tomorrowDate);
                List<AttendanceDomain> attds = dao.getSignInsByWhere(query);//dys[t+1] 签到的考勤
                boolean isOverNight  = false;
                if(null!=attds&&attds.size()>0){
                    todayDomain3New = attds.get(0);
                    //判断是否熬夜
                    isOverNight  = DateUtil4Timeslecte.DateCompare(todayDomain3New.getTimeStr(),
                            AttendanceAL.OVER_START,AttendanceAL.DEFAULT_FORTIME)<=0;
                }
                String endtime = "";
                String starttime = todayDomain1New.getDateStr() +" "+ OverSumAL.DEFAULT_END;
                if(!workDay){
                    starttime = todayDomain1New.getDateStr() +" "+ OverSumAL.DEFAULT_START;
                }
                if(isOverNight){
                    //熬夜，计算加班时长
                    endtime = todayDomain3New.getDateStr() +" "+ todayDomain3New.getTimeStr();
                }else{
                    //没有熬夜,判断当晚有没有加班
                    if(null!=todayDomain2New){
                        //今晚有签退
                        boolean hasOvertime  = DateUtil4Timeslecte.DateCompare(todayDomain2New.getTimeStr(),
                                OverSumAL.DEFAULT_END,AttendanceAL.DEFAULT_FORTIME)==1;
                        if(hasOvertime){
                            endtime = todayDomain2New.getDateStr() +" "+ todayDomain2New.getTimeStr();
                        }
                    }
                }

                if("".equals(endtime)||null==endtime){
                    //没有签退，不用计算加班,计算今天下一个人的加班
                    continue;
                }else{
                    //计算加班工时(小时)
//                    float overTimes  =  new BigDecimal((float)(
//                            DateUtil4Timeslecte.DateDifference(endtime,
//                            starttime, AttendanceAL.DEFAULT_FORMAT +" "+
//                                            AttendanceAL.DEFAULT_FORTIME))/3600000)
//                            .setScale(3,   BigDecimal.ROUND_HALF_UP).floatValue();
                    float overTimes  =  (float)(
                            DateUtil4Timeslecte.DateDifference(endtime,
                            starttime, AttendanceAL.DEFAULT_FORMAT +" "+
                                            AttendanceAL.DEFAULT_FORTIME))/3600000;
                    DecimalFormat fnum  = new DecimalFormat("##0.00");
                    String  overTimesstr =fnum.format(overTimes);
                    //将 todayDomain1New 为基础 构建加班统计实际
                    OvertimeDomain overtimeDomain = new OvertimeDomain();

                    overtimeDomain.setLogIdInternal(todayDomain1New.getLogIdInternal());
                    overtimeDomain.setPersonalNr(todayDomain1New.getPersonalNr());
                    overtimeDomain.setUsername(todayDomain1New.getUsername());
                    overtimeDomain.setDateStr(todayDomain1New.getDateStr());
//                    SimpleDateFormat def_fdate = new SimpleDateFormat(
//                            AttendanceAL.DEFAULT_FORMAT+AttendanceAL.DEFAULT_FORTIME);
//                    Long startt = def_fdate.parse(starttime).getTime()/1000;
                    overtimeDomain.setStarttime(starttime+"");
//                    Long endt = def_fdate.parse(endtime).getTime()/1000;
                    overtimeDomain.setEndtime(endtime+"");
                    overtimeDomain.setOvertimeh(overTimesstr+"");
                    reta.add(overtimeDomain);

                }
                //判断当天  dys[t] 是否有签到:end






            }
        }
        LogUtils.info(Thread.currentThread().getName()+"完成 处理："+reta.size()+"条信息");
        return reta;
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
