/*
 * @(#)AppConst.java
 *
 */
package com.supercode.koakatool.system;


/**   
*    
* 项目名称：yueqingRMS   
* 类名称：AppConst   
* 类描述：   系统底层程序相关的常量定义
* 创建人：林曌   
* 创建时间：2017年8月22日 下午1:27:41   
* 修改人：   
* 修改时间：2017年8月22日 下午1:27:41   
* 修改备注：   
* @version    
*    
*/
public class AppConst {
	
	// 系统配置信息
	/**数据源**/
	public final static String SOURCE_default = "ds_default";
	public final static String SOURCE_ds1 = "ds_ds1";
	public final static String SOURCE_ds2 = "ds_ds2";
	
	/** Html中空格常量 */
	public final static String HTMLBLANK = "&nbsp;";

	/** 普通空格常量 */
	public final static String BLANK = "";

	/**
	 * 格式数据时的分隔符号，默认为逗号
	 */
	public final static String FMTSPLITCHAR = ",";

	/**
	 * 默认保留两位小数
	 */
	public final static int FRACTIONNUM = 2;
	
	public final static int FRACTIONZERONUM = 0;

	/**
	 * 当提供的字符串格式化位数为此值时，对于98.0形式返回98
	 */
	public final static int NATUREFMT = -1;

	/**
	 * oracle 数据库类型
	 */
	public final static String ORACLE = "ORA";

	/**
	 * db2 数据库类型
	 */
	public final static String DB2 = "DB2";

	/**
	 * sybase 数据库类型
	 */
	public final static String SYBASE = "SYB";

	/**
	 * 日期字符串分隔字符
	 */
	public final static String DATESPLITCHAR = ",";

	/**
	 * 默认的日期格式
	 */
	public final static String DATEFMT = "yyyyMMdd";

	/**
	 * 默认的年月格式
	 */
	public final static String MONTHFTM = "yyyyMM";

	/**
	 * 默认的年的格式
	 */
	public final static String YEARFMT = "yyyy";

	/**
	 * 前端系统星期的显示定义
	 */
	public final static String[] WEEKDAYS = { "", "星期日", "星期一", "星期二", "星期三",
			"星期四", "星期五", "星期六" };

	public final static int UNKNOWN = 0;

	public final static int SUNDAY = 1;

	public final static int MONDAY = 2;

	public final static int TUESDAY = 3;

	public final static int WENSDAY = 4;

	public final static int THURSDAY = 5;

	public final static int FRIDAY = 6;

	public final static int SATURDAY = 7;

	/**
	 * 所有非正常返回的结果用此变量表示，默认为－1
	 */
	public final static int ERRORRESULT = -1;
	
	/**
	 * 所有非正常返回的结果用此变量表示，默认为0 // jcm专用,需要讨论
	 */
	public final static double RESULT = 0;
	
	/**
	 * 百分比默认输出值
	 * add by jcm
	 */
	public final static String RatePer = "0%";
	
	/**
	 * 百分比默认输出值
	 * add by jcm
	 */
	public final static String RatePerNot = "0";
	
	
	public static final int MAX_FETCH_SIZE       = 100;
	  
}