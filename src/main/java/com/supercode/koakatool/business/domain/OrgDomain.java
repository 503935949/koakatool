package com.supercode.koakatool.business.domain;


import com.supercode.koakatool.platform.base.domain.BaseEntity;

/**
*    
* 项目名称：yueqingRMS   
* 类名称：OrgDomain   
* 类描述：   
* 创建人：林曌   
* 创建时间：2017年7月18日 下午4:03:23   
* 修改人：   
* 修改时间：2017年7月18日 下午4:03:23   
* 修改备注：   
* @version    
*    
*/
public class OrgDomain extends BaseEntity {


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 621034699214581576L;

	public OrgDomain() {
	}

	public OrgDomain(String org_id) {
		this.org_id = org_id;
	}

	public OrgDomain(String org_id, String name) {
		this.org_id = org_id;
		this.name = name;
	}

	/**
	 *  USER_ID 用户ID
	 */
	private String org_id;
	/**
	 *  USER_NAME 用户姓名
	 */
	private String name;

	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

	public String getOrg_id() {
		return org_id;
	}

	public OrgDomain setOrg_id(String org_id) {
		this.org_id = org_id;
		return this;
	}

	public String getName() {
		return name;
	}

	public OrgDomain setName(String name) {
		this.name = name;
		return this;
	}

	@Override
	public String toString() {
		return "OrgDomain{" +
				"org_id='" + org_id + '\'' +
				", name='" + name + '\'' +
				'}';
	}
}
