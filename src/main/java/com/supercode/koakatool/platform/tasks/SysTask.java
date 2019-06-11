package com.supercode.koakatool.platform.tasks;/**
 * @Author: zhao.lin
 * @Date: 2018/10/12 10:43
 */

import com.supercode.koakatool.attendance.service.IAttendanceService;
import com.supercode.koakatool.cardinfo.service.ICardInfoService;
import com.supercode.koakatool.faceinfo.domain.FaceInfoDomain;
import com.supercode.koakatool.faceinfo.service.IFaceInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

/**
 * @Author: zhao.lin
 * @Date: 2018/10/12 10:43
 */
@Component
public class SysTask {

    private Logger logger = LoggerFactory.getLogger(SysTask.class);

    @Autowired
    private IFaceInfoService faceInfoService;

    @Autowired
    private ICardInfoService cardInfoService;

    @Autowired
    private IAttendanceService attendanceService;


    @Scheduled(cron = "0 0 22 * * ? ")
    public void task1() throws ParseException {

        try{
            logger.info("task1-串行定时任务:拉取人脸系统信息：开始"+new Date().toString());
            List<FaceInfoDomain> ls = faceInfoService.getFaceLogsInfo();
            faceInfoService.insertFaceInfo(ls);
            logger.info("task1-串行定时任务:拉取人脸系统信息：结束"+new Date().toString());
        }catch(Exception e){
            logger.error("task1-串行定时任务:拉取人脸系统信息：失败"+new Date().toString());
            logger.error(e.toString());
        }



        try {
            logger.info("task2-串行定时任务:拉取门卡系统信息：开始" + new Date().toString());
            cardInfoService.getCardLogs();
            logger.info("task2-串行定时任务:拉取门卡系统信息：结束" + new Date().toString());
        }catch(Exception e1){
            logger.error("task2-串行定时任务:拉取门卡系统信息：失败"+new Date().toString());
            logger.error(e1.toString());
        }


        //查询当前合并记录的最近日期
        attendanceService.mergeLogs();


    }
    //@Scheduled(cron = "0 0/2 * * * ?")
    //@Scheduled(fixedDelayString = "${jobs.fixedDelay}")
//    @Scheduled(fixedDelayString = "20000")
//    public void task2(){
//        logger.info("task2-串行定时任务2"+new Date().toString());
//    }

//    @Scheduled(fixedDelay = 10000)
//    public void task3(){
//        logger.info("task3-串行定时任务3"+new Date().toString());
//    }


    //@Scheduled所支持的参数：
    //1.cron：cron表达式，指定任务在特定时间执行；
    //2.fixedDelay：表示上一次任务执行完成后多久再次执行，参数类型为long，单位ms；
    //3.fixedDelayString：与fixedDelay含义一样，只是参数类型变为String；
    //4.fixedRate：表示按一定的频率执行任务，参数类型为long，单位ms；
    //5.fixedRateString: 与fixedRate的含义一样，只是将参数类型变为String；
    //6.initialDelay：表示延迟多久再第一次执行任务，参数类型为long，单位ms；
    //7.initialDelayString：与initialDelay的含义一样，只是将参数类型变为String；
    //8.zone：时区，默认为当前时区，一般没有用到。


    //每隔5秒执行一次：*/5 * * * * ?
    //每隔1分钟执行一次：0 */1 * * * ?
    //每天23点执行一次：0 0 23 * * ?
    //每天凌晨1点执行一次：0 0 1 * * ?
    //每月1号凌晨1点执行一次：0 0 1 1 * ?
    //每月最后一天23点执行一次：0 0 23 L * ?
    //每周星期天凌晨1点实行一次：0 0 1 ? * L
    //在26分、29分、33分执行一次：0 26,29,33 * * * ?
    //每天的0点、13点、18点、21点都执行一次：0 0 0,13,18,21 * * ?

}
