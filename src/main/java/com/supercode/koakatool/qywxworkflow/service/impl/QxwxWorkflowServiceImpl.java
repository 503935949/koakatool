package com.supercode.koakatool.qywxworkflow.service.impl;

import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.supercode.koakatool.business.domain.OrgDomain;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.business.service.IOrgService;
import com.supercode.koakatool.business.service.IUserService;
import com.supercode.koakatool.business.service.query.OrgQuery;
import com.supercode.koakatool.business.service.query.UserQuery;
import com.supercode.koakatool.qywxworkflow.domain.QywxInfoCache;
import com.supercode.koakatool.qywxworkflow.domain.SPExcelEntity;
import com.supercode.koakatool.system.Context;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.HttpUtils;
import com.supercode.koakatool.system.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.*;

@Service
@Transactional(rollbackFor=Throwable.class)
public class QxwxWorkflowServiceImpl {

    @Autowired
    private IUserService userservice;

    @Autowired
    private IOrgService orgservice;

    @Autowired
    private HttpSession session;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private UserDao userDao;

    private static final String HTTPS = "https://";
    private static final String QYWX_CORPID = "ww9752f28617037129";
    private static final String QYWX_SECRET = "P1_CLasChP9fm9k2H3FZym2lm6T2dFZiZ1Vv-m68P0I";
    private static final String QYWX_GET_ACCESS_TOKEN  =
            "qyapi.weixin.qq.com/cgi-bin/gettoken?corpid={corpid}&corpsecret={secret}";
    private static final String QYWX_ACCESS_TOKEN  = "access_token";
    private static final String QYWX_POST_GETAPPROVALDATA  =
            "qyapi.weixin.qq.com/cgi-bin/corp/getapprovaldata?access_token={access_token}";

    private static final String QYWX_ERRCODE = "0"; //成功返回

    private static final String QYWX_TOKENMISS = "41001"; //token过期

    private static final String QYWX_PARAMSERROR = "301025"; //参数类型不正确

    private static final String[] QYWX_SPLX = {"请假","因公外出"};

    private static final String[] QYWX_SPLX_NUM = {"0","1"};

    private static final String DAYEFORMATE = "yyy/MM/dd HH:mm:ss";

    //private static final String TIMEFORMATE = "hh:MM:ss";
    private static final String[]  QYWX_QJ_REASON = {"事假","病假"};

    private static final String[]  QYWX_QJ_REASON_NUM = {"26","27"};


    /**
     * 获取最近一次合并的日期
     * @return
     */
//    public String getLogsMaxDate(){
//        return dao.getLogsMaxDate();
//    }

    /**
     * 从企业微信上拉取审批token
     * @return
     */
    public String getQywxIdentifier(){
        LogUtils.info("企业微信后台管理登录，获取API令牌"+" \n\r");

        HttpHeaders header = HttpUtils.getHttpHeader();
//        String authString = CARD_AUTHSTRING;
//        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
//        String authStringEnc = new String(authEncBytes);
//        header.add("Authorization", "Basic " + authStringEnc);
        String url = HTTPS+QYWX_GET_ACCESS_TOKEN;
        Map<String, String> map= new HashMap<>();
        map.put("corpid", QYWX_CORPID);
        map.put("secret", QYWX_SECRET);

        HttpEntity<String> formEntity = new HttpEntity<>(null, header);
        ResponseEntity<Map> result =null;
        try{
            result = restTemplate.exchange(url, HttpMethod.GET, formEntity, Map.class, map);
        }catch(Exception e){
            e.printStackTrace();
            LogUtils.error("企业微信后台管理获取API令牌失败"+e+" \n\r");
            throw e;
        }
        Map value = result.getBody();
        String errcode = value.get("errcode").toString();
        String errmsg = value.get("errmsg").toString();
        String access_token = value.get("access_token").toString();
        String expires_in = value.get("expires_in").toString();
        LogUtils.info("企业微信后台管理登录，获取API令牌成功，" +
                "access_token:"+access_token +" \n\r"+
                "有效时间:"+expires_in +"秒 \n\r"+
                "交易状态码:"+errcode +" \n\r"+
                "交易返回信息:"+errmsg +" \n\r");
        // Group group2 = JSON.parseObject(result, Group.class);
        Context.refreshTokenCache(QYWX_ACCESS_TOKEN,access_token);
        LogUtils.info("刷新企业微信登陆token");
        return access_token;
    }

