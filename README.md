# SpringCloud
springcloud-----

spring cloud 封装了Eureka模块来 实现服务注册和发现（zookeeper）

Eureka采用了c-s的设计架构。 Eureka Server 作为服务注册功能的服务器，是服务注册中心

而系统中的其他微服务，使用Eureka的客户端连接到Eureka Server并维持心跳连接。这样系统人员就可以通过Eureka Server
来监控系统中各个微服务是否正常运行。SpringCloud 的一些其他模块（如Zuul） 就可以通过Eureka Server来发现系统中的
其他微服务，并执行相关的逻辑。

				 
三大角色 
1.Service Provider（服务提供者）将服务注册到Eureka Server（服务注册中心）；
2.Service Consumer（服务消费者）通过Eureka Server查找Service Provider（服务提供者）；
3.Service Consumer（服务消费者）调用Service Provider（服务提供者）；


Eureka包含两个组件：Eureka Server和Eureka Client

1.Eureka Server提供服务注册服务
各个节点启动后，会在Eureka Server中进行注册，Eureka Server中的服务注册表中会存储所有可用服务节点的信息，服务节点的信息可以在界面中直观的看到。 

2.Eureka Client 是一个Java客户端，用于简化Eureka Server的交互，客户端同时也具备一个内置的、使用轮询（round-robin）负载算法的负载均衡器。在应用启动会，将会向Eureka Server发送心跳（默认周期为30秒）。如果EurekaServer在多个心跳周期内没有接收到某个节点的心跳，EurekaServer 将会从服务注册表中吧这个服务节点移除（默认90秒）。


**** 使用@EnableEurekaClient注解（服务提供者，主启动类之上） 该服务启动后会自动注册进eureka服务（EurekaServer）中；
**** 使用@EnableEurekaServer注解（服务注册中心，主启动类之上） 该服务启动之后会接受其他服务注册（Service Provider）进来；


## actuator  监控和配置


## Eureka自我保护机制
如果EurekaServer在一定时间内没有接收到某一个微服务实例的心跳，EurekaServer将会注销该实例（默认90秒）。
但是当网络分区故障发生时，微服务与EurekaServer之间无法正常通信，而此时微服务本身是健康的，此时是不应该注销这个微服务。 就需要通过“自我保护机制”来解决这个问题：
当EurekaServer节点短时间内丢失过多客户端时（可能发生了网络分区故障），那么这个节点就会进入自我保护模式。一旦进入该模式，EurekaServer就会保护服务注册表中的信息，不再删除表中数据（也就是不会注销任何微服务）。当网络故障恢复后，该EurekaServer节点会自动退出自我保护模式。

某时刻某一个微服务不可用了，Eureka不会立刻清理，依旧会对该微服务的信息进行保存（不盲目注销任何健康的微服务）；
可以使用eureka.server.enable-self-preservation = false 禁用自我保护模式

** 另外一种保护机制：如果在15分钟内超过85%的节点都没有正常的心跳，那么eureka就认为客户端与注册中心出现了网络故障，此时会出现以下几种情况：
1.Eureka不再从注册表中移除因为长时间没有收到心跳而应该国企的服务；
2.Eureka仍然能够接受新服务的注册和查询请求，但是不会被同步到其他节点（即保证当前节点依然可用）；
3.当网络稳定时，当前实例新的注册信息会被同步到其他节点中；
==》 

## CAP && ACID
关系型数据库（MySQL/Orcale/SQLServer） ----  ACID:
	A（Atomicity） 原子性
	C（Consistency）一致性
	I（Isolation）独立性
	D（Durability）持久性
noSql数据库（redis/MongoDB/HBASE）  -------   CAP
	C（Consistency） 强一致性
	A（Availability）（高）可用性
	P（Partition tolerance）分区容错性

分布式系统（不可能只有一个服务器，P 必须满足）     ----- CAP（同上）
	任何一个分布式系统不可能同时满足上述cap；
