# Nebula-Spring-Cloud

#### 项目列表

Spring-Cloud-Config: 分布式配置中心 配置文件 Git 仓库项目

Spring-Cloud-Config-Server: 分布式配置中心 服务器端项目

Spring-Cloud-Config-Client: 分布式配置中心 客户端项目

Spring-Cloud-Eureka-Server: 服务治理 服务注册中心 服务器端项目
```
服务治理可以说是微服务架构中最为核心和基础的模块，它主要用来实现各个为服务实例的自动化注册与发现。
```

Spring-Cloud-Eureka-Client: 服务治理 服务提供者 客户端项目

Spring-Cloud-Eureka-Consumer: 服务治理 服务消费者 客户端项目

Spring-Cloud-Eureka-Ribbon-Consumer: 服务治理 Ribbon 服务消费者 客户端项目

Spring-Cloud-Eureka-Feign-Consumer: 服务治理 Feign 服务消费者 客户端项目

Spring-Cloud-Eureka-Ribbon-Hystrix-Consumer: 服务治理 Ribbon 服务消费者 Hystrix 服务容错保护（服务降级 依赖隔离 断路器） 客户端项目
```
服务降级

在微服务架构中，我们将系统拆分成了一个个的服务单元，各单元应用间通过服务注册与订阅的方式互相依赖。由于每个单元都在不同的进程中运行，依赖通过远程调用的方式执行，这样就有可能因为网络原因或是依赖服务自身问题出现调用故障或延迟，而这些问题会直接导致调用方的对外服务也出现延迟，若此时调用方的请求不断增加，最后就会出现因等待出现故障的依赖方响应而形成任务积压，线程资源无法释放，最终导致自身服务的瘫痪，进一步甚至出现故障的蔓延最终导致整个系统的瘫痪。如果这样的架构存在如此严重的隐患，那么相较传统架构就更加的不稳定。为了解决这样的问题，因此产生了断路器等一系列的服务保护机制。

针对上述问题，在Spring Cloud Hystrix中实现了线程隔离、断路器等一系列的服务保护功能。它也是基于Netflix的开源框架 Hystrix实现的，该框架目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程隔离、请求缓存、请求合并以及服务监控等强大功能。
```

```
依赖隔离

为每一个Hystrix命令创建一个独立的线程池，这样就算某个在Hystrix命令包装下的依赖服务出现延迟过高的情况，也只是对该依赖服务的调用产生影响，而不会拖慢其他的服务。

通过对依赖服务的线程池隔离实现，可以带来如下优势：

应用自身得到完全的保护，不会受不可控的依赖服务影响。即便给依赖服务分配的线程池被填满，也不会影响应用自身的额其余部分。
可以有效的降低接入新服务的风险。如果新服务接入后运行不稳定或存在问题，完全不会影响到应用其他的请求。
当依赖的服务从失效恢复正常后，它的线程池会被清理并且能够马上恢复健康的服务，相比之下容器级别的清理恢复速度要慢得多。
当依赖的服务出现配置错误的时候，线程池会快速的反应出此问题（通过失败次数、延迟、超时、拒绝等指标的增加情况）。同时，我们可以在不影响应用功能的情况下通过实时的动态属性刷新（后续会通过Spring Cloud Config与Spring Cloud Bus的联合使用来介绍）来处理它。
当依赖的服务因实现机制调整等原因造成其性能出现很大变化的时候，此时线程池的监控指标信息会反映出这样的变化。同时，我们也可以通过实时动态刷新自身应用对依赖服务的阈值进行调整以适应依赖方的改变。
除了上面通过线程池隔离服务发挥的优点之外，每个专有线程池都提供了内置的并发实现，可以利用它为同步的依赖服务构建异步的访问。
总之，通过对依赖服务实现线程池隔离，让我们的应用更加健壮，不会因为个别依赖服务出现问题而引起非相关服务的异常。同时，也使得我们的应用变得更加灵活，可以在不停止服务的情况下，配合动态配置刷新实现性能配置上的调整。

虽然线程池隔离的方案带了如此多的好处，但是很多使用者可能会担心为每一个依赖服务都分配一个线程池是否会过多地增加系统的负载和开销。对于这一点，使用者不用过于担心，因为这些顾虑也是大部分工程师们会考虑到的，Netflix在设计Hystrix的时候，认为线程池上的开销相对于隔离所带来的好处是无法比拟的。同时，Netflix也针对线程池的开销做了相关的测试，以证明和打消Hystrix实现对性能影响的顾虑。
```

