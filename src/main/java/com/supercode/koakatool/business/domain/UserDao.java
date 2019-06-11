package com.supercode.koakatool.business.domain;

import com.supercode.koakatool.business.service.query.UserQuery;
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
public  interface UserDao extends BaseDao<UserDomain> {

	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(UserDomain object);

	/**
	 * 修改
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int updateByKey(UserDomain object);

	/**
	 * 删除
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int deleteByKey(UserDomain object);

	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return object
	 */
	public  UserDomain loadByKey(UserDomain object);


	/**
	 * 查询
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<UserDomain> findByWhereForList(UserQuery object);
}
