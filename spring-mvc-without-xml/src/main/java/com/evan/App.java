/*
package com.evan;

import org.apache.catalina.Context;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.startup.Tomcat;

*/
/**
 * @ClassName App
 * @Description
 * @Author EvanWang
 * @Version 1.0.0
 * @Date 2019/11/19 11:25
 *//*

public class App {
    public static void main(String[] args) throws Exception {
        //内嵌tomcat
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(80);
        //现在找不到静态资源，如果指向我们当前项目，则可以访问到静态资源
        Context context = tomcat.addContext("/", System.getProperty("java.io.tmpdir"));
        //addContext和addWebapp的区别：只会去初始化一个 context的资源目录(项目) 并不会加载 web的生命周期
        // Tomcat的文件夹webapps，目录内是我们的项目
        // 有两种方式启动项目：1.war  2.文件夹
        //tomcat.addWebapp("/","C:\\Program Files\\pro\\public-luban-project\\spring-mvc\\src\\main\\webapp");
        //手动添加生命周期监听器
        context.addLifecycleListener((LifecycleListener) Class.forName(tomcat.getHost().getConfigClass()).newInstance());
        tomcat.start();
        //挂起
        tomcat.getServer().await();

        //传一个xml文件进去
//        ClassPathXmlApplicationContext
//                classPathXmlApplicationContext = new ClassPathXmlApplicationContext();

    }
}
*/