    /**
     * 从企业微信上拉取审批信息
     * @return
     */
    public Map getQywxWorkInfoInfo(String starttime,String endtime,Long next_spnum){
        LogUtils.info("企业微信后台管理拉取审批信息，next_spnum："+next_spnum+" \n\r");
        if(StringUtils.isEmpty(Context.getTokenCache(QYWX_ACCESS_TOKEN))){
            getQywxIdentifier();
        }
        HttpHeaders header = HttpUtils.getHttpHeader();

        header.setContentType(MediaType.APPLICATION_JSON);
        String url = HTTPS+QYWX_POST_GETAPPROVALDATA;
        Map<String, Object> map= new HashMap<>();
        map.put("starttime",starttime);
        map.put("endtime",endtime);
        map.put("next_spnum",next_spnum);

        HttpEntity<Map> formEntity = new HttpEntity<>(map, header);
        ResponseEntity<Map> result =null;
        try{
            result = restTemplate.exchange(url, HttpMethod.POST, formEntity, Map.class ,Context.getTokenCache(QYWX_ACCESS_TOKEN));
        }catch(Exception e){
            e.printStackTrace();
            throw e;
        }
        Map value = result.getBody();
        String errcode = value.get("errcode").toString();
        //判断是否请求成功
        if( QYWX_TOKENMISS.equals(errcode)){
            LogUtils.info("企业微信后台管理拉取审批信息失败，尝试刷新token"+" \n\r");
            getQywxIdentifier();

            return getQywxWorkInfoInfo( starttime, endtime,next_spnum);
        } else if(QYWX_PARAMSERROR.equals(errcode)){
            LogUtils.info("企业微信后台管理拉取审批信息失败，参数错误，错误码:"+errcode+". \n\r");
            return null;
        } else if(QYWX_ERRCODE.equals(errcode)){
            LogUtils.info("企业微信后台管理拉取审批信息成功，next_spnum："+next_spnum+". \n\r");
            //String datas = value.get("data").toString();
            List<Map> ls = (List) value.get("data");
            //LogUtils.info(datas);
            return value;
        }
        return null;
    }

    /**
     * 从企业微信上拉取审批信息(过滤提取已审批的)
     * @return
     */
    public void pullAllInfo(String starttime,String endtime){
        LogUtils.info("企业微信后台管理拉取审批信息开始,:starttime:"+starttime+"   endtime:"+endtime+". \n\r");
        QywxInfoCache.clearCache();
        Long next_spnum = 0L;
        int count =0;
        do{
            Map infoMap = getQywxWorkInfoInfo(starttime,endtime,next_spnum);
            next_spnum = (Long)infoMap.get("next_spnum");
            count = (int)infoMap.get("count");
            if(null != infoMap){
                List<Map> ls = (List) infoMap.get("data");
                for(Map im:ls){
                    if(null != im&& 2 == (Integer)im.get("sp_status")){
                        //已经审批过的
                        QywxInfoCache.put((Long)im.get("sp_num"),im);
                    }

                }
            }else{
                break;
            }
        }while(count==100);
        LogUtils.info("企业微信后台管理拉取审批信息成功,:starttime:"+starttime+"   endtime:"+endtime+"！！！. \n\r");
    }


