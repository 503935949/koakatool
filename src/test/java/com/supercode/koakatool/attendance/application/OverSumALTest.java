package com.supercode.koakatool.attendance.application;

import com.supercode.TestBase;
import com.supercode.koakatool.attendance.domain.OvertimeDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import java.text.ParseException;
import java.util.List;

public class OverSumALTest extends TestBase {

    @Autowired
    private OverSumAL al;

    @Test
    //@Commit
    public void getOverSumInfos() {
        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-01");
        attendanceQuery.setEndTime("2019-01-31");
        List<OvertimeDomain> l = null;
        try {
            l = al.getOverSumInfos(attendanceQuery);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}