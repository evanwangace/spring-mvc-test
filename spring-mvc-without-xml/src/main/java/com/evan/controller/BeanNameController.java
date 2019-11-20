package com.evan.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component("/user.do")  //<bean  beanName="/getUser"></bean>
// Spring MVC 2种类型的Controller  注解  BeanName    3种方式实现
//BeanName 2大类型的controller 3种方式实现Controller
public class BeanNameController implements Controller {


    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        System.out.println("beanName");
        return null;
    }
}
