# 对该模块的理解
通用模块将一些开发中都需要的东西抽出后进行封装，其他模块中用到时可以直接调用，而且这种封装通常情况下下是合理且方便的。在我的认知里，通常最需要封装的一般为对数据的校验模块、日志模块及异常处理模块，该项目的作者加入了redis用作缓存，所以对redis的配置也应当加入到这个通用模块中。

# 几点疑问
1.mall项目作者在该项目中并没有加入对数据对象的基本校验，项目的后端技术介绍中提到了使用了Hibernator-Validator的验证框架，但在项目与子项目的pom文件中均为找到，对此感到疑惑。

**spring-boot-starter-web和spring-boot-starter-validation两个springboot包中均含有hibernate-validator，所以在开发中有3个包中的任意一个即可实现校验**

具体校验内容：

JSR提供的校验注解：         
@Null   被注释的元素必须为 null    
@NotNull    被注释的元素必须不为 null    
@AssertTrue     被注释的元素必须为 true    
@AssertFalse    被注释的元素必须为 false    
@Min(value)     被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@Max(value)     被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@DecimalMin(value)  被注释的元素必须是一个数字，其值必须大于等于指定的最小值    
@DecimalMax(value)  被注释的元素必须是一个数字，其值必须小于等于指定的最大值    
@Size(max=, min=)   被注释的元素的大小必须在指定的范围内    
@Digits (integer, fraction)     被注释的元素必须是一个数字，其值必须在可接受的范围内    
@Past   被注释的元素必须是一个过去的日期    
@Future     被注释的元素必须是一个将来的日期    
@Pattern(regex=,flag=)  被注释的元素必须符合指定的正则表达式    


Hibernate Validator提供的校验注解：  
@NotBlank(message =)   验证字符串非null，且trim后长度必须大于0    
@Email  被注释的元素必须是电子邮箱地址    
@Length(min=,max=)  被注释的字符串的大小必须在指定的范围内    
@NotEmpty   被注释的字符串的必须非空    
@Range(min=,max=,message=)  被注释的元素必须在合适的范围内

2.作者将生成需求文档的工具swagger的配置也放入了通用模块，理解不了。

