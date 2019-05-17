 package com.xzp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.xzp.model.Dept;


@RestController
public class DeptControllerCustomer {
	
	//private static final String REST_URL_PREFIX = "http://localhost:8001";
	//通过服务名字找到访问的服务器 ribbon							  MICROSERVICECLOUD-DEPT
	private static final String REST_URL_PREFIX = "http://MICROSERVICECLOUD-DEPT";
	
	//restTemplate提供了多种便捷访问远程HTTP服务的方法
	//是一种简单便捷的访问restful服务模板类，是spring提供的用于访问Rest服务的客户端模板工具集
	//使用方法： (url,requestMap,ResponseBean.class)这三个参数分别代表
	//		rest请求地址，请求参数，http响应转换  被转换成的对象类型
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value="/customer/dept/add")
	public boolean add(Dept dept){
		return restTemplate.postForObject(REST_URL_PREFIX + "/dept/add", dept, Boolean.class);
	}
	
	@RequestMapping(value="/customer/dept/get/{id}")
	public Dept get(@PathVariable("id") Long id){
		return restTemplate.getForObject(REST_URL_PREFIX + "/dept/get/" + id, Dept.class);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/customer/dept/list")
	public List<Dept> list(){
		return restTemplate.getForObject(REST_URL_PREFIX + "/dept/list", List.class);
	}
	
	@RequestMapping(value = "/customer/dept/discovery")
	public Object discovery(){
		return restTemplate.getForObject(REST_URL_PREFIX + "/dept/discovery", Object.class);
	}
	
}
