package com.xzp.cfgbean;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import com.netflix.loadbalancer.RoundRobinRule;

// @Configuration用于ConfigBean类上时，相当于 applicationContext.xml文件
@Configuration
public class ConfigBean {
	
	@Bean
	@LoadBalanced  //负载均衡（默认轮询）
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}
	
	//重写IRule接口，替代默认轮询
	@Bean
	public IRule myRule(){
		return new RandomRule();					//随机
		//return new RoundRobinRule();				//轮询
		
		//先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表按照轮询策略进行访问
		//return new AvailabilityFilteringRule();
		
		//根据平均响应时间计算所有服务的权重，越快响应的服务权重越大，刚启动时如果统计信息不足，则使用RoundRobinRule策略，信息足够时切换
		//return new WeightedResponseTimeRule();	
		
		//return new RetryRule();	
		
		//先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的服务
		//return new BestAvailableRule();	
		
		//默认规则，复合判断server所在区域的性能和server的可用性选择服务器
		//return new ZoneAvoidanceRule();
	}
	
}
