package com.supercode.koakatool.attendance.service.impl;

import com.supercode.TestBase;
import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.service.IAttendanceService;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;

import java.text.ParseException;
import java.util.List;

public class AttendanceServiceImplTest extends TestBase {

    @Autowired
    private AttendanceDao dao;

    @Autowired
    private IAttendanceService service;

    @Test
    //@Commit
    public void insert() {



        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-10");
        attendanceQuery.setEndTime("2019-01-10");
        List<AttendanceDomain> a = service.getFaceCardEvents(attendanceQuery);
        service.insert(a);
    }

    @Test
    //@Commit
    public void hebing() throws ParseException {

        service.mergeLogs();
    }
}