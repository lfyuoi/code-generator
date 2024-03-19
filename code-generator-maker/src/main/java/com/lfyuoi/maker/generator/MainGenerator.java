package com.lfyuoi.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.lfyuoi.maker.meta.MateManager;
import com.lfyuoi.maker.meta.Meta;
import com.lfyuoi.maker.generator.file.DynamicFileGenerator;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;

public class MainGenerator {

  public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
    Meta meta = MateManager.getMetaObject();
    System.out.println(meta);

    //输出的根路径
    String projectPath = System.getProperty("user.dir");
    String outputPath = projectPath + File.separator + "generator";
    if (!FileUtil.exist(outputPath)) {
      FileUtil.mkdir(outputPath);
    }

    //读取resource目录
    ClassPathResource classPathResource = new ClassPathResource("");
    String inputResourcePath = classPathResource.getAbsolutePath();

    //Java包的基础路径
    //com.lfyuoi
    String outputBasePackage = meta.getBasePackage();
    //com/lfyuoi
    String outputBasePackagePath = StrUtil.join("/",StrUtil.split(outputBasePackage,"."));
    //generated/src/main/java/com/lfyuoi/xxx
    String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

    String inputFilePath;
    String outputFilePath;

    //model.DateModel
    inputFilePath = inputResourcePath + File.separator + "templates/java/model/DateModel.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + "/model/DateModel.java";
    DynamicFileGenerator.doGenerate(inputFilePath,outputFilePath,meta);

    // command ConfigCommand
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "ConfigCommand.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/ConfigCommand.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // command GenerateCommand
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "GenerateCommand.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/GenerateCommand.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


    // command ListCommand
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command" + File.separator + "ListCommand.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/command/ListCommand.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


    // util ConsoleUtil
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ConsoleUtil.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ConsoleUtil.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


    // util ReflexUtil
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ReflexUtil.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ReflexUtil.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


    // util ConvertUtil
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli/util" + File.separator + "ConvertUtil.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/util/ConvertUtil.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);



    // cli CommandExecutor
    inputFilePath = inputResourcePath + File.separator + "templates/java/cli" + File.separator + "CommandExecutor.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/cli/CommandExecutor.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);


    // java Main
    inputFilePath = inputResourcePath + File.separator + "templates/java" + File.separator + "Main.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/Main.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // generator StaticGenerator
    inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "StaticGenerator.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/StaticGenerator.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // generator DynamicGenerator
    inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "DynamicGenerator.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/DynamicGenerator.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // generator MainGenerator
    inputFilePath = inputResourcePath + File.separator + "templates/java/generator" + File.separator + "MainGenerator.java.ftl";
    outputFilePath = outputBaseJavaPackagePath + File.separator + "/generator/MainGenerator.java";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // pom pom.xml
    inputFilePath = inputResourcePath + File.separator + "templates" + File.separator + "pom.xml.ftl";
    outputFilePath = outputPath + File.separator + "pom.xml";
    DynamicFileGenerator.doGenerate(inputFilePath, outputFilePath, meta);

    // 构建jar包
    JarGenerator.doGenerate(outputPath);

    // 封装脚本
    String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(),meta.getVersion());
    ScriptGenerator.doGenerate(outputPath + File.separator + "generator", "target/" + jarName);
  }
}
