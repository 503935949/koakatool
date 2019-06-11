package com.supercode.koakatool.faceinfo.domain;

import com.supercode.koakatool.faceinfo.service.query.FaceInfoQuery;
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
public  interface FaceInfoDao extends BaseDao<FaceInfoDomain> {
    
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(FaceInfoDomain object);

	String getLogsMaxDate();
	


	
	/**
	 * 查询  
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<FaceInfoDomain> findByWhereForList(FaceInfoQuery object);

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<FaceInfoDomain> getFaceEvents(FaceInfoQuery object);
}
