package com.evan.servlet;

import com.evan.annotation.Controller;
import com.evan.annotation.RequestMapping;
import com.evan.annotation.ResponseBody;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName EvanDispatcherServlet
 * @Description
 * @Author EvanWang
 * @Version 1.0.0
 * @Date 2019/11/14 23:38
 */
public class EvanDispatcherServlet extends HttpServlet {

    private static String COMPONENT_SCAN_ELEMENT_PACKAGE_NAME = "package";

    private static String COMPONENT_SCAN_ELEMENT_NAME = "componentScan";

    private static String XML_PATH_LOCAL = "xmlPathLocal";

    private static String prefix = "";
    private static String suffix = "";
    //获取类加载的根路径 "/F:/project/imitate-mvc/target/classes/"
    private static String projectPath = EvanDispatcherServlet.class.getResource("/").getPath();
    //存放URI和其对应的Method
    private static Map<String, Method> methodMap = new HashMap<>();
    //存放method和声明方法的class的对象。
    private static Map<String, Object> controllerInstanceMap = new HashMap<>();


    @Override
    public void init(ServletConfig config) throws ServletException {
        projectPath = projectPath.replaceAll("%20", " ");
        //获取由tomcat解析的web.xml中，xmlPathLocal对应的属性值。
        String initParaValue = config.getInitParameter(XML_PATH_LOCAL);
        //解析xml文件，evanMvc.xml路径绝对路径
        File mvcXml = new File(projectPath + initParaValue);
        //因为我们要使用dom4j解析xml，先将文件对象转化成Document。
        Document document = parseFileToDocument(mvcXml);
        //根元素<beans></beans>
        Element rootElement = document.getRootElement();
        //获取<view></view>中的值
        Element viewElement = rootElement.element("view");
        prefix = viewElement.attribute("prefix").getValue();
        suffix = viewElement.attribute("suffix").getValue();
        //获取<componentScan></componentScan>的值
        Element componentScanElement = rootElement.element(COMPONENT_SCAN_ELEMENT_NAME);
        String packageValue = componentScanElement.attribute(COMPONENT_SCAN_ELEMENT_PACKAGE_NAME).getValue();
        // 当前com文件夹的路径"/F:/project/imitate-mvc/target/classes/com"
        scanProjectByPath(projectPath + packageValue);

    }

    /**
     * @param file ：你的xml文件对象
     * @return
     */
    public Document parseFileToDocument(File file) {
        SAXReader saxReader = new SAXReader();
        try {
            return saxReader.read(file);
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return null;
    }

    //递归解析项目所有文件 目的：最终扫描出我们定义定的TestController
    public void scanProjectByPath(String path) {
        File file = new File(path);
        scanControllerFile(file);
    }

    public void scanControllerFile(File file) {
        //递归解析项目
        if (file.isDirectory()) {
            for (File insideFile : file.listFiles()) {
                scanControllerFile(insideFile);
            }
        } else {
            //如果不是文件夹，注意：这里在处理时候会出现\\的转义问题
            //当前会得到的路径(filePath)：F:\project\imitate-mvc\target\classes\com\evan\annotation\TestController.class
            //最终我们希望得到：com.evan.controller.TestController
            String filePath = file.getPath();
            //截取文件后缀，判断是否类.class文件。
            String suffix = filePath.substring(filePath.lastIndexOf("."));
            if (suffix.equals(".class")) {
                //替换得到 com\evan\controller\TestController.class
                String classPath = filePath.replace(new File(projectPath).getPath() + "\\", "");
                //替换得到com.evan.controller.TestController.class
                classPath = classPath.replaceAll("\\\\", ".");
                String className = classPath.substring(0, classPath.lastIndexOf("."));
                handleControllerClass(className);
            }

        }
    }

    private void handleControllerClass(String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (clazz.isAnnotationPresent(Controller.class)) {
                RequestMapping classRequestMapping = clazz.getAnnotation(RequestMapping.class);
                String classRequestMappingUrl = "";
                if (classRequestMapping != null) {
                    classRequestMappingUrl = classRequestMapping.value();
                }
                for (Method method : clazz.getDeclaredMethods()) {
                    //判断是不是合成方法
                    if (!method.isSynthetic()) {
                        RequestMapping annotation = method.getAnnotation(RequestMapping.class);
                        if (annotation != null) {
                            String methodRequestMappingUrl;
                            methodRequestMappingUrl = annotation.value();
                            //打印下结果
                            System.out.println("类:" + clazz.getName() + "的" + method.getName() + "方法被映射到了" + classRequestMappingUrl + methodRequestMappingUrl + "上面");
                            //将指定url对应的method放入methodMap。
                            methodMap.put(classRequestMappingUrl + methodRequestMappingUrl, method);
                            controllerInstanceMap.put(method.getName(), clazz.newInstance());
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //为了方便我们在doGet中，调用doPost
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //拿到请求的URI
        String requestURI = req.getRequestURI();
        //通过请求的URI拿到对应的method
        Method method = methodMap.get(requestURI);
        if (method != null) {
            //形参数组，jdk8以前直接拿参数名称拿不到
            //javac -parameters
            //操作字节码文件
            Parameter[] formalParameters = method.getParameters();
            //实参数组
            Object[] actualParameters = new Object[formalParameters.length];
            //对参数做简单处理
            for (int index = 0; index < formalParameters.length; index++) {
                Parameter parameter = formalParameters[index];
                String name = parameter.getName();
                Class type = parameter.getType();
                if (type.equals(String.class)) {
                    actualParameters[index] = req.getParameter(name);
                } else if (type.equals(HttpServletRequest.class)) {
                    actualParameters[index] = req;
                } else if (type.equals(HttpServletResponse.class)) {
                    actualParameters[index] = resp;
                } else {
                    try {
                        //根据类型创建对象
                        Object paramObject = type.newInstance();
                        for (Field field : type.getDeclaredFields()) {
                            field.setAccessible(true);
                            String fieldName = field.getName();
                            field.set(paramObject, req.getParameter(fieldName));
                        }
                        actualParameters[index] = paramObject;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                Object controller;
                //获得声明当前方法的controller对象
                controller = controllerInstanceMap.get(method.getName());
                //TestController里的方法返回值
                Object returnValue = method.invoke(controller, actualParameters);
                // 判断返回值是否是Void
                if (!method.getReturnType().equals(Void.class)) {
                    ResponseBody annotation = method.getAnnotation(ResponseBody.class);
                    if (annotation != null) {
                        //这里应该处理将对象转化成json字符串的格式，时间有限就暂时省略了，有兴趣的小伙伴可以实现下。
                        resp.getWriter().write(String.valueOf(returnValue));
                    } else {
                        // /page/index.html
                        req.getRequestDispatcher(prefix + returnValue + suffix).forward(req, resp);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            resp.setStatus(404);
        }
    }
}
