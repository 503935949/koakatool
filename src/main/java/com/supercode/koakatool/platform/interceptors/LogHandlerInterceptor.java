package com.supercode.koakatool.platform.interceptors;/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:16
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:16
 */
@Component
public class LogHandlerInterceptor implements HandlerInterceptor {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpSession session;

    /**
     * controller 执行之前调用
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        System.out.println("LogHandlerInterceptor------preHandle-----");
      //  System.out.println("------LogHandlerInterceptor-----sessionID :"+session.getId());
        return true;
    }

    /**
     * controller 执行之后，且页面渲染之前调用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        System.out.println("LogHandlerInterceptor------postHandle-----");
    }

    /**
     * 页面渲染之后调用，一般用于资源清理操作
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        System.out.println("LogHandlerInterceptor------afterCompletion-----");

    }

}
