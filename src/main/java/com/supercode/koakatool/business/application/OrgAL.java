package com.supercode.koakatool.business.application;



import java.util.List;

import com.supercode.koakatool.business.domain.OrgDao;
import com.supercode.koakatool.business.domain.OrgDomain;
import com.supercode.koakatool.business.service.query.OrgQuery;
import com.supercode.koakatool.platform.base.application.BaseAL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;





/**   
*    
* 项目名称：
* 类名称：
* 类描述：   
* 创建人：林曌   
* 创建时间：
* 修改人：
* 修改时间：
* 修改备注：
* @version    
*    
*/
@Component
public class OrgAL extends BaseAL<OrgDomain> {

	@Autowired
	private OrgDao OrgDao;
	
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public int insert(OrgDomain object) {
		return OrgDao.insert(object);
	}
	
	/**
	 * 修改 
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int updateByKey(OrgDomain object) {
		return OrgDao.updateByKey(object);
	}
	
	/**
	 * 删除 
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int deleteByKey(OrgDomain object) {
		return OrgDao.deleteByKey(object);
	}
	
	/**
	 * 查询  
	 * @param object 对象，传参用
	 * @return object
	 */
	public  OrgDomain loadByKey(OrgDomain object) {
		return OrgDao.loadByKey(object);
	}
	


	/**
	 * 查询  
	 * @param query 对象，传参用
	 * @return List
	 */
	public List<OrgDomain> findByWhereForList(OrgQuery query) {
		return OrgDao.findByWhereForList(query);
	}
	

	/**
	 * @return OrgDao
	 */
	public OrgDao getOrgDao() {
		return OrgDao;
	}

	/**
	 * @param OrgDao
	 */
	public void setOrgDao(OrgDao OrgDao) {
		this.OrgDao = OrgDao;
	}



	
	
}
