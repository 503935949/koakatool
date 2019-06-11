package com.supercode.koakatool.attendance.domain;


import com.supercode.koakatool.platform.base.domain.BaseEntity;

import java.io.*;

/**
*    
* 项目名称：yueqingRMS   
* 类名称：OvertimeDomain
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午4:03:23   
* 修改人：   
* 修改时间：2017年7月18日 下午4:03:23   
* 修改备注：   
* @version    
*    
*/
public class OvertimeDomain extends BaseEntity {

	public String LogIdInternal;

	public String PersonalNr;

	public String username;

	public String CreateTime;

	public String DateStr;

	public String starttime;

	public String endtime;

	public String overtimeh;

	public String rownum;

	public String daysum;



	public OvertimeDomain() {
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

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public String getOvertimeh() {
		return overtimeh;
	}

	public void setOvertimeh(String overtimeh) {
		this.overtimeh = overtimeh;
	}

	public String getRownum() {
		return rownum;
	}

	public void setRownum(String rownum) {
		this.rownum = rownum;
	}

	public String getDaysum() {
		return daysum;
	}

	public void setDaysum(String daysum) {
		this.daysum = daysum;
	}

	@Override
	public String toString() {
		return "OvertimeDomain{" +
				"LogIdInternal='" + LogIdInternal + '\'' +
				", PersonalNr='" + PersonalNr + '\'' +
				", username='" + username + '\'' +
				", CreateTime='" + CreateTime + '\'' +
				", DateStr='" + DateStr + '\'' +
				", starttime='" + starttime + '\'' +
				", endtime='" + endtime + '\'' +
				", overtimeh='" + overtimeh + '\'' +
				", rownum='" + rownum + '\'' +
				", daysum='" + daysum + '\'' +
				'}';
	}

	public OvertimeDomain clone() {
		OvertimeDomain outer = null;
		try { // 将该对象序列化成流,因为写在流里的是对象的一个拷贝，而原对象仍然存在于JVM里面。所以利用这个特性可以实现对象的深拷贝
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(this);
 			// 将流序列化成对象
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			ObjectInputStream ois = new ObjectInputStream(bais);
			outer = (OvertimeDomain) ois.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return outer;
	}
}
