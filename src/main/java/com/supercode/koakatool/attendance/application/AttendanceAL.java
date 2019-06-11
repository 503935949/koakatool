package com.supercode.koakatool.attendance.application;

import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Component
@Transactional(rollbackFor=Throwable.class)
public class AttendanceAL {

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_FORTIME = "HH:mm:ss";

    public static final String ZORE = "00:00:00";



    public static final String OVER_END = "20:30:00";

    public static final String OVER_BACK = "05:00:00";

    public static final String OVER_START = "06:00:00";

    public static final String DEFAULT_START = "08:30:00";

    public static final String DEFAULT_END = "17:30:00";

    public static final SimpleDateFormat def_format = new SimpleDateFormat(DEFAULT_FORMAT);

    public static final SimpleDateFormat def_ftime = new SimpleDateFormat(DEFAULT_FORTIME);

    public static final SimpleDateFormat def_fdate = new SimpleDateFormat(DEFAULT_FORMAT+DEFAULT_FORTIME);
//    public static  Long ZORETIME = 0L;
//
//    static {
//        try {
//            ZORETIME = def_ftime.parse(ZORE).getTime();
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//    }
    
    @Autowired
    private AttendanceDao dao;

    @Autowired
    private UserDao userDao;

    @Deprecated
    public List<AttendanceDomain> getPostponeInfos(AttendanceQuery query) throws ParseException {
        LogUtils.info("批量处理延时打卡信息：开始");
        Long d1 = new Date().getTime();
        List<AttendanceDomain> ret = new ArrayList<>();
        //清理延时打卡计算表
        dao.deletePostponeByDateStr(query);
        String startDate =query.getStartTime(),endDate=query.getEndTime();

        //startDate = "2018-12-01";
        //endDate = "2019-01-31";

        //获得startDate的前一天 ， yesstartDate
        //String yesstartDate = DateUtil4Timeslecte.getYestodays(def_format.parse(startDate),1,DEFAULT_FORMAT);
        //计算查询考勤日期
        List<String> dys =  DateUtil4Timeslecte.getDateList(startDate,endDate, DEFAULT_FORMAT);
        //System.out.println(dys);

        for(int t=0;t<dys.size();t++){
            //查询dys[t] 开始的当天签到日志。遍历查询员工们的考勤
            AttendanceQuery query1 = new AttendanceQuery();
            query1.setDateStr(dys.get(t));
            List<AttendanceDomain> attds = dao.getSignInsByWhere(query1);//dys[t] 签到的考勤

            for(AttendanceDomain domain:attds){
                AttendanceDomain todayDomain1New = domain;
                AttendanceDomain todayDomain2New = dao.getSignBackByWhere(domain.getPersonalNr(),dys.get(t));
                //查询前一天信息 domain.getPersonalNr()  dys.get(t-1) logtype=2(签到)的
                String yesstartDate = DateUtil4Timeslecte.getYestodays(def_format.parse(domain.getDateStr()),
                        1,AttendanceAL.DEFAULT_FORMAT);
                AttendanceDomain yesDomain = dao.getSignBackByWhere(domain.getPersonalNr(),yesstartDate);
                if(null!=yesDomain
                        && (!StringUtils.isEmpty(yesDomain.getTimeStr()))
                        && (!StringUtils.isEmpty(yesDomain.getTimestamp()))
                ){
                    //前一天上班
                    //查询.domain.getPersonalNr()   dys.get(t) logtype=1(签到)的
                    AttendanceDomain todayDomain1 = domain.clone();
                    //判断当天  dys[t] 是否有签到:start
                    if(null!=todayDomain1) {
                        //当天  dys[t] 是有签到,计算延时并重新赋值
                        boolean isOverNight  = DateUtil4Timeslecte.DateCompare(todayDomain1.getTimeStr(),OVER_START,DEFAULT_FORTIME)<=0;
                        if(isOverNight){
                            //认定dys[t-1]加班一晚，则制造今天补偿数据
                            todayDomain1New = makeDefalutInfo(todayDomain1,dys.get(t),DEFAULT_START,"1");
                            todayDomain2New = makeDefalutInfo(todayDomain1,dys.get(t),DEFAULT_END,"2");

                        }else{
                            //认定正常上班，判断是否昨天加班到20.30以后
                            boolean isOvertime  = DateUtil4Timeslecte.DateCompare(domain.getTimeStr(),OVER_END,DEFAULT_FORTIME)==1;
                            if(isOvertime){
                                //时间晚于20.30则，修改 dys[t]的签到数据
                                //计算加班工时
                                Long overTimes = DateUtil4Timeslecte.DateDifference(domain.getTimeStr(),OVER_END,DEFAULT_FORTIME);
                                Long timestamp = Long.parseLong(todayDomain1.getTimestamp());
                                Date timeNumDate = new Date(overTimes+timestamp);
                                String changeTimestamp = timeNumDate.getTime()+"";
                                String changeTime = def_ftime.format(timeNumDate);
                                todayDomain1.setTimestamp(changeTimestamp);
                                todayDomain1.setTimeStr(changeTime);
                            }
                        }
                    }
                    //判断当天  dys[t] 是否有签到:end
                    //将 todayDomain1New todayDomain2New 插入延时计算表中
                    if(null!=todayDomain1New){
                        dao.insertPostpone(todayDomain1New);
                        ret.add(todayDomain1New);
                    }
                    if(null!=todayDomain2New){
                        dao.insertPostpone(todayDomain2New);
                        ret.add(todayDomain2New);
                    }

                }
            }
        }
        Long d2 = new Date().getTime();
        LogUtils.info("批量处理延时打卡信息：结束 耗时："+(d2-d1)/1000+"秒。  共："+ret.size()+"条信息！");
        return ret;
    }

