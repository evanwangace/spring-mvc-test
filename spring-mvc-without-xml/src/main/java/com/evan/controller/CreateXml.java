package com.evan.controller;


import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author hskw
 * @version 1.0.0
 * @Description TODO
 * @createTime 2019-11-01
 */
public class CreateXml {

    public static void build01(HttpServletResponse response) {
        String tableName = "xxx";
        try {
            //DocumentHelper提供了创建Document对象的方法
            Document document = DocumentHelper.createDocument();
            //添加节点信息
            Element rootElement = document.addElement("modules");
            //这里可以继续添加子节点，也可以指定内容
            rootElement.setText("这个是module标签的文本信息");
            Element element = rootElement.addElement("module");
            Element nameElement = element.addElement("name");
            Element valueElement = element.addElement("value");
            Element descriptionElement = element.addElement("description");
            nameElement.setText("名称");
            nameElement.addAttribute("language", "java");//为节点添加属性值
            valueElement.setText("值");
            valueElement.addAttribute("language", "c#");
            descriptionElement.setText("描述");
            descriptionElement.addAttribute("language", "sql server");

            PrintWriter writer = response.getWriter();
            response.setCharacterEncoding("UTF-8");
//                response.setContentType("application/xml;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + tableName + ".xml");

            document.write(writer);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = servletRequestAttributes.getResponse();
        CreateXml.build01(response);
    }
}
