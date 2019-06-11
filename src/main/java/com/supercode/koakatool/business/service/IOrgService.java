package com.supercode.koakatool.business.service;


import com.supercode.koakatool.business.domain.OrgDomain;
import com.supercode.koakatool.business.service.query.OrgQuery;
import com.supercode.koakatool.platform.base.service.IBaseService;

import java.util.List;

/**   
*    
* 项目名称：yueqingRMS   
* 类名称：IDemoService   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午2:15:47   
* 修改人：   
* 修改时间：2017年7月18日 下午2:15:47   
* 修改备注：   
* @version    
*    
*/
public interface IOrgService extends IBaseService {



	/**
	 * @param query
	 * @return 查询orgs
	 */
	public List<OrgDomain> findByWhereForList(OrgQuery query);

	int insert(OrgDomain query);
	int updateByKey(OrgDomain query);
	int deleteByKey(OrgDomain query);
}
