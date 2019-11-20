package com.evan.arg;

import com.evan.annocation.UserParam;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class ArgumentResolver implements HandlerMethodArgumentResolver {

    //判断你的参数是否需要由当前 适配器来处理
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        //判断当前参数如果是String
        return parameter.hasParameterAnnotation(UserParam.class);
    }


    //给这个参数赋的值
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        //通过Reques.getParameter();
        //request.getSession

        //从redis 里面获取....


        System.out.println(1);
        return "loginUser";
    }
}
