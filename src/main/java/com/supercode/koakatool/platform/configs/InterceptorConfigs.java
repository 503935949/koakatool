package com.supercode.koakatool.platform.configs;/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:33
 */

import com.supercode.koakatool.platform.interceptors.LogHandlerInterceptor;
import com.supercode.koakatool.platform.interceptors.LoginHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: zhao.lin
 * @Date: 2018/10/11 15:33
 */
@Configuration
public class InterceptorConfigs implements WebMvcConfigurer {

    /*
        @WebMvcAutoConfiguration,这个注释有一个条件注释
        @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)。
        也就是说只要在容器中发现有WebMvcConfigurationSupport这个类，那就会失效，
        我们就必须在我们的主类上添加@@EnableWebMvc注解，这样我就无法访问默认的静态资源了。
        因为WebMvcConfigurerAdapter过时，是因为java8中接口有默认实现，
        而WebMvcConfigurerAdapter实现的就是WebMvcConfigurer方法，
        所以我只要实现WebMvcConfigurer接口，然后重写我们需要的方法即可
    */
    /**
     * 注册 拦截器
     */
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogHandlerInterceptor()).order(1);
        registry.addInterceptor(new LoginHandlerInterceptor()).order(2);
    }

}
