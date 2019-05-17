package com.xzp.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@SuppressWarnings("serial")
//@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain=true)
public class Dept implements Serializable{
	private Long 	deptno; // 主键
	private String 	dname; // 部门名称
	private String 	db_source;// 来自那个数据库，因为微服务架构可以一个服务对应一个数据库，同一个信息被存储到不同数据库
	public Dept(String dname) {
		super();
		this.dname = dname;
	}
	
	public static void main(String[] args) {
		Dept dept = new Dept("21312");
		dept.setDeptno((long) 12).setDb_source("01");
		System.out.println(dept.toString());
	}
}
