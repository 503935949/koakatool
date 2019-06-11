package com.supercode.koakatool.attendance.service.impl;

import com.supercode.koakatool.attendance.domain.AttendanceDao;
import com.supercode.koakatool.attendance.domain.AttendanceDomain;
import com.supercode.koakatool.attendance.service.IAttendanceService;
import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.cardinfo.service.ICardInfoService;
import com.supercode.koakatool.faceinfo.service.IFaceInfoService;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Transactional(rollbackFor=Throwable.class)
@Service
public class AttendanceServiceImpl implements IAttendanceService {

	@Autowired
	private AttendanceDao dao;

//	@Autowired
//	private UserDao userDao;

	@Autowired
	private IFaceInfoService faceInfoService;

	@Autowired
	private ICardInfoService cardInfoServiceImpl;

	/**
	 * 批量插入合并后信息
	 * @param datas 对象，传参用
	 * @return int
	 */
	@Transactional
	public void insert(List<AttendanceDomain> datas) {
		String infodata = "";
		String infoName = "";
		for(AttendanceDomain domain:datas){
			String logType = "1";
			if(infodata.equals((domain.getDateStr()))
					&&infoName.equals(domain.getPersonalNr())){
				//第二次识别 下班 2
				logType="2";
			}else{
				//第1次识别 上班 1
				logType="1";
			}
			domain.setLogtype(logType);
			infodata = domain.getDateStr();
			infoName = domain.getPersonalNr();
			//查询是否已经有 logType PersonalNr DateStr
			List<AttendanceDomain> ck = dao.checkTypeNoDate(domain);
			if(null!=ck&&ck.size()>1){
				//删掉所有
				dao.deleteTypeNoDate(domain);
			}else if(null!=ck&&ck.size()==1&&(!StringUtils.isEmpty(ck.get(0).getLogIdInternal()))){
				//有。
				if(!ck.get(0).getLogIdInternal().equals(domain.getLogIdInternal())){

					if("1".equals(domain.getLogtype()) ){
						if(Integer.parseInt(ck.get(0).getTimestamp())>Integer.parseInt(domain.getTimestamp())){
							dao.deleteTypeNoDate(ck.get(0));
						}
					} else if("2".equals(domain.getLogtype()) ){
						if(Integer.parseInt(ck.get(0).getTimestamp())<Integer.parseInt(domain.getTimestamp())){
							dao.deleteTypeNoDate(ck.get(0));
						}
					}
				}
			}
			dao.insert(domain);
			//抽取简单人员信息
			UserDomain user =  new UserDomain();
			user.setJob_number(domain.PersonalNr);
			user.setName(domain.username);

			//userDao.insert(user);
		}
	}

	/**
	 * 查询考勤导入模板信息
	 * @param object 对象，传参用
	 * @return
	 */
	@Override
	public List<AttendanceDomain> getAttendanceEvents(AttendanceQuery object) {
		AttendanceQuery query = new AttendanceQuery();
		query.setStartTime(object.getStartTime());
		query.setEndTime(object.getEndTime());
//        List<AttendanceDomain> a = this.getFaceCardEvents(query);
//        this.insert(a);

		return dao.getAttendanceEvents(object);
	}

	/**
	 * 查询考勤导入模板信息(延时打卡)
	 * @param object 对象，传参用
	 * @return
	 */
	@Override
	public List<AttendanceDomain> getPostponeEvents(AttendanceQuery object) {
		AttendanceQuery query = new AttendanceQuery();
		query.setStartTime(object.getStartTime());
		query.setEndTime(object.getEndTime());
//        List<AttendanceDomain> a = this.getFaceCardEvents(query);
//        this.insert(a);

		return dao.getPostponeEvents(object);
	}


	/**
	 * 合并查询门卡和人脸信息
	 * @param object 对象，传参用
	 * @return
	 */
	@Override
	public List<AttendanceDomain> getFaceCardEvents(AttendanceQuery object) {
		return dao.getFaceCardEvents(object);
	}

	/**
	 * 拉取两个系统最近的日志（10000条）
	 */
	public void getKoaKaInfo(){
		try{
			LogUtils.info("尝试获取最新信息：开始");
			cardInfoServiceImpl.getCardLogs();
			faceInfoService.getFaceLogs();
			LogUtils.info("尝试获取最新信息：成功");
			mergeLogs();
		}catch(Exception e){
			LogUtils.error("尝试获取最新信息：失败");
			try {
				throw e;
			} catch (ParseException e1) {
				e1.printStackTrace();
			}
		}


	}

	/**
	 * 获取最近一次合并的日期
	 * @return
	 */
	public String getLogsMaxDate(){
		return dao.getLogsMaxDate();
	}

	/**
	 * 合并已有信息
	 */
	public void mergeLogs() throws ParseException {
		LogUtils.info("尝试合并已有信息：开始" + new Date().toString());
		//查询当前合并记录的最近日期
		String logsMaxdate =  this.getLogsMaxDate();
		String startDate = null;
		String endDate = null;
		if(StringUtils.isEmpty(logsMaxdate)){
			startDate = "1970-01-01";
		}else{

			//比较当前日期
			String dateFormat = "yyyy-MM-dd";
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			int idc = 0;

			try {
				idc = DateUtil4Timeslecte.DateCompare(sdf.format(new Date()),logsMaxdate,dateFormat);
				//获取往前5天的日期，为了容错
				startDate = DateUtil4Timeslecte.getYestodays(sdf.parse(logsMaxdate),5,dateFormat);
			} catch (ParseException e) {
				e.printStackTrace();
				LogUtils.error("尝试合并已有信息失败：时间格式解析不正确" + e);
				throw e;
			}
			//idc=1 则当前时间晚于 数据最近时间   =0 则相等     <0则早于，显然不应该，除非系统时间有问题
			if(idc!=0) {
				endDate = sdf.format(new Date());
			}
		}
		AttendanceQuery query = new AttendanceQuery();
		query.setStartTime(startDate);
		query.setEndTime(endDate);
		//获取这一时间段的合并数据
		List<AttendanceDomain> a = this.getFaceCardEvents(query);
		//存入数据库
		this.insert(a);
		LogUtils.info("尝试合并已有信息：结束" + new Date().toString());
	}


}
