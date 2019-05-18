package com.xzp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.xzp"})
@ComponentScan("com.xzp")
public class DeptCustomer80_Feign_App { 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(DeptCustomer80_Feign_App.class, args);
	}

}
