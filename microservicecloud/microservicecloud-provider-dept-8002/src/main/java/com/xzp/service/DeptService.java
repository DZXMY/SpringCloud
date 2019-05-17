package com.xzp.service;

import java.util.List;

import com.xzp.model.Dept;

public interface DeptService
{
	public boolean add(Dept dept);

	public Dept get(Long id);

	public List<Dept> list();
}
