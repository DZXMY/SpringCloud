 package com.xzp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xzp.model.Dept;
import com.xzp.service.DeptClientService;


@RestController
public class DeptControllerCustomer {
	
	@Autowired
	private DeptClientService deptClientService;
	

	@RequestMapping(value="/customer/dept/add")
	public boolean add(Dept dept){
		return deptClientService.add(dept);
	}
	
	@RequestMapping(value="/customer/dept/get/{id}")
	public Dept get(@PathVariable("id") Long id){
		return deptClientService.get(id);
	}

	@RequestMapping(value="/customer/dept/list")
	public List<Dept> list(){
		return deptClientService.list();
	}
	
}