```
断路器

断路器模式源于Martin Fowler的Circuit Breaker一文。“断路器”本身是一种开关装置，用于在电路上保护线路过载，当线路中有电器发生短路时，“断路器”能够及时的切断故障电路，防止发生过载、发热、甚至起火等严重后果。

在分布式架构中，断路器模式的作用也是类似的，当某个服务单元发生故障（类似用电器发生短路）之后，通过断路器的故障监控（类似熔断保险丝），直接切断原来的主逻辑调用。但是，在Hystrix中的断路器除了切断主逻辑的功能之外，还有更复杂的逻辑，下面我们来看看它更为深层次的处理逻辑。

我们来说说断路器的工作原理。当我们把服务提供者 Spring-Cloud-Eureka-Client 中加入了模拟的时间延迟之后，在服务消费端的服务降级逻辑因为hystrix命令调用依赖服务超时，触发了降级逻辑，但是即使这样，受限于Hystrix超时时间的问题，我们的调用依然很有可能产生堆积。

这个时候断路器就会发挥作用，那么断路器是在什么情况下开始起作用呢？这里涉及到断路器的三个重要参数：快照时间窗、请求总数下限、错误百分比下限。这个参数的作用分别是：

快照时间窗：断路器确定是否打开需要统计一些请求和错误数据，而统计的时间范围就是快照时间窗，默认为最近的10秒。
请求总数下限：在快照时间窗内，必须满足请求总数下限才有资格根据熔断。默认为20，意味着在10秒内，如果该hystrix命令的调用此时不足20次，即时所有的请求都超时或其他原因失败，断路器都不会打开。
错误百分比下限：当请求总数在快照时间窗内超过了下限，比如发生了30次调用，如果在这30次调用中，有16次发生了超时异常，也就是超过50%的错误百分比，在默认设定50%下限情况下，这时候就会将断路器打开。
那么当断路器打开之后会发生什么呢？我们先来说说断路器未打开之前，对于之前那个示例的情况就是每个请求都会在当hystrix超时之后返回fallback，每个请求时间延迟就是近似hystrix的超时时间，如果设置为5秒，那么每个请求就都要延迟5秒才会返回。当熔断器在10秒内发现请求总数超过20，并且错误百分比超过50%，这个时候熔断器打开。打开之后，再有请求调用的时候，将不会调用主逻辑，而是直接调用降级逻辑，这个时候就不会等待5秒之后才返回fallback。通过断路器，实现了自动地发现错误并将降级逻辑切换为主逻辑，减少响应延迟的效果。

在断路器打开之后，处理逻辑并没有结束，我们的降级逻辑已经被成了主逻辑，那么原来的主逻辑要如何恢复呢？对于这一问题，hystrix也为我们实现了自动恢复功能。当断路器打开，对主逻辑进行熔断之后，hystrix会启动一个休眠时间窗，在这个时间窗内，降级逻辑是临时的成为主逻辑，当休眠时间窗到期，断路器将进入半开状态，释放一次请求到原来的主逻辑上，如果此次请求正常返回，那么断路器将继续闭合，主逻辑恢复，如果这次请求依然有问题，断路器继续进入打开状态，休眠时间窗重新计时。

通过上面的一系列机制，hystrix的断路器实现了对依赖资源故障的端口、对降级策略的自动切换以及对主逻辑的自动恢复机制。这使得我们的微服务在依赖外部服务或资源的时候得到了非常好的保护，同时对于一些具备降级逻辑的业务需求可以实现自动化的切换与恢复，相比于设置开关由监控和运维来进行切换的传统实现方式显得更为智能和高效。
```

Spring-Cloud-Hystrix-Dashboard: Hystrix 监控面板

### Reference Project
spring-cloud-examples
https://github.com/ityouknow/spring-cloud-examples

SpringCloud-Learning
https://github.com/dyc87112/SpringCloud-Learning

#### Reference Site
Spring Cloud 官网
http://projects.spring.io/spring-cloud

http://blog.didispace.com/Spring-Cloud基础教程

### Reference Blog
纯洁的微笑
http://www.ityouknow.com/spring-cloud

程序猿DD
http://blog.didispace.com/categories/Spring-Cloud

liaokailin的专栏
http://blog.csdn.net/liaokailin/article/category/6212338

周立 Spring Cloud
http://www.itmuch.com

方志朋 Spring Cloud 专栏
http://blog.csdn.net/column/details/15197.html

许进 跟我学Spring Cloud
http://xujin.org/categories/跟我学Spring-Cloud

#### Spring Cloud Edgware
虽然在 Edgware 版中，原 Artifact ID 依然可用，但一旦 Spring Cloud Finchley 发布，老的 Artifact ID 将会废弃。

以下是 Spring Cloud Edgware 及之前版本中 Starter 改名前后的映射表：
```
spring-cloud-starter-eureka-server —> spring-cloud-starter-netflix-eureka-server
spring-cloud-starter-eureka —> spring-cloud-starter-netflix-eureka-client
spring-cloud-starter-ribbon —> spring-cloud-starter-netflix-ribbon
spring-cloud-starter-hystrix —> spring-cloud-starter-netflix-hystrix
spring-cloud-starter-hystrix-dashboard —> spring-cloud-starter-netflix-hystrix-dashboard
spring-cloud-starter-turbine —> spring-cloud-starter-netflix-turbine
spring-cloud-starter-turbine-stream --> spring-cloud-starter-netflix-turbine-stream
spring-cloud-starter-feign —> spring-cloud-starter-openfeign
spring-cloud-starter-zuul —> spring-cloud-starter-netflix-zuul
```