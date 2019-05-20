package com.myrule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RetryRule;
import com.netflix.loadbalancer.RoundRobinRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import com.netflix.loadbalancer.ZoneAvoidanceRule;

@Configuration
public class MySelfRule
{
	@Bean
	public IRule myRule()
	{
		//return new RandomRule();				// 自定义为随机
		//return new RoundRobinRule();			// 自定义为轮询
		
		return new RandomRule_ZY();				// 自定义为每台机器5次
		
		//会先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，还有并发的连接数量超过阈值的服务，然后对剩余的服务列表进行轮询
		//return new AvailabilityFilteringRule();
		
		//根据平均响应时间计算所有服务的权重，响应越快服务的权重越大；如刚启动时统计信息不足，则使用轮询策略，信息足够时切换到此方法
//		return new WeightedResponseTimeRule();
		
		//先按照轮询策略获取服务，如果获取失败会在指定的时间内进行重试，超过时间跳过
//		return new RetryRule();
		
		//先过滤掉由于多次访问故障而处于断路器跳闸状态的服务，然后选择一个并发量最小的
//		return newe BestAvailableRule();
		
		//默认规则，复合判断server所在区域的性能和server的可用性来选择服务器
//		return new ZoneAvoidanceRule();
		
	}
}
