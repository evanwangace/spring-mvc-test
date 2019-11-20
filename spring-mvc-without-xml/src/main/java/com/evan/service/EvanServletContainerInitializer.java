package com.evan.service;

import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
//@HandlesTypes web容器把所有实现了webInit接口的类扫描出来，放在集合set中(Set<Class<?>> set)
//然后遍历set 把所有的实现类new出来，然后遍历执行所有方法。
//@HandlesTypes(WebInit.class)
public class EvanServletContainerInitializer implements ServletContainerInitializer {
    @Override
    public void onStartup(Set<Class<?>> set, ServletContext servletContext) throws ServletException {
        System.out.println("EvanServletContainerInitializer");
        /*List<WebInit> list = new ArrayList<>();
        for (Class<?> aClass : set) {
            try {
                list.add((WebInit) aClass.newInstance());
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        for (WebInit webInit : list) {
            webInit.start(servletContext);
        }*/
    }
}
