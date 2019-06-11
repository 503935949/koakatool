package com.supercode.koakatool.cardinfo.domain;


import com.supercode.koakatool.platform.base.domain.BaseEntity;

/**
*    
* 项目名称：yueqingRMS   
* 类名称：UserDomain   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午4:03:23   
* 修改人：   
* 修改时间：2017年7月18日 下午4:03:23   
* 修改备注：   
* @version    
*    
*/
public class CardInfoDomain extends BaseEntity {

	public String LogIdInternal;

	public String PersonalNr;

	public String CardNr; //卡号

	public String DeviceAddress; //设备地址

	public String DataPointType;//  "DR" "RU" //RU 目测是刷卡行为

	public String LogDate;//日志时间

	public String EventTypeInfo;//Z0010目测是打卡行为

	public String Name;

	public String cardname;

	public String username;

	public String CreateTime;

	public String DateStr;

	public String TimeStr;

	public CardInfoDomain() {
	}

	public String getCardNr() {
		return CardNr;
	}

	public void setCardNr(String cardNr) {
		CardNr = cardNr;
	}

	public String getDeviceAddress() {
		return DeviceAddress;
	}

	public void setDeviceAddress(String deviceAddress) {
		DeviceAddress = deviceAddress;
	}

	public String getDataPointType() {
		return DataPointType;
	}

	public void setDataPointType(String dataPointType) {
		DataPointType = dataPointType;
	}

	public String getLogDate() {
		return LogDate;
	}

	public void setLogDate(String logDate) {
		LogDate = logDate;
	}

	public String getEventTypeInfo() {
		return EventTypeInfo;
	}

	public void setEventTypeInfo(String eventTypeInfo) {
		EventTypeInfo = eventTypeInfo;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getLogIdInternal() {
		return LogIdInternal;
	}

	public void setLogIdInternal(String logIdInternal) {
		LogIdInternal = logIdInternal;
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

	public String getPersonalNr() {
		return PersonalNr;
	}

	public void setPersonalNr(String personalNr) {
		PersonalNr = personalNr;
	}

	public String getCardname() {
		return cardname;
	}

	public void setCardname(String cardname) {
		this.cardname = cardname;
	}

	@Override
	public String toString() {
		return "CardInfoDomain{" +
				"LogIdInternal='" + LogIdInternal + '\'' +
				", PersonalNr='" + PersonalNr + '\'' +
				", CardNr='" + CardNr + '\'' +
				", DeviceAddress='" + DeviceAddress + '\'' +
				", DataPointType='" + DataPointType + '\'' +
				", LogDate='" + LogDate + '\'' +
				", EventTypeInfo='" + EventTypeInfo + '\'' +
				", Name='" + Name + '\'' +
				", cardname='" + cardname + '\'' +
				", username='" + username + '\'' +
				", CreateTime='" + CreateTime + '\'' +
				", DateStr='" + DateStr + '\'' +
				", TimeStr='" + TimeStr + '\'' +
				'}';
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
