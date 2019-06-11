package com.supercode.koakatool.attendance.domain;

import com.supercode.TestBase;
import com.supercode.koakatool.attendance.service.IAttendanceService;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.system.LogUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class AttendanceDaoTest extends TestBase {

    @Autowired
    private AttendanceDao dao;


    @Test
    //@Commit
    public void insert() {

        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-10");
        attendanceQuery.setEndTime("2019-01-10");
        List<AttendanceDomain> a = dao.getAttendanceEvents(attendanceQuery);
        for(AttendanceDomain s:a){
            dao.insert(s);
        }

    }

    @Test
    public void getFaceCardEvents() {
        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-01");
        attendanceQuery.setEndTime("2019-02-01");
        List<AttendanceDomain> a = dao.getFaceCardEvents(attendanceQuery);
        for(AttendanceDomain s:a){
            LogUtils.info(s.toString());
        }

    }

    @Test
    public void getAttendanceEvents() {
        AttendanceQuery attendanceQuery =new AttendanceQuery();
        attendanceQuery.setStartTime("2019-01-01");
        attendanceQuery.setEndTime("2019-02-01");
        List<AttendanceDomain> a = dao.getAttendanceEvents(attendanceQuery);
        for(AttendanceDomain s:a){
            LogUtils.info(s.toString());
        }

    }
}