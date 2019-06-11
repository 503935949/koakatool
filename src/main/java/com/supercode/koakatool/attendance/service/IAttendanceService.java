package com.supercode.koakatool.attendance.service;

import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;

import java.text.ParseException;
import java.util.List;

public interface IAttendanceService {

    /**
     * 新增
     * @param datas 对象，传参用
     * @return int
     */
    public void insert(List<AttendanceDomain> datas);

    /**
     * 查询
     * @param object 对象，传参用
     * @return List
     */
    public List<AttendanceDomain> getAttendanceEvents(AttendanceQuery object);

    /**
     * 查询 （延时打卡）
     * @param object 对象，传参用
     * @return List
     */
    public List<AttendanceDomain> getPostponeEvents(AttendanceQuery object);

    public  List<AttendanceDomain> getFaceCardEvents(AttendanceQuery object);

    public void getKoaKaInfo();

    public String getLogsMaxDate();

    public void mergeLogs() throws ParseException;

}
