package com.lfyuoi.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.lfyuoi.maker.cli.meta.MateManager;
import com.lfyuoi.maker.cli.meta.Meta;
import com.lfyuoi.maker.generator.file.DynamicFileGenerator;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;

public class MainGenerator {

  public static void main(String[] args) throws TemplateException, IOException {
    Meta meat = MateManager.getMetaObject();
    System.out.println(meat);

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
    String outputBasePackage = meat.getBasePackage();
    //com/lfyuoi
    String outputBasePackagePath = StrUtil.join("/",StrUtil.split(outputBasePackage,"."));
    //generated/src/main/java/com/lfyuoi/xxx
    String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

    String inPutFilePath;
    String outPutFilePath;

    //model.DateModel
    inPutFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
    outPutFilePath = outputBaseJavaPackagePath + "/model/DateModel.java";
    DynamicFileGenerator.doGenerate(inPutFilePath,outPutFilePath,meat);
  }
}
