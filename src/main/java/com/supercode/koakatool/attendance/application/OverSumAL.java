package com.supercode.koakatool.attendance.application;

import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.OvertimeDao;
import com.supercode.koakatool.attendance.domain.OvertimeDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

@Component
public class OverSumAL {

    public static final String DEFAULT_START = "08:30:00";

    public static final String DEFAULT_END = "17:30:00";

    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_FORTIME = "HH:mm:ss";

    @Autowired
    private AttendanceDao dao;

    @Autowired
    private OvertimeDao overdao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private OvertimeDao overtimeDao;

    /**
     * 处理加班数据
     * @param query
     * @return
     */
    public List<OvertimeDomain> getOverSumInfos(AttendanceQuery query) throws ParseException {
        LogUtils.info("批量处理延时打卡信息：开始");
        Long d1 = new Date().getTime();
        List<OvertimeDomain> ret = new ArrayList<>();



        int nThreads = 31;
        //清理延时打卡计算表
        overdao.deleteOvertimeByDateStr(query);
        String startDate =query.getStartTime(),endDate=query.getEndTime();
        //计算查询考勤日期
        List<String> dys =  DateUtil4Timeslecte.getDateList(startDate,endDate, DEFAULT_FORMAT);

        ExecutorService executor = Executors.newFixedThreadPool(nThreads);
        CompletionService<List<OvertimeDomain>> completionService =
                new ExecutorCompletionService<List<OvertimeDomain>>(executor);
        try {
            for(String dy:dys){
                List<String> now = new ArrayList<>();
                now.add(dy);
                AttendanceQuery querya = new AttendanceQuery();
                completionService.submit(new OverSumTask(dao, querya, now,userDao));

            }
            for(int i =0;i<dys.size();i++){
                Future<List<OvertimeDomain>>  f= completionService.take();
                if(null!=f&&null!=f.get())
                    ret.addAll(f.get());
            }
        } catch (Exception e){
            e.printStackTrace();
            System.out.println("批量加班工时计算信息失败");
        }finally {
            executor.shutdown();
        }
        for(OvertimeDomain todayDomainNew:ret){
            overdao.insert(todayDomainNew);
        }

        Long d2 = new Date().getTime();
        LogUtils.info("批量处理延时打卡信息：结束 耗时："+(d2-d1)/1000+"秒。  共："+ret.size()+"条信息！");
        return ret;
    }

    public List<OvertimeDomain>  getOvertimeEvents(AttendanceQuery query){
        return overtimeDao.getOvertimeEvents(query);
    }

    /**
     * 查询熬夜的天数排名
     * @param object
     */
    public List getOverNightInfos(AttendanceQuery object){
        return overtimeDao.getOverNightInfos(object);
    }

}
