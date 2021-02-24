#对该模块的理解

1.该模块中用到了很多依赖，其中有Redis、MongoDB、Jackson等，当前使用的是springboot框架，这些框架在使用时springboot都对其提供了支持。

    Spring Boot 提供了 Jackson 的自动配置，Jackson 是 spring-boot-starter-json 的一部分。当 Jackson 在类路径上时，会自动配置 ObjectMapper bean。

    MongoDB: spring-boot-starter-data-mongodb

    Redis: spring-boot-starter-data-redis 


2.定时任务在配置类上添加@EnableScheduling开启对定时任务的支持，在相应的方法上添加@Scheduled声明需要执行的定时任务。
其中Scheduled注解中有以下几个参数：

    (1)cron是设置定时执行的表达式，如 0 0/5 * * * ?每隔五分钟执行一次
    
    (2)zone表示执行时间的时区
    
    (3)fixedDelay 和fixedDelayString 表示一个固定延迟时间执行，上个任务完成后，延迟多长时间执行
    
    (4)fixedRate 和fixedRateString表示一个固定频率执行，上个任务开始后，多长时间后开始执行
    
    (5)initialDelay 和initialDelayString表示一个初始延迟时间，第一次被调用前延迟的时间

3.debug过程

在原项目中，IgnoreUrlsConfig类没有@Component注解，加上后在报错如下
    
    Description:
    The bean 'ignoreUrlsConfig', defined in class path resource 
    [com/jjr8112/mall/front_end/config/SecurityServerConfig.class], could not be registered. 
    A bean with that name has already been defined in file 
    [F:\MyMall\my-security-server\target\classes\com\jjr8112\mall\security\config\IgnoreUrlsConfig.class]
    and overriding is disabled.
    
    Action:
    Consider renaming one of the beans 
    or enabling overriding by setting spring.main.allow-bean-definition-overriding=true
因此去掉了@Component注解。
之后又有新的报错如下：