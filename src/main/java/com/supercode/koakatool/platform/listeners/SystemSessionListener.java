package com.supercode.koakatool.platform.listeners;/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 17:05
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 17:05
 */
@WebListener
public class SystemSessionListener implements HttpSessionListener {
    private Logger logger = LoggerFactory.getLogger(SystemSessionListener.class);

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        logger.info("监听器：Session >>>> 创建");
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        logger.info("监听器：Session >>>> 销毁");
    }
}

