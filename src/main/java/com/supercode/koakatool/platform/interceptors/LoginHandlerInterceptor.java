package com.supercode.koakatool.platform.interceptors;/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:16
 */

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:16
 */
@Order(1)
@Component
public class LoginHandlerInterceptor implements HandlerInterceptor {


    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LoginHandlerInterceptor------preHandle-----");

        System.out.println("LoginHandlerInterceptor-+"+request.getRequestURL());
        System.out.println("LoginHandlerInterceptor-+"+request.getRequestURI());
        System.out.println("LoginHandlerInterceptor-+"+request.getContextPath());
        System.out.println("LoginHandlerInterceptor-+"+request.getServletPath());
        System.out.println("LoginHandlerInterceptor-+"+request.getQueryString());
      //  System.out.println("------LoginHandlerInterceptor-----sessionID :"+session.getId());
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("LoginHandlerInterceptor------postHandle-----");
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("LoginHandlerInterceptor------afterCompletion-----");

    }



    //1、request.getRequestURL() 返回的是完整的url，包括Http协议，端口号，servlet名字和映射路径，但它不包含请求参数。
    //
    //2、request.getRequestURI() 得到的是request URL的部分值，并且web容器没有decode过的
    //
    //3、request.getContextPath() 返回 the context of the request.
    //
    //4、request.getServletPath() 返回调用servlet的部分url.
    //
    //5、request.getQueryString() 返回url路径后面的查询字符串
    //
    //
    //
    //示例： 当前url：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp?action=idp.sptopn
    //
    //request.getRequestURL() ：http://localhost:8080/CarsiLogCenter_new/idpstat.jsp
    //
    //request.getRequestURI() ：/CarsiLogCenter_new/idpstat.jsp
    //
    //request.getContextPath()：/CarsiLogCenter_new
    //
    //request.getServletPath()： /idpstat.jsp
    //
    //request.getQueryString()：action=idp.sptopn

}
