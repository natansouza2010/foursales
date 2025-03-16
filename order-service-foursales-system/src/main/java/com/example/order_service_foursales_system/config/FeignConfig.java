package com.example.order_service_foursales_system.config;

import com.example.order_service_foursales_system.config.interceptor.FeignRequestInterceptor;
import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients
@Configuration
public class FeignConfig {

    @Bean(name = "customFeignRequestInterceptor")
    public RequestInterceptor feignRequestInterceptor() {
        return new FeignRequestInterceptor();
    }
}
