package com.xzp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

import com.myrule.MySelfRule;


@SpringBootApplication
@EnableEurekaClient
//在启动微服务的时候添加如下配置，可以加载自定义Ribbon的配置类（可实现自定义负载均衡）
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)
public class DeptCustomer80_App { 
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(DeptCustomer80_App.class, args);
	}

}
