package com.lfyuoi.generator;

import com.lfyuoi.model.MainTemplateConfig;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

public class MainGenerator {

    public static void main(String[] args) throws TemplateException, IOException {
        //1.静态文件生成
        // 获取当前模块目录 code-generator-basic 路径
        String projectPath = System.getProperty("user.dir");
        // 获取根目录 code-generator 路径
        File parentFile = new File(projectPath).getParentFile();
        // 输入路径：ACM示例代码目录
        String inputPath = new File(projectPath, "code-generator-demo-projects/acm-template").getAbsolutePath();
        // 输出路径：code-generator-basic
        String outputPath =  new File(projectPath, "code-generator-basic").getAbsolutePath();
        StaticGenerator.copyFilesByRecursive(inputPath, outputPath);


        //2.动态文件生成
        // 当前idea打开的窗口
        String dynamicInputPath = projectPath + File.separator + "code-generator-basic/src/main/resources/templates/MainTemplateConfig.java.ftl";
        String dynamicOutputPath = projectPath + File.separator + "MainTemplate3.java";
        // 这次使用循环
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        mainTemplateConfig.setAuthor("lfyuoi");
        mainTemplateConfig.setOutputText("输出结果动态文件");
        mainTemplateConfig.setLoop(true);
        //调用生成器方法
        DynamicGenerator.doGenerate(dynamicInputPath,dynamicOutputPath,mainTemplateConfig);
    }
}
