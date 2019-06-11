package com.supercode.koakatool.attendance.application;

import com.supercode.TestBase;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.util.List;

public class AttendanceALTest extends TestBase {

    @Autowired
    private AttendanceAL al;
    @Test
    //@Commit
    public void getPostponeInfos() throws ParseException {

        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-01");
        attendanceQuery.setEndTime("2019-01-31");
        List l= al.getPostponeInfosByTask(attendanceQuery);
        //System.out.println(l);


    }
}