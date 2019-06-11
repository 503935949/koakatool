package com.supercode.koakatool.platform.base.domain;

import java.util.List;


/**   
*    
* 项目名称：yueqingRMS   
* 类名称：BaseDao   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 上午10:41:40   
* 修改人：   
* 修改时间：2017年7月18日 上午10:41:40   
* 修改备注：   
* @version    
*    
*/
public  interface BaseDao<T> {
    
	/**
	 * 新增
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int insert(T object)throws Exception;
	
	/**
	 * 修改 
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int updateByKey(T object)throws Exception;
	
	/**
	 * 删除 
	 * @param object 对象，传参用
	 * @return int
	 */
	public  int deleteByKey(T object)throws Exception;
	
	/**
	 * 查询  
	 * @param object 对象，传参用
	 * @return object
	 */
	public  T loadByKey(T object)throws Exception;
	
	/**
	 * 查询分页  
	 * @param object 对象，传参用
	 * @return Page
	 */
	public  List<T> findByWhereForPage(T object)throws Exception;
	
	/**
	 * 查询  
	 * @param object 对象，传参用
	 * @return List
	 */
	public  List<T> findByWhereForList(T object)throws Exception;
}
