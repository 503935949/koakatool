package com.supercode.koakatool.attendance.domain;

import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.platform.base.domain.BaseDao;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**   
*    
* 项目名称：
* 类名称：
* 类描述：   
* 创建人：林曌   
* 创建时间：
* 修改人：   
* 修改时间：2
* 修改备注：   
* @version    
*    
*/
public  interface AttendanceDao extends BaseDao<AttendanceDomain> {
    
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(AttendanceDomain object);

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<AttendanceDomain> getAttendanceEvents(AttendanceQuery object);

	public  List<AttendanceDomain> getFaceCardEvents(AttendanceQuery object);

	List<AttendanceDomain> checkTypeNoDate(AttendanceDomain object);
	void deleteTypeNoDate(AttendanceDomain object);
	String getLogsMaxDate();

	void deletePostponeByDateStr(AttendanceQuery query);

	int insertPostpone(AttendanceDomain domain);

	AttendanceDomain getSignBackByWhere(@Param("PersonalNr") String PersonalNr,@Param("DateStr") String DateStr );

	List<AttendanceDomain> getSignInsByWhere(AttendanceQuery query);

	public  List<AttendanceDomain> getPostponeEvents(AttendanceQuery object);

	int countTodayPersons(@Param("DateStr") String DateStr );

}
