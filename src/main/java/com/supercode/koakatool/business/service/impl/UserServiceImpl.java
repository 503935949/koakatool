package com.supercode.koakatool.business.service.impl;

import com.supercode.koakatool.business.application.UserAL;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.IUserService;
import com.supercode.koakatool.business.service.query.UserQuery;
import com.supercode.koakatool.platform.base.service.Impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;









@Transactional(rollbackFor=Throwable.class)
@Service
public class UserServiceImpl extends BaseServiceImpl implements IUserService {

	@Autowired
	private UserAL userAL;

	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int insert(UserDomain object) {
		return userAL.insert(object);
	}

	/**
	 * 修改
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int updateByKey(UserDomain object) {
		return userAL.updateByKey(object);
	}

	/**
	 * 删除
	 * @param object 对象，传参用
	 * @return int
	 */
	@Transactional
	public  int deleteByKey(UserDomain object) {
		return userAL.deleteByKey(object);
	}

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return object
	 */
	@Transactional
	public  UserDomain loadByKey(UserDomain object) {
		return null;
	}

	/**
	 * 查询分页
	 * @param query 对象，传参用
	 * @return Page
	 */
	@Transactional
	public Object findByWhereForPage(UserQuery query) {
		return null;
	}


	/**
	 * list分页
	 * @param query 对象，传参用
	 * @return Page
	 */
	@Transactional
	public  List<UserDomain> findByWhereForList(UserQuery query) {
		return userAL.findByWhereForList(query);
	}



	/**
	 * @return UserAL
	 */
	public UserAL getBaseAL() {
		return userAL;
	}





}
