package com.example.order_service_foursales_system.config.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class FeignRequestInterceptor implements RequestInterceptor {


        @Override
        public void apply(RequestTemplate template) {
            String token = getToken();
            System.out.println("Token dentro do apply: " + token);
            if (token != null) {
                System.out.println("Token enviado: " + token); // Log do token
                template.header("Authorization", "Bearer " + token);
            }
        }



        private String getToken() {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.getCredentials() != null) {
                return authentication.getCredentials().toString();
            }
            return null;
        }
}



