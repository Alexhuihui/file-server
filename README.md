# file-server

### questions
1. Failed to configure a DataSource: 'url' attribute is not specified and no embedded datasource could be configured.
> 问题分析及解决方案
> 问题原因: Mybatis没有找到合适的加载类,其实是大部分spring - datasource - url没有加载成功,分析原因如下所示.
> ataSourceAutoConfiguration会自动加载.
  
> 没有配置spring - datasource - url 属性.
  
> spring - datasource - url 配置的地址格式有问题.
  
> 配置 spring - datasource - url的文件没有加载.
> 在启动类上添加 @EnableAutoConfiguration(exclude={DataSourceAutoConfiguration.class})


