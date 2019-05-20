package com.xzp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.xzp.model.Dept;

import feign.hystrix.FallbackFactory;

@Component		//注解必须加！
public class DeptClientServiceFallBackFactory implements FallbackFactory<DeptClientService> {

	@Override
	public DeptClientService create(Throwable arg0) {
		// TODO Auto-generated method stub
		return new DeptClientService() {
			
			@Override
			public List<Dept> list() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Dept get(long id) {
				// TODO Auto-generated method stub
				return new Dept().setDeptno(id).setDname("该id" + id + "没有对应的信息，降级-- 这个服务已经关了").setDb_source("在服务接口处处理");
			}
			
			@Override
			public boolean add(Dept dept) {
				// TODO Auto-generated method stub
				return false;
			}
		};
	}

}
