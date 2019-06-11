package com.supercode.koakatool.system;

import com.supercode.koakatool.cardinfo.domain.CardInfoDomain;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DateUtil4TimeslecteTest {

    @Test
    public void timestampDate() throws ParseException {
        String ttDate = DateUtil4Timeslecte.timestampDate("1545369412","yyyy-MM-dd hh:mm:ss");
        System.out.println(ttDate);

        List<CardInfoDomain> ret = new ArrayList<>();
        //String logsMaxdate =  DateUtil4Timeslecte.getLogsMaxDate();
        String firstDay = "2018-12-01";
//        if(null!=logsMaxdate&&!"".equals(logsMaxdate)){
//            firstDay = logsMaxdate;
//        }
        String format = "yyyy-MM-dd";
        SimpleDateFormat fat = new SimpleDateFormat(format);
        //计算相差多少天
        int daylongs = DateUtil4Timeslecte.daysBetweenDates(fat.parse(firstDay),new Date());
        List<String> dys =  DateUtil4Timeslecte.getDayStrToNowByLongs(daylongs, format);
    }


    @Test
    public void getDateList() throws ParseException {
        String ttDate = DateUtil4Timeslecte.timestampDate("1545369412","yyyy-MM-dd hh:mm:ss");
        System.out.println(ttDate);


        String format = "yyyy-MM-dd";
        String startDay = "2018-12-01";
        String endDay = "2019-01-31";


        List<String> dys =  DateUtil4Timeslecte.getDateList(startDay,endDay, format);
        System.out.println(dys);
        String  dyss=  DateUtil4Timeslecte.getDaySpansStr(31,endDay, format,true);
        LogUtils.debug(dyss);
    }

    @Test
    public void DateCompare() throws ParseException {
        String OVER_START = "12;00;00";
        //"HH;mm:ss";
        int s= DateUtil4Timeslecte.DateCompare("08:21:00","08:30:00","HH:mm:ss");
        System.out.println(s);
    }

}