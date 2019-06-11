package com.supercode.koakatool.cardinfo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.cardinfo.domain.CardInfoDao;
import com.supercode.koakatool.cardinfo.domain.CardInfoDomain;
import com.supercode.koakatool.cardinfo.service.ICardInfoService;
import com.supercode.koakatool.cardinfo.service.query.CardInfoQuery;
import com.supercode.koakatool.system.*;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Transactional(rollbackFor=Throwable.class)
public class CardInfoServiceImpl implements ICardInfoService {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private CardInfoDao dao;

//    @Autowired
//    private UserDao userDao;

    private static final String HTTPS = "https://";
    //投产用172.26.42.182  //测试用 47.92.132.65
    private static final String CARD_IP = Context.CARD_IP;
    private static final String CARD_AUTHSTRING = "admin:Exos..9300";
    private static final String CARD_APIKEY = "MyApiKey:";
    private static final String CARD_LOGIN_URL = "/ExosApiLogin/api/v1.0/login/";
    private static final String CARD_AUDITLOGENTRIES_URL = "/ExosApi/log_api/v1.0/accessLogEntries";//GET accessLogEntries auditLogEntries
    private static final String CARD_LOGIOUT_URL = "/ExosApi/api/v1.0/logins/logoutMyself/";


    /**
     * 获取最近一次合并的日期
     * @return
     */
    public String getLogsMaxDate(){
        return dao.getLogsMaxDate();
    }

    public String getCardIdentifier(){
        LogUtils.info("门卡系统登录，获取API令牌"+" \n\r");
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        HttpHeaders header = HttpUtils.getHttpHeader();
        String authString = CARD_AUTHSTRING;
        byte[] authEncBytes = Base64.encodeBase64(authString.getBytes());
        String authStringEnc = new String(authEncBytes);
        header.add("Authorization", "Basic " + authStringEnc);
        HttpEntity<String> formEntity = new HttpEntity<>( header);

        String url = HTTPS+CARD_IP+CARD_LOGIN_URL;
        Map result =null;
        try{
            result = restTemplate.postForObject(url, formEntity, Map.class);
        }catch(Exception e){
            e.printStackTrace();
            if(e.getMessage().indexOf("403")>-1){
                LogUtils.error("门卡系统登录请求不被允许，数据拉取失败"+" \n\r");
            }
            throw e;
        }
        Map value = (Map)result.get("Value");
        String identifier = (String)value.get("Identifier");
        LogUtils.info("门卡系统登录成功，identifie:"+identifier +" \n\r");
        // Group group2 = JSON.parseObject(result, Group.class);
        String identifierString = CARD_APIKEY+identifier;
        byte[] encBytes = Base64.encodeBase64(identifierString.getBytes());
        String stringEnc = new String(encBytes);
        return stringEnc;
    }

    /**
     * 获取门卡信息
     * @return
     */
    public List<CardInfoDomain> getAllCardLogs(String identifier) throws ParseException {
        List<CardInfoDomain> ret = new ArrayList<>();
        String logsMaxdate =  this.getLogsMaxDate();
        String firstDay = "2018-12-01";
        if(null!=logsMaxdate&&!"".equals(logsMaxdate)){
            firstDay = logsMaxdate;
        }
        String format = "yyyy-MM-dd";
        SimpleDateFormat fat = new SimpleDateFormat(format);
        //计算相差多少天
        int daylongs = DateUtil4Timeslecte.daysBetweenDates(fat.parse(firstDay),new Date());
        List<String> dys =  DateUtil4Timeslecte.getDayStrToNowByLongs( daylongs, format);
        for(String dy:dys){
            List<CardInfoDomain> re = this.getCardLogs(identifier,dy,0);
            if(null!=re&&null!=ret){
                ret.addAll(re);
            }
        }

        return ret;

    }


    /**
     * 获取门卡信息
     * @return
     */
    public List<CardInfoDomain> getCardLogs(String identifier,String date,int skip){
        LogUtils.info("拉取门卡系统日志:"+identifier+" \n\r");

        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        HttpHeaders header = HttpUtils.getHttpHeader();
        header.add("Authorization", "Basic " + identifier);

        // -------------------------------> 解决(响应数据可能)中文乱码 的问题
//        List<HttpMessageConverter<?>> converterList = restTemplate.getMessageConverters();
//        converterList.remove(1); // 移除原来的转换器
//        // 设置字符编码为utf-8
//        HttpMessageConverter<?> converter = new StringHttpMessageConverter(StandardCharsets.UTF_8);
//        converterList.add(1, converter); // 添加新的转换器(注:convert顺序错误会导致失败)
//        restTemplate.setMessageConverters(converterList);
        //header.add("Content-Type","application/json");
        HttpEntity<String> requestEntity = new HttpEntity<String>(null, header);



        String  url = HTTPS+CARD_IP+CARD_AUDITLOGENTRIES_URL;
        url = HTTPS+CARD_IP+CARD_AUDITLOGENTRIES_URL+
                "?$filter={filter}&$orderby={orderby}&$top={top}&$skip={skip}";
        //%24count=true&%24orderby=LogDate%20desc&%24top=10000
        Map<String, String> map= new HashMap<String, String>();
        //map.put("count", "true");
        map.put("orderby", " LogDate desc");
        map.put("top", "10000");
        map.put("filter","LogDate ge "+date);
        map.put("skip",skip+"");
//        HttpEntity<MultiValueMap<String, String> > formEntity = new HttpEntity<>( map,header);
        ResponseEntity<Map> result =null;
        try{
            result = restTemplate.exchange(url, HttpMethod.GET, requestEntity, Map.class, map);
//            result = restTemplate.getForEntity(url, String.class,map);
        }catch(Exception e){
            e.printStackTrace();
            if(e.getMessage().indexOf("403")>-1){
                LogUtils.error("拉取门卡系统日志请求不被允许，数据拉取失败"+" \n\r");
            }
            throw e;
        }
        List<Map> value = (List) result.getBody().get("value");
        System.out.println("登陆成功后访问的页面===============");

        List<CardInfoDomain> cls = new ArrayList<>();
        for(Map ob:value){
            JSONObject json = (JSONObject) JSONObject.toJSON(ob);
            CardInfoDomain ci = JSONObject.toJavaObject(json,CardInfoDomain.class);
            cls.add(ci);
        }

        List<CardInfoDomain> ncls = null;
        if(cls.size()==10000){
            skip += 10000;
            ncls = this.getCardLogs(identifier, date, skip);
        }
        if(null!=ncls&&null!=cls){
            cls.addAll(ncls);
        }
//        cls.addAll();
        if(cls.size()>0)
            LogUtils.info("拉取门卡系统日志成功 date="+date+" skip="+skip+"，result.size:"+cls.size() +" \n\r");

        return cls;
    }


    //门卡系统登出
    public void cardLogout(String identifier){
        LogUtils.info("门卡系统登出:"+identifier+" \n\r");
        RestTemplate restTemplate = new RestTemplate(new HttpsClientRequestFactory());
        HttpHeaders header = HttpUtils.getHttpHeader();
        header.add("Authorization", "Basic " + identifier);
        header.add("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        header.add("Origin","http://localhost:8080");
        header.add("Referer","http://localhost:8080");
        HttpEntity<String> formEntity = new HttpEntity<>( header);
        String url = HTTPS+CARD_IP+CARD_LOGIOUT_URL;
        try{
            restTemplate.postForObject(url, formEntity, Map.class);
            LogUtils.info("门卡系统登出，拉取门卡信息结束"+" \n\r");
        }catch(Exception e){
            e.printStackTrace();
            if(e.getMessage().indexOf("403")>-1){
                LogUtils.error("门卡系统已经登出或已断开连接"+" \n\r");
            }
        }

    }

    //持久化入库
    public void insertCordInfo(List<CardInfoDomain> cordInfos){
        for(CardInfoDomain domain:cordInfos){
            //LogUtils.info(domain+" \n\r");
            if((!StringUtils.isEmpty(domain.getCardNr()))
                    &&"RU".equals(domain.getDataPointType())
                    &&"Z0010".equals(domain.getEventTypeInfo())
                    &&((!StringUtils.isEmpty(domain.getName())&&domain.getName().indexOf("临时卡")==-1))
                    &&((!StringUtils.isEmpty(domain.getPersonalNr())&&domain.getPersonalNr().indexOf("M")==-1))
//                    &&((!StringUtils.isEmpty(domain.getPersonalNr())&&domain.getPersonalNr().indexOf("{")==-1))
//                    &&((!StringUtils.isEmpty(domain.getPersonalNr())&domain.getPersonalNr().indexOf("}")==-1))
                    &&((!StringUtils.isEmpty(domain.getName())&&domain.getName().indexOf("Test")==-1))
                    &&((!StringUtils.isEmpty(domain.getName())&&domain.getName().indexOf("_")==-1))
            ){
                try{
                    if(!StringUtils.isEmpty(domain.getLogDate())){
                        String dateString = domain.getLogDate().substring(0,10);
                        String timeString = domain.getLogDate().substring(11,19);
                        domain.setDateStr(dateString);
                        domain.setTimeStr(timeString);
                        domain.setPersonalNr(domain.getPersonalNr().replace("{","").replace("}",""));
                        String[] strInfo = domain.getName().split(",|，");
                        if(strInfo.length>1){
                            if(null!=strInfo[0]&&(!"".equals(strInfo[0]))&&(strInfo[0].indexOf("MSA")>-1)){
                                domain.setCardname(strInfo[0]);
                                domain.setUsername(strInfo[1]);
                            }else if(null!=strInfo[1]&&(!"".equals(strInfo[1]))&&(strInfo[1].indexOf("MSA")>-1)){
                                domain.setCardname(strInfo[1]);
                                domain.setUsername(strInfo[0]);
                            }
                        }

                        dao.insert(domain);

                        //抽取简单人员信息
                        UserDomain user = new UserDomain(domain.getPersonalNr(),domain.getUsername());
                        //userDao.insert(user);
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    LogUtils.error("插入失败内容："+domain.toString());
                    throw e;
                }
            }

        }
        LogUtils.info("门卡信息入库成功,共："+cordInfos.size()+" 条记录！！！");
    }

    /**
     * 查询打卡记录
     * @param query
     * @return
     */
    public List<CardInfoDomain> getCardEvents(CardInfoQuery query){
        return dao.getCardEvents(query);
    }

    /**
     * 拉取门卡系统信息系
     */
    public void getCardLogs() throws ParseException {
        LogUtils.info("拉取门卡系统开始");
        //登录获取令牌
        String identifier = this.getCardIdentifier();
        // String identifier="TXlBcGlLZXk6ODAzMTVjNzQtNWFkYS00OGE0LWJlMWMtMTRmY2RkZjVhZWU5";
        //拉取门卡信息
        List m = null;
        try {
            m = this.getAllCardLogs(identifier);

        } catch (Exception e) {
            throw e;

        }finally {
            this.cardLogout(identifier);
        }
        if (m != null && m.size() > 0) {
            this.insertCordInfo(m);
        }
        LogUtils.info("拉取门卡系统结束");
    }

    public static void main(String[] s){
//        CardInfoServiceImpl a = new CardInfoServiceImpl();
//        //登录获取令牌
//        String identifier =  a.getCardIdentifier();
//        // String identifier="TXlBcGlLZXk6ODAzMTVjNzQtNWFkYS00OGE0LWJlMWMtMTRmY2RkZjVhZWU5";
//        //拉取门卡信息
//        List m = null;
//        try{
//            m =  a.getCardLogs(identifier);
//            LogUtils.info(m+" \n\r");
//        }catch(Exception e){
//            a.cardLogout(identifier);
//        }
//        a.cardLogout(identifier);
//        if(m!=null&&m.size()>0){
//            a.insertCordInfo(m);
//        }


    }




}
