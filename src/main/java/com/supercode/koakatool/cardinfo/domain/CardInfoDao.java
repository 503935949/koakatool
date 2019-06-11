package com.supercode.koakatool.cardinfo.domain;

import com.supercode.koakatool.cardinfo.service.query.CardInfoQuery;
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
public  interface CardInfoDao extends BaseDao<CardInfoDomain> {
    
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(CardInfoDomain object);

	public List<CardInfoDomain> getCardEvents(CardInfoQuery query);

	String getLogsMaxDate();

	

}
