package com.xzp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class DeptCustomer80_App {
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(DeptCustomer80_App.class, args);
	}

}
