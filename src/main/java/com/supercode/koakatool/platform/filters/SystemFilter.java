package com.supercode.koakatool.platform.filters;

import com.supercode.koakatool.cardinfo.service.ICardInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Order(1)
@WebFilter(filterName = "systemFilter", urlPatterns = "/*")
public class SystemFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(SystemFilter.class);
    @Autowired
    private ICardInfoService cardInfoServiceImpl;
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("过滤器：SystemFilter >>>> 初始化");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        logger.info("过滤器：SystemFilter >>>> 执行过滤操作");
//        filterChain.doFilter(servletRequest, servletResponse);
//
//
//
//        String ContextPath = servletRequest.getServletContext().getContextPath();
//        logger.info("过滤器：SystemFilter >>>> ContextPath:"+ContextPath);
//        String realPath = servletRequest.getServletContext().getRealPath("/");
//        logger.info("过滤器：SystemFilter >>>> realPath:"+realPath);
//


    }

    @Override
    public void destroy() {
        logger.info("过滤器：SystemFilter >>>> 销毁");

    }

}
