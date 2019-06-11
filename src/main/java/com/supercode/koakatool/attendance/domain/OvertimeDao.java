package com.supercode.koakatool.attendance.domain;

import com.supercode.koakatool.attendance.service.query.AttendanceQuery;
import com.supercode.koakatool.platform.base.domain.BaseDao;

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
public  interface OvertimeDao extends BaseDao<OvertimeDomain> {

	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(OvertimeDomain object);

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<OvertimeDomain> getOvertimeEvents(AttendanceQuery object);

	/**
	 * 删掉范围内的数据
	 * @param object
	 */
	void deleteOvertimeByDateStr(AttendanceQuery object);

	/**
	 * 查询熬夜的天数排名
	 * @param object
	 */
	List getOverNightInfos(AttendanceQuery object);



}
