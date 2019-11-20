package com.evan.init;

import com.evan.config.AppConfig;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;

//https://docs.spring.io/spring/docs/5.2.1.RELEASE/spring-framework-reference/web.html#mvc-servlet
public class MyWebApplicationInitializer implements WebApplicationInitializer {


    //0配置原理
    //servlet 3.0 SPI规范
    //AppConfig
    //内嵌Tomcat
    //自己实现SPI.. 在项目根路径下定义META-INFO文件夹，然后在文件夹下定义services，然后在里面定义一个文件名为
    //你要实现的接口，里面内容写上接口的实现类。然后由web容器去解析到你编写的实现类。

    // 为什么 spring boot  能解析 非 web app 目录下的资源


    //=>实现0xml
    //写一个类 实现spring 的接口：WebApplicationInitializer


    //tomcat 启动的时候会调用 onStartup方法 为什么？

    //传入一个ServletContext ： web上下文对象   web.xml能做的 ServletContext都能做
    //因为servlet 3.0的一个新规范,跟tomcat没关系，tomcat是规范的实现者之一。
    // 为什么不是tomcat规范而是servlet规范？因为市面上有很多web容器，例如jetty。如果你是web容器的规范，如果换了容器，代码将不再适用。
    //SPI "你"=>这里指的是spring
    @Override
    public void onStartup(ServletContext servletCxt) {
        //初始化spring容器  以注解的方式
        AnnotationConfigWebApplicationContext ac = new AnnotationConfigWebApplicationContext();
        //将配置类注册进spring容器
        ac.register(AppConfig.class);
//        ac.setServletContext(servletCxt);
//        ac.refresh();
        DispatcherServlet servlet = new DispatcherServlet(ac);
        ServletRegistration.Dynamic registration = servletCxt.addServlet("app", servlet);
        registration.setLoadOnStartup(1);
//        registration.setInitParameter("contextConfigLocation","spring mvc.xml 的地址");
        registration.addMapping("*.do");
    }
}