package com.lfyuoi.generator;

import com.lfyuoi.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicGenerator {
    public static void main(String[] args) throws IOException , TemplateException {
        // Step2. 在Test方法中创建一个FreeMarker的全局配置对象，可以统一指定模板文件所在的路径、模板文件的字符集等
        // 不要自己手写，在官方文档里直接CV大法
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        //绝对路径
        String projectPath = System.getProperty("user.dir") + File.separator + "code-generator-basic";
        System.out.println(projectPath);
        File parentFile = new File(projectPath);
        // 指定模板文件所在的路径
        configuration.setDirectoryForTemplateLoading(new File(parentFile,"src/main/resources/templates"));

        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        //2,023->2023
        configuration.setNumberFormat("0.######");

        // Step3. 将准备1の《代码生成器官网》FreeMarker模板保存在src/main/resources/templates目录下的myweb.html.ftl文件里
        // 创建模板对象，加载指定模板
        Template template = configuration.getTemplate("MainTemplateConfig.java.ftl");

        // Step4. 创建数据模型，指定当前的年份和导航栏菜单项
        // Tips: 要保证数据的质量和规范性，可以用参数配置类来传递
        // 反之，灵活构造可以选用HashMap集合
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("lfyuoi");
        mainTemplateConfig.setOutputText("输出结果1");
        mainTemplateConfig.setLoop(false);

        // Step5. 指定生成的文件
        Writer out = new FileWriter("MainTemplateConfig.java.ftl");
        // Step6. 调用process方法，处理并生成文件
        template.process(mainTemplateConfig, out);

        // 生成文件后别忘了关闭哦
        out.close();
    }

}
