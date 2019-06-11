package com.supercode.koakatool.faceinfo.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.supercode.koakatool.business.domain.UserDao;
import com.supercode.koakatool.business.domain.UserDomain;
import com.supercode.koakatool.faceinfo.domain.FaceInfoDao;
import com.supercode.koakatool.faceinfo.domain.FaceInfoDomain;
import com.supercode.koakatool.faceinfo.service.IFaceInfoService;
import com.supercode.koakatool.faceinfo.service.query.FaceInfoQuery;
import com.supercode.koakatool.system.DateUtil4Timeslecte;
import com.supercode.koakatool.system.HttpUtils;
import com.supercode.koakatool.system.LogUtils;
import org.apache.http.Consts;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional(rollbackFor=Throwable.class)
public class FaceInfoServiceImpl implements IFaceInfoService {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletResponse response;
    @Autowired
    private FaceInfoDao dao;

//    @Autowired
//    private UserDao userDao;


    private static final String HTTP = "http://";
    private static final String FACE_IP = "192.168.1.50";
    private static final String FACE_AUDITLOGENTRIES_URL = "/auth/login";

    /**
     * 获取最近一次合并的日期
     * @return
     */
    public String getLogsMaxDate(){
        return dao.getLogsMaxDate();
    }

    /**
     * 拉取人脸系统信息
     */
    public void getFaceLogs(){
        LogUtils.info("拉取人脸系统开始");
        List<FaceInfoDomain> ls = this.getFaceLogsInfo();
        this.insertFaceInfo(ls);
        LogUtils.info("拉取人脸系统结束 共："+ls.size()+"条数据。");
    }

