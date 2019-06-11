package com.supercode.koakatool.business.service.impl;

import com.supercode.koakatool.business.application.OrgAL;
import com.supercode.koakatool.business.domain.OrgDomain;
import com.supercode.koakatool.business.service.IOrgService;
import com.supercode.koakatool.business.service.query.OrgQuery;
import com.supercode.koakatool.platform.base.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;









@Transactional(rollbackFor=Throwable.class)
@Service
public class OrgServiceImpl extends BaseServiceImpl implements IOrgService {

	@Autowired
	private OrgAL orgAL;
	
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int insert(OrgDomain object) {
		return orgAL.insert(object);
	}
	
	/**
	 * 修改 
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int updateByKey(OrgDomain object) {
		return orgAL.updateByKey(object);
	}
	
	/**
	 * 删除 
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int deleteByKey(OrgDomain object) {
		return orgAL.deleteByKey(object);
	}
	
	/**
	 * 查询  
	 * @param object 对象，传参用
	 * @return object
	 */
	@Transactional
	public  OrgDomain loadByKey(OrgDomain object) {
		return null;
	}
	
	/**
	 * 查询分页  
	 * @param query 对象，传参用
	 * @return Page
	 */
	@Transactional
	public Object findByWhereForPage(OrgQuery query) {
		return null;
	}
	
	
	/**
	 * list分页  
	 * @param query 对象，传参用
	 * @return Page
	 */
	@Transactional
	public  List<OrgDomain> findByWhereForList(OrgQuery query) {
		return orgAL.findByWhereForList(query);
	}
	

	
	/**
	 * @return OrgAL
	 */
	public OrgAL getBaseAL() {
		return orgAL;
	}





}
