package com.lfyuoi.generator;

import com.lfyuoi.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * 动态文件生成
 * @author lfyuoi
 */
public class DynamicGenerator {
    public static void main(String[] args) throws IOException , TemplateException {
        // 当前idea打开的窗口
        String projectPath = System.getProperty("user.dir")+ File.separator + "code-generator-basic";
        String inputPath = projectPath + File.separator + "src/main/resources/templates/MainTemplateConfig.java.ftl";
        String outputPath = projectPath + File.separator + "MainTemplate2.java";
        System.out.println(projectPath);
        System.out.println(outputPath);
        // 这次使用循环
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("lfyuoi");
        mainTemplateConfig.setOutputText("输出结果1");
        mainTemplateConfig.setLoop(false);
        //调用生成器方法
        doGenerate(inputPath,outputPath,mainTemplateConfig);

    }

    /**
     * @param inputPath  模板文件输入路径
     * @param outputPath 生成代码的输出路径
     * @param model      参数配置
     * @throws IOException
     * @throws TemplateException
     */
    public  static  void doGenerate(String inputPath,String outputPath,Object model) throws TemplateException, IOException {
        // new 出 Configuration 对象，参数为 FreeMarker 版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        // 指定模板文件所在的路径，模板文件的父级目录
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);


        // 设置模板文件使用的字符集
        configuration.setDefaultEncoding("utf-8");
        //设置数字格式：去除逗号分隔符 如2,023->2023
        configuration.setNumberFormat("0.######");

        // 创建模板对象，加载指定模板
        String templateName = new File(inputPath).getName();
        // 解决中文乱码问题
        Template template = configuration.getTemplate(templateName,"utf-8");
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(Paths.get(outputPath)), StandardCharsets.UTF_8));
        template.process(model, out);

        // 生成文件后别忘了关闭哦
        out.close();
    }
}