    public List<FaceInfoDomain> getFaceLogsInfo(){
        String logsMaxdate =  this.getLogsMaxDate();
        List<FaceInfoDomain> dl = new ArrayList<>();
        String name = "username";
        String password = "password";
        // 全局请求设置
        RequestConfig globalConfig = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
        // 创建cookie store的本地实例
        CookieStore cookieStore = new BasicCookieStore();
        // 创建HttpClient上下文
        HttpClientContext context = HttpClientContext.create();
        context.setCookieStore(cookieStore);
        // 创建一个HttpClient
        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(globalConfig)
                .setDefaultCookieStore(cookieStore).build();
        CloseableHttpResponse res = null;
        // 创建本地的HTTP内容
        try {
            try {
                // 创建一个get请求用来获取必要的Cookie，如_xsrf信息
                HttpGet get = new HttpGet("http://192.168.1.50/");

                res = httpClient.execute(get, context);
                // 获取常用Cookie,包括_xsrf信息
                LogUtils.info("访问后的获取的常规Cookie:===============");
                for (Cookie c : cookieStore.getCookies()) {
                    LogUtils.info(c.getName() + ": " + c.getValue());
                }
                res.close();

                // 构造post数据
                List<NameValuePair> valuePairs = new LinkedList<NameValuePair>();
                valuePairs.add(new BasicNameValuePair(name, "abc@abc.com"));
                valuePairs.add(new BasicNameValuePair(password, "abc123"));
                // valuePairs.add(new BasicNameValuePair("remember_me", "true"));
                UrlEncodedFormEntity entity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
                entity.setContentType("application/x-www-form-urlencoded");

                // 创建一个post请求
                HttpPost post = new HttpPost("http://192.168.1.50/auth/login");
                post.setHeader("user-agent","Koala Admin");
                // 注入post数据
                post.setEntity(entity);
                res = httpClient.execute(post, context);

                // 打印响应信息，查看是否登陆是否成功
                LogUtils.info("打印响应信息===========");
                //HttpClientUtils.printResponse(res);
                res.close();

                LogUtils.info("登陆成功后,新的Cookie:===============");
                for (Cookie c : context.getCookieStore().getCookies()) {
                    LogUtils.info(c.getName() + ": " + c.getValue());
                }
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                Date d = null;
                if(null==logsMaxdate||"".equals(logsMaxdate)) {
                    d = sdf.parse("20000000");
                }else{
                    d = sdf.parse(DateUtil4Timeslecte.getYestodays(sdf.parse(logsMaxdate),5,"yyyyMMdd"));
                }
                LogUtils.info(d.getTime()+"");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
                Date d2 = sdf.parse("20190201");
                LogUtils.info(new Date().getTime()+"");
                //&start="+d.getTime()+"&end="+new Date().getTime()
                String logsurl = "http://192.168.1.50//event/events?size=1000";
                logsurl+= "&start="+d.getTime()/10000+"&end="+new Date().getTime();
                // 构造一个新的get请求，用来测试登录是否成功
                HttpGet newGet = new HttpGet(logsurl);
                res = httpClient.execute(newGet, context);
                String content = EntityUtils.toString(res.getEntity());
                LogUtils.info("登陆成功后访问的页面===============");
                // LogUtils.info(content);
                JSONObject obj = JSONObject.parseObject(content);
                String lss = obj.getString("data");
                JSONObject page = JSONObject.parseObject(obj.getString("page"));
                int total = Integer.parseInt(page.getString("total"));
                dl = JSONObject.parseArray(lss, FaceInfoDomain.class);
//                for(FaceInfoDomain o:dl){
//                    LogUtils.info(o);
//                }
//                insertFaceInfo(dl);
                this.getInfoByPage(httpClient, context, total, dl);
                res.close();

            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException | ParseException e) {
                e.printStackTrace();
            } finally {
                httpClient.close();
                res.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dl;
    }


    //持久化入库
    public void insertFaceInfo(List<FaceInfoDomain> datas){
        for(FaceInfoDomain domain:datas){
            if((!StringUtils.isEmpty(domain.getId()))
                    &&(null!=domain.getSubject())
                    &&(!StringUtils.isEmpty(domain.getSubject().getJob_number()))
                    &&(!StringUtils.isEmpty(domain.getSubject().getName()))
                    &&(domain.getSubject().getName().indexOf("临时卡")==-1)
                    &&(domain.getSubject().getJob_number().indexOf("{")==-1)
                    &&(domain.getSubject().getJob_number().indexOf("}")==-1)
                    &&(domain.getSubject().getName().indexOf("Test")==-1)
                    &&(domain.getSubject().getName().indexOf("_")==-1)
            ){
                try{
                    //timestampDate
                    String timestamp = domain.getTimestamp();
                    if(!StringUtils.isEmpty(timestamp)){
                        String ttDate = DateUtil4Timeslecte.timestampDate(timestamp,"yyyy-MM-dd HH:mm:ss");
                        String dateString = ttDate.substring(0,10);
                        String timeString = ttDate.substring(11,19);
                        domain.setDateStr(dateString);
                        domain.setTimeStr(timeString);
                        dao.insert(domain);



                        //抽取简单人员信息
                        UserDomain user =  domain.getSubject();
                        //userDao.insert(user);
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    LogUtils.error("插入失败内容："+domain.toString());
                }
            }

        }
    }

    public List<FaceInfoDomain> getFaceEvents(FaceInfoQuery query) {
        return dao.getFaceEvents(query);
    }


    public void getInfoByPage(CloseableHttpClient httpClient, HttpClientContext context,int page,List<FaceInfoDomain> dls){
        CloseableHttpResponse res = null;


        try {
            for (int i=1;i<=page;i++) {

                List<FaceInfoDomain> dl = new ArrayList<>();
                String logsurl = "http://192.168.1.50//event/events?size=20000";
                logsurl += "&start=1" + "&end=" + new Date().getTime() + "&page=" + i;
                // 构造一个新的get请求，用来测试登录是否成功
                HttpGet newGet = new HttpGet(logsurl);
                res = httpClient.execute(newGet, context);
                String content = EntityUtils.toString(res.getEntity());
                LogUtils.info("登陆成功后访问的页面===============");
                // LogUtils.info(content);
                JSONObject obj = JSONObject.parseObject(content);
                String lss = obj.getString("data");
                dl = JSONObject.parseArray(lss, FaceInfoDomain.class);
                if (null != dl && null != dls) {
                    dls.addAll(dl);
                }

            }
            res.close();


        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        FaceInfoServiceImpl a = new FaceInfoServiceImpl();


    }


}
