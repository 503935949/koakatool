package com.supercode.koakatool.qywxworkflow.domain;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

public class SPExcelEntity extends BaseRowModel {

    @ExcelProperty(value = "序号" ,index = 0)
    private String sp_num;

    @ExcelProperty(value = "申请人",index = 1)
    private String apply_name;

    @ExcelProperty(value = "申请人部门",index = 2)
    private String apply_org;

    @ExcelProperty(value = "开始日期",index = 3,format = "yyyy/MM/dd")
    private String begin_day;

    @ExcelProperty(value = "开始时间",index = 4,format = "hh:mm:ss")
    private String begin_time;

    @ExcelProperty(value = "结束日期",index = 5,format = "yyyy/MM/dd")
    private String end_day;

    @ExcelProperty(value = "结束时间",index = 6,format = "hh:mm:ss")
    private String end_time;

    @ExcelProperty(value = "请假类型",index = 7)
    private String sp_qj_type;

    @ExcelProperty(value = "流程类型",index = 8)
    private String sp_type;

    public String getSp_num() {
        return sp_num;
    }

    public void setSp_num(String sp_num) {
        this.sp_num = sp_num;
    }

    public String getApply_name() {
        return apply_name;
    }

    public void setApply_name(String apply_name) {
        this.apply_name = apply_name;
    }

    public String getApply_org() {
        return apply_org;
    }

    public void setApply_org(String apply_org) {
        this.apply_org = apply_org;
    }

    public String getBegin_day() {
        return begin_day;
    }

    public void setBegin_day(String begin_day) {
        this.begin_day = begin_day;
    }

    public String getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(String begin_time) {
        this.begin_time = begin_time;
    }

    public String getEnd_day() {
        return end_day;
    }

    public void setEnd_day(String end_day) {
        this.end_day = end_day;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getSp_qj_type() {
        return sp_qj_type;
    }

    public void setSp_qj_type(String sp_qj_type) {
        this.sp_qj_type = sp_qj_type;
    }

    public String getSp_type() {
        return sp_type;
    }

    public void setSp_type(String sp_type) {
        this.sp_type = sp_type;
    }
}
