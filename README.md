# 项目前置介绍

> Spring的目的在于使JAVA EE开发更加容易，个人认为其核心在于IOC以及AOP。IOC是一种理念，将对象的创建权交给Spring，而AOP是一个模块，实现面向切面编程，比较接近一种动态增强。个人将着重IOC、AOP的相关功能进行实现。

个人学习的Spring相关知识将放在[dailyNote仓库](https://github.com/yato-sama-sword/dailyNote/blob/main/my-spring.md)

# 思路详解

## 1.在容器中存储、获取Bean和BeanDefinition

> 采用map的方法保存Bean、BeanDefinition。在此基础上就需要先定义BeanDefinition，项目中的BeanDefinition只保留了Bean的Class信息。

在Spring源码中，DefaultListableBeanFactory通过直接或间接实现SingletonBeanRegistry、BeanFactory、BeanDefinitionRegistry接口来获取以上功能，项目中仿照这一形式进行，提供注册、获取Bean，注册、获取BeanDefinition的方法。

## 2.实现Bean的生命周期

> 一般来说要使得Bean可以使用需要经历实例化、属性赋值、初始化、销毁的过程。此中过程在调用Bean自身方法的基础上，还需要进行BeanPostProcessor以及Aware接口的增强处理。

这里简单讲讲实例化、属性赋值、初始化的过程

1. 实例化：类似于调用了无参构造函数，只开辟了内存空间，但是没有存储真实的数据

2. 属性赋值：向Bean对象的属性注入真实的数据

3. 初始化：进行动态增强（其实属性赋值也可以属于初始化的一部分，只是分开逻辑会更清晰）

4. 销毁：GC—对象、卸载—类

### 2.1.实例化策略

> 如何进行Bean的实例化？对应JVM的类加载过程，Bean的实例化应该可以看作是初始、验证、准备、解析、初始化的一整套流程。选取反射和Cglib动态代理的方法

在Spring源码中有InstantiationStrategy接口来对实例化策略进行一个规范，项目中实现两个实现类，一个使用反射调用无参构造函数，而另一个采用Cglib动态代理反射生成。

### 2.2.属性赋值

> 想要动态的修改属性的值，可以很迅速的联想到反射。但是这里引申出一些问题，属性值从哪里获取（或者说资源文件要怎么读取）；著名循环依赖问题怎么解决。

1. 考虑属性赋值本身，使用反射就可以简单实现，这里我选择调用hutool中的BeanUtil中的setFeildValue方法，其实质就是通过反射来修改属性值

2. 考虑资源获取，Spring提供Resource接口，提供资源的抽象和访问功能，实现文件系统资源、classpath下资源、java.net.URL资源进行定位的实现类；ResourceLoader接口提供资源查找定位策略的抽象，Spring中的DefaultResourceLoader类是该接口的默认实现类。此外需要指出，一般bean的配置是在xml文件中进行的，所以我们要提供在xml文件中操纵bean的相关方法。BeanDefinitionReader是Spring定义的读取bean元信息的抽象接口，xmlBeanDefinitionReader则是从xml文件中读取的实现类，并且负责将有关BeanDefinition注册到容器中。

3. 考虑循环依赖，这一点毕竟复杂后面单独再说

## 2.3.初始化

> Bean的初始化完成意味着可以正式的使用Bean，在初始化过程中既可以使用定义好的Bean自身的相关方法，也可以使用BeanPostProcessor进行动态增强，

BeanFactoryPostProcessor允许在bean实例化之前修改BeanDefinition信息，严格上不算初始化这一章的，但是提一下可以和BeanPostProcessor作对比

BeanPostProcessor可以在bean实例化后修改或替换bean，有两个实现方法，前者可以在初始化方法前执行，后者可以在初始化方法后执行

项目中实现ApplicationContext在xml文件中配置有关接口，避免手动添加。

# 3.考虑增强功能具体实现

> 在前面也提过一嘴BeanFactoryPostProcessor、BeanPostProcessor、Aware等接口的实现。具体怎么实现，有哪些功能上的提升？此节细说！



# 杂项

1. 从xml文件中读取信息为String类型，需要实现类型转换的功能