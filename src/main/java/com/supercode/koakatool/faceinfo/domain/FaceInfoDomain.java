package com.supercode.koakatool.faceinfo.domain;

import com.supercode.koakatool.business.domain.UserDomain;

public class FaceInfoDomain {

    public String id;

    public String timestamp;

    public String CreateTime;

    public String DateStr;

    public String TimeStr;

    public UserDomain subject;



    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getDateStr() {
        return DateStr;
    }

    public void setDateStr(String dateStr) {
        DateStr = dateStr;
    }

    public String getTimeStr() {
        return TimeStr;
    }

    public void setTimeStr(String timeStr) {
        TimeStr = timeStr;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public UserDomain getSubject() {
        return subject;
    }

    public void setSubject(UserDomain subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "FaceInfoDomain{" +
                "id='" + id + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", CreateTime='" + CreateTime + '\'' +
                ", DateStr='" + DateStr + '\'' +
                ", TimeStr='" + TimeStr + '\'' +
                ", subject=" + subject +
                '}';
    }
}
