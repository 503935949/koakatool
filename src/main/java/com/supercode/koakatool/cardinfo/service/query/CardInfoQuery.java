package com.supercode.koakatool.cardinfo.service.query;



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
public class CardInfoQuery extends  RichQuery {


	public String startTime;

	public String endTime;

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
