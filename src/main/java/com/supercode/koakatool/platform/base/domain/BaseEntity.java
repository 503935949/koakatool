package com.supercode.koakatool.platform.base.domain;

import java.io.Serializable;





public class BaseEntity  implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2983894705093915419L;
	
	/**
	 * 创建时间
	 */
	private String crDate ;	
	/**
	 * 创建人ID
	 */
	private String crUser;
	/**
	 * 创建人姓名
	 */
	private String crUserName;
	/**
	 * 删除位
	 */
	private String del;
	/**
	 * 修改日期
	 */
	private String edDate;
	/**
	 * 修改人ID
	 */
	private String edUser;
	/**
	 * 修改人姓名
	 */
	private String edUserName;


	
	
	
	
	
	/**
	 * @return crDate
	 */
	public String getCrDate() {
		return crDate;
	}

	/**
	 * @param crDate
	 */
	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}

	/** 
	 * @return crUser
	 */
	public String getCrUser() {
		return crUser;
	}

	/**
	 * @param crUser
	 */
	public void setCrUser(String crUser) {
		this.crUser = crUser;
	}

	/**
	 * @return crUserName
	 */
	public String getCrUserName() {
		return crUserName;
	}

	/**
	 * @param crUserName
	 */
	public void setCrUserName(String crUserName) {
		this.crUserName = crUserName;
	}

	/**
	 * @return del
	 */
	public String getDel() {
		return del;
	}

	/**
	 * @param del
	 */
	public void setDel(String del) {
		this.del = del;
	}

	/**
	 * @return edDate
	 */
	public String getEdDate() {
		return edDate;
	}

	/**
	 * @param edDate
	 */
	public void setEdDate(String edDate) {
		this.edDate = edDate;
	}

	/**
	 * @return edUser
	 */
	public String getEdUser() {
		return edUser;
	}

	/**
	 * @param edUser
	 */
	public void setEdUser(String edUser) {
		this.edUser = edUser;
	}

	/**
	 * @return edUserName
	 */
	public String getEdUserName() {
		return edUserName;
	}

	/**
	 * @param edUserName
	 */
	public void setEdUserName(String edUserName) {
		this.edUserName = edUserName;
	}
	
	



	/**
	 * @return serialVersionUID
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