CA - 单点集群，满足一致性和可用性。在可扩展性上不太强大；
CP - 满足一致性，分区容错性的系统，通常性能不是特别高； （zookeeper）
AP - 满足可用性，分区容错性的系统，通常对一致性要求低一些；（eureka）


#######  Ribbon  
SpringCloud Ribbon基于Netflix Ribbon实现的一套 客户端 负载均衡 的工具；
属于进程内LB：将LB逻辑集成到消费方，消费方从服务注册中心获取有哪些地址可用，然后自己选择一个合适的服务器；
sc中负载均衡算法可自定义
核心组件：IRule：根据特定算法从服务列表中选取一个要访问的服务
######   LB（loadbalancer负载均衡）  将用户的请求平摊到多个服务商，常见的有Nginx，LVS，硬件F5=

***  ribbon和eureka整合后，customer可以直接调用服务（根据服务名）而不再关心地址和端口号
***  ribbon工作时：
	1.先选择EurekaServer，优先选择在同一个区域内负载比较少的Server；
	2.再根据用户指定的策略，在从server取到的服务注册列表中选择一个地址；

ribbon其实就是一个软负载均衡的客户端组件，可以和其他所需请求的客户端结合使用，和eureka结合只是其中一个实例。



踩坑：
      多个服务注册到eureka服务列表中时，如需修改eureka服务名称；各个服务的instance-id不可相同，否则相同id只能注册最后一个启动的服务；
      多个服务的application.name要相同，对外暴露统一的服务实例名；


在启动微服务的时候添加如下配置，可以加载自定义Ribbon的配置类（可实现自定义负载均衡，针对某个服务）
@RibbonClient(name="MICROSERVICECLOUD-DEPT",configuration=MySelfRule.class)
******注：
	MySelfRule类不能放在@ComponentScan所扫描的当前包以及子包下，否则该自定义配置类就会被所有Ribbon客户端所共享，也就是说不能达到特殊化定制的目的了。
	不能和主启动类在同一个包下；


##### Feign  是一个声明式的Web服务客户端，使得编写Web服务客户端变得简单；
    只需创建接口，加上注解即可完成对服务提供方的接口绑定，简化了使用ribbon时，自动封装服务调用客户端的开发量；
feign集成了ribbon：
    利用ribbon维护了服务列表信息，并且通过轮询实现了客户端的负载均衡。而与ribbon不同的是，通过feign只需要定义服务，绑定接口且以声明式的方法，优雅而简单的实现了服务调用。

**** Feign通过接口的方法调用Rest服务（之前是Ribbon+RestTemplate）
	请求发送给eureka服务器，通过Feign直接找到服务接口，由于集成了ribbon，所以也支持负载均衡。




####	Hystrix  用于处理分布式系统的延迟和容错的开源库，保证在一个依赖出现问题的情况下，不会导致整体服务失败，避免级联故障，以提高分布式系统的弹性；

“断路器”： 当某个服务单元发生故障之后，通过断路器的故障监控，向调用方返回一个符合预期的、可处理的备选响应（fallBack），而不是长时间的等待获取抛出调用方无法处理的异常，这样就保证了服务调用方的线程不会被长时间、不必要的占用，从而避免了故障在分布式系统中蔓延，乃至雪崩；

Hystrix监控微服务的调用状态，当失败的调用到一定的阈值，缺省是5秒内20次调用失败就会启动熔断机制（服务降级，熔断该节点微服务的调用，快速返回“错误”的响应信息）。当检测到该节点的微服务调用响应正常后 ， 恢复调用链路。

服务熔断：某个服务故障或者异常，直接熔断整个服务，而不是一直等到此服务超时；

服务降级：从整体负荷考虑，适当关闭某个服务。就是当某个服务熔断之后，服务器将不再被调用，此时客户端可以自己准备一个本地的fallback回调，返回一个缺省值，保证高可用；































