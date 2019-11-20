package com.evan.controller;

import com.evan.annotation.Controller;
import com.evan.annotation.RequestMapping;
import com.evan.annotation.ResponseBody;
import com.evan.entity.UserEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @ClassName TestController
 * @Description
 * @Author EvanWang
 * @Version 1.0.0
 * @Date 2019/11/14 23:37
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @RequestMapping("/show.do")
    @ResponseBody
    public Object show(String content, HttpServletRequest request, HttpServletResponse response, UserEntity userEntity){
        System.out.println(content);
        System.out.println(request);
        System.out.println(response);
        System.out.println(userEntity);
        return  "Show is successful.";

    }

    @RequestMapping("/view.do")
    public Object view(){
        return "index";
    }
}
