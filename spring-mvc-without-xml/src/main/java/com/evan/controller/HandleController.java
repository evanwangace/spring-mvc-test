package com.evan.controller;

import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component("/test1.do") //BeanName
public class HandleController implements HttpRequestHandler {
    //默认会识别两种controller
    //一种以BeanName 声明URL的形式
    //一种是controller注解的形式

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("111");
    }
}
