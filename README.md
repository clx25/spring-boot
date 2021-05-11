## learn-simple
spring-boot相关
## learn-mybatis
mybatis-spring-boot相关
## @SpringBootApplication
包含的注解及作用
[![gd3DM9.png](https://z3.ax1x.com/2021/05/12/gd3DM9.png)](https://imgtu.com/i/gd3DM9)
## @ConfigurationProperties
把配置文件中属性自动注入
```flow
op1=>operation: @EnableConfigurationProperties
op2=>operation: @Import(EnableConfigurationPropertiesRegistrar.class)
op3=>operation: 导入EnableConfigurationPropertiesRegistrar
op4=>operation: 把ConfigurationPropertiesBindingPostProcessor
和ConfigurationPropertiesBinder注册到BeanDefinition
op5=>operation: 在bean初始化过程中执行ConfigurationPropertiesBindingPostProcessor
并通过ConfigurationPropertiesBinder绑定数据
op1->op2->op3->op4->op5
```