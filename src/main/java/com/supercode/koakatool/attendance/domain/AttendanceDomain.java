package com.supercode.koakatool.attendance.domain;


import com.supercode.koakatool.platform.base.domain.BaseEntity;

import java.io.*;

/**
*    
* 项目名称：yueqingRMS   
* 类名称：AttendanceDomain
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午4:03:23   
* 修改人：   
* 修改时间：2017年7月18日 下午4:03:23   
* 修改备注：   
* @version    
*    
*/
public class AttendanceDomain extends BaseEntity {

	public String LogIdInternal;

	public String PersonalNr;

	public String username;

	public String CreateTime;

	public String DateStr;

	public String TimeStr;

	public String timestamp;

	public String logtype;

	public String signtype;

	public AttendanceDomain() {
	}

	public String getLogIdInternal() {
		return LogIdInternal;
	}

	public void setLogIdInternal(String logIdInternal) {
		LogIdInternal = logIdInternal;
	}

	public String getPersonalNr() {
		return PersonalNr;
	}

	public void setPersonalNr(String personalNr) {
		PersonalNr = personalNr;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

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

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getLogtype() {
        return logtype;
    }

    public void setLogtype(String logtype) {
        this.logtype = logtype;
    }

	public String getSigntype() {
		return signtype;
	}

	public void setSigntype(String signtype) {
		this.signtype = signtype;
	}

	@Override
	public String toString() {
		return "AttendanceDomain{" +
				"LogIdInternal='" + LogIdInternal + '\'' +
				", PersonalNr='" + PersonalNr + '\'' +
				", username='" + username + '\'' +
				", CreateTime='" + CreateTime + '\'' +
				", DateStr='" + DateStr + '\'' +
				", TimeStr='" + TimeStr + '\'' +
				", timestamp='" + timestamp + '\'' +
				", logtype='" + logtype + '\'' +
				", signtype='" + signtype + '\'' +
				'}';
	}

	public AttendanceDomain clone() {
		AttendanceDomain outer = null;
		try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
 			// 将流序列化成对象
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			outer = (AttendanceDomain) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return outer;
	}
}
