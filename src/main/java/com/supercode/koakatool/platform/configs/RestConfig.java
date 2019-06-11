package com.supercode.koakatool.platform.configs;

import com.supercode.koakatool.system.HttpsClientRequestFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

    @Bean
    public RestTemplate configRestTemplate(){
        return  new RestTemplate(new HttpsClientRequestFactory());
    }

}
