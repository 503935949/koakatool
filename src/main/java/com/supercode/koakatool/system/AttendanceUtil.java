package com.supercode.koakatool.system;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AttendanceUtil {

    public static String[] getYesAndToday(){
        String format = "yyyy-MM-dd";
        DateUtil4Timeslecte.getYestodays(new Date(),5,format);
        Date tod = new Date();
        String  today = new SimpleDateFormat(format).format(tod.getTime());
        String  yestoday = DateUtil4Timeslecte.getYestodays(tod,5,format);
        String[] ret = {today,yestoday};
        return ret;
    }



}