    /**
     * 配置默认的签到记录
     * @param oldInfo
     * @param setDateStr
     * @param setTimeStr
     * @return
     */
    public static AttendanceDomain makeDefalutInfo(AttendanceDomain oldInfo,String setDateStr,String setTimeStr,String logtype){
        AttendanceDomain newInfo = oldInfo.clone();
        newInfo.setDateStr(setDateStr);
        newInfo.setTimeStr(setTimeStr);
        String timestamps  = null;
        try {
            timestamps  = def_fdate.parse(setDateStr+setTimeStr).getTime()/1000+"";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        newInfo.setTimestamp(timestamps);
        newInfo.setLogtype(logtype);
        return newInfo;
    }


    public List<AttendanceDomain> getPostponeInfosByTask(AttendanceQuery query) throws ParseException {
        LogUtils.info("批量处理延时打卡信息：开始");
        Long d1 = new Date().getTime();
        int nThreads = 31;
        List<AttendanceDomain> ret = new ArrayList<>();
//        List<Future<List<AttendanceDomain>>> futureList = new ArrayList<Future<List<AttendanceDomain>>>();
        //清理延时打卡计算表
        dao.deletePostponeByDateStr(query);
        String startDate =query.getStartTime(),endDate=query.getEndTime();
        //获得startDate的前一天 ， yesstartDate
        //String yesstartDate = DateUtil4Timeslecte.getYestodays(def_format.parse(startDate),1,DEFAULT_FORMAT);
        //计算查询考勤日期
        List<String> dys =  DateUtil4Timeslecte.getDateList(startDate,endDate, DEFAULT_FORMAT);


        // 创建一个执行任务的服务
//        final BlockingQueue<Future<List<AttendanceDomain>>> queue = new LinkedBlockingDeque<Future<List<AttendanceDomain>>>(
//                );

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        CompletionService<List<AttendanceDomain>> completionService = new ExecutorCompletionService<List<AttendanceDomain>>(executor);
//        List<PostponeTask> tls= new ArrayList<>();
        try {
            for(String dy:dys){
                List<String> now = new ArrayList<>();
                now.add(dy);
                AttendanceQuery querya = new AttendanceQuery();
                completionService.submit(new PostponeTask(dao, querya, now,userDao));

            }
//            Future<List<AttendanceDomain>> future = executor.submit(new Info(completionService,
//                    futureList,dys.size()));
//            ret=future.get();
            for(int i =0;i<dys.size();i++){
                Future<List<AttendanceDomain>>  f= completionService.take();
                if(null!=f&&null!=f.get())
                    ret.addAll(f.get());
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("批量处理延时打卡信息失败");
        }finally {
            executor.shutdown();
        }
        for(AttendanceDomain todayDomainNew:ret){
            dao.insertPostpone(todayDomainNew);
        }
        Long d2 = new Date().getTime();
        LogUtils.info("批量处理延时打卡信息：结束 耗时："+(d2-d1)/1000+"秒。  共："+ret.size()+"条信息！");

        return ret;
    }




}
