package com.supercode.koakatool.business.service.query;



import com.supercode.koakatool.platform.base.service.query.RichQuery;


/**
*    
* 项目名称：yueqingRMS   
* 类名称：UserDomain   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午4:03:23   
* 修改人：   
* 修改时间：2017年7月18日 下午4:03:23   
* 修改备注：   
* @version    
*    
*/
public class UserQuery extends  RichQuery {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 621034699214581576L;


	public UserQuery() {
	}

	public UserQuery(String job_number) {
		this.job_number = job_number;
	}


	public UserQuery(String job_number, String name) {
		this.job_number = job_number;
		this.name = name;
	}

	/**
	 *  USER_ID 用户ID
	 */
	private String job_number;
	/**
	 *  USER_NAME 用户姓名
	 */
	private String name;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getJob_number() {
		return job_number;
	}

	public UserQuery setJob_number(String job_number) {
		this.job_number = job_number;
		return this;
	}

	public String getName() {
		return name;
	}

	public UserQuery setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "UserQuery{" +
				"job_number='" + job_number + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