    public List<SPExcelEntity> readToList(){

//        Map<String,List> lsmap = new HashMap();
//        for(String spnm:QYWX_SPLX){
//            lsmap.put(spnm,);
//        }
        List newList = new ArrayList();
        Set<Map.Entry<Long,Object>> entrySet =   QywxInfoCache.entrySet();
        for(Map.Entry<Long,Object> me: entrySet){
            Map meInfo = (LinkedHashMap)me.getValue();
            Map newInfos = new HashMap();
            SPExcelEntity se = new SPExcelEntity();
            String spname = (String)meInfo.get("spname"); //流程名
            Long sp_num = (Long)meInfo.get("sp_num"); //序号
            String apply_name = (String)meInfo.get("apply_name"); //人名
            String apply_org = (String)meInfo.get("apply_org"); //部门名
            String sp_type;
            String sp_qj_type = "";
            if(QYWX_SPLX[0].equals(spname)) {
                //请假
                sp_type = QYWX_SPLX_NUM[0];
                String  reason = (String)((Map)meInfo.get("leave")).get("reason");
                if(QYWX_QJ_REASON[0].equals(reason)){
                    //事假
                    sp_qj_type = QYWX_QJ_REASON_NUM[0];
                }else if(QYWX_QJ_REASON[1].equals(reason)){
                    //病假
                    sp_qj_type = QYWX_QJ_REASON_NUM[1];
                }

            } else if(QYWX_SPLX[1].equals(spname)) {
                //因公
                sp_type = QYWX_SPLX_NUM[1];
            }else {
                //其他申请（用车？）
                continue;
            }

            String apply_data = (String)((Map)meInfo.get("comm")).get("apply_data");
            String begin_time_value = "";
            String end_time_value = "";
            String duration = "";
            //判断模板数据新旧格式
            if(0 == apply_data.indexOf("[")){
                //新的数组格式
                List<Map> apply_data_json = JSON.parseArray(apply_data,Map.class);
                for(Map mp : apply_data_json){
                    if("smart-time".equals(mp.get("id").toString())){
                        List<Map> smart_time  = ( List<Map>)mp.get("value");
                        for(Map mp_time : smart_time){
                            if("begin_time".equals(mp_time.get("id").toString())){
                                begin_time_value = mp_time.get("value").toString();
                            }else if("end_time".equals(mp_time.get("id").toString())){
                                end_time_value = mp_time.get("value").toString();
                            }else if("duration".equals(mp_time.get("id").toString())){
                                duration = mp_time.get("value").toString();
                            }
                        }
                    }
                }
            }else{
                //旧的对象格式
                Map apply_data_json = JSON.parseObject(apply_data,Map.class);
                begin_time_value = (String)((Map)apply_data_json.get("begin_time")).get("value");
                end_time_value = (String)((Map)apply_data_json.get("end_time")).get("value");
                duration = (String)((Map)apply_data_json.get("duration")).get("value");
            }
            String begin_date_str = "";
            String end_date_str = "";
            if(null!=begin_time_value&&begin_time_value.length()>=10)
                begin_date_str = DateUtil4Timeslecte.timestampDate(begin_time_value.substring(0,10),DAYEFORMATE);
            if(null!=end_time_value&&end_time_value.length()>=10)
                end_date_str = DateUtil4Timeslecte.timestampDate(end_time_value.substring(0,10),DAYEFORMATE);

            List<UserDomain> listn = userservice.findByWhereForList(new UserQuery().setName(apply_name));
            if(!CollectionUtils.isEmpty(listn)&&listn.size()==1){
                apply_name = listn.get(0).getJob_number();
            }

            String lastOrgName = "";
            if(!StringUtils.isEmpty(apply_org)){
                String[] apply_org_array = apply_org.split("/");
                lastOrgName  = apply_org_array[apply_org_array.length-1];
                //查询组织id并赋值到apply_org
                List<OrgDomain> listo = orgservice.findByWhereForList(new OrgQuery().setName(lastOrgName));
                if(!CollectionUtils.isEmpty(listo)&&listo.size()==1){
                    apply_org = listo.get(0).getOrg_id();
                }
                //apply_org = ;
            }

            newInfos.put("sp_num",sp_num);
            newInfos.put("apply_name",apply_name);
            newInfos.put("apply_org",apply_org);
            newInfos.put("begin_day",begin_date_str.substring(0,10));
            if("00:00:00".equals(begin_date_str.substring(11,19))){
                newInfos.put("begin_time","08:30:00");
            }else{
                newInfos.put("begin_time","13:30:00");
            }
           // newInfos.put("begin_time",begin_date_str.substring(11,19));
            newInfos.put("end_day",end_date_str.substring(0,10));
            if("00:00:00".equals(end_date_str.substring(11,19))){
                newInfos.put("end_time","12:30:00");
            }else{
                newInfos.put("end_time","17:30:00");
            }
            newInfos.put("sp_qj_type",sp_qj_type);
            newInfos.put("sp_type",sp_type);

            se.setSp_num(newInfos.get("sp_num").toString());
            se.setApply_name(newInfos.get("apply_name").toString());
            se.setApply_org(newInfos.get("apply_org").toString());
            se.setBegin_day(newInfos.get("begin_day").toString());
            se.setBegin_time(newInfos.get("begin_time").toString());
            se.setEnd_day(newInfos.get("end_day").toString());
            se.setEnd_time(newInfos.get("end_time").toString());
            se.setSp_qj_type(newInfos.get("sp_qj_type").toString());
            se.setSp_type(newInfos.get("sp_type").toString());


            newList.add(se);
            //apply_data_json

        }
        return newList;
    }


    public void textExcel() throws FileNotFoundException {

        File f = new File("C:/Users/Administrator/Desktop/77.xlsx");
        if(f.exists()){
            f.mkdir();
        }
        OutputStream out = new FileOutputStream(f);
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX,true);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 1,SPExcelEntity.class);
            sheet1.setSheetName("sheet");
            writer.write(readToList(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }




}
