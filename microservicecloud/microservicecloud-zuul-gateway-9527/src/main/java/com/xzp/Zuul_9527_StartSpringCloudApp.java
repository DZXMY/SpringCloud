package com.xzp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy
public class Zuul_9527_StartSpringCloudApp {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Zuul_9527_StartSpringCloudApp.class, args);
	}

}
