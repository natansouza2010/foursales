package com.example.order_service_foursales_system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class OrderServiceFoursalesSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderServiceFoursalesSystemApplication.class, args);
	}

}
