package com.supercode.koakatool.business.application;



import java.util.List;

import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.query.UserQuery;
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
public class UserAL extends BaseAL<UserDomain> {

	@Autowired
	private UserDao userDao;

	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public int insert(UserDomain object) {
		return userDao.insert(object);
	}

	/**
	 * 修改
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int updateByKey(UserDomain object) {
		return userDao.updateByKey(object);
	}

	/**
	 * 删除
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int deleteByKey(UserDomain object) {
		return userDao.deleteByKey(object);
	}

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return object
	 */
	public  UserDomain loadByKey(UserDomain object) {
		return userDao.loadByKey(object);
	}



	/**
	 * 查询
	 * @param query 对象，传参用
	 * @return List
	 */
	public List<UserDomain> findByWhereForList(UserQuery query) {
		return userDao.findByWhereForList(query);
	}


	/**
	 * @return userDao
	 */
	public UserDao getUserDao() {
		return userDao;
	}

	/**
	 * @param userDao
	 */
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}





}
