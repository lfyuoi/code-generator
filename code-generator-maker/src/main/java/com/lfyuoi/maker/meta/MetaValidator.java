package com.lfyuoi.maker.meta;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateException;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.lfyuoi.maker.meta.Meta.FileConfig;
import com.lfyuoi.maker.meta.Meta.FileConfig.FileInfo;
import com.lfyuoi.maker.meta.Meta.ModelConfig;
import com.lfyuoi.maker.meta.enums.FileGenerateTypeEnum;
import com.lfyuoi.maker.meta.enums.FileTypeEnum;
import com.lfyuoi.maker.meta.enums.ModelTypeEnum;
import java.io.File;
import java.nio.file.Paths;
import java.util.List;

public class MetaValidator {

  public static void doValidate(Meta meta) {
    validAndFillMetaRoot(meta);

    validAndFillFillConfig(meta);

    validAndFillModelConfig(meta);

  }

  private static void validAndFillModelConfig(Meta meta) {
    // modelConfig 校验和默认值
    ModelConfig modelConfig = meta.getModelConfig();
    if (modelConfig == null) {
      return;
    }
    List<ModelConfig.ModelInfo> modelInfoList = modelConfig.getModels();
    if (CollectionUtil.isEmpty(modelInfoList)) {
      return;
    }
    for (ModelConfig.ModelInfo modelInfo : modelInfoList) {
      // 输出路径默认值
      String fieldName = modelInfo.getFieldName();
      if (StrUtil.isBlank(fieldName)) {
        throw new MetaException("未填写 fieldName");
      }

      String modelInfoType = modelInfo.getType();
      if (StrUtil.isEmpty(modelInfoType)) {
        modelInfo.setType(ModelTypeEnum.STRING.getValue());
      }
    }
  }

  private static void validAndFillFillConfig(Meta meta) {
    //fileConfig 校验和默认值
    FileConfig fileConfig = meta.getFileConfig();
    if (fileConfig == null) {
      return;
    }
    //sourceRootPath 必填项
    String sourceRootPath = fileConfig.getSourceRootPath();
    if (StrUtil.isBlank(sourceRootPath)) {
      throw new MetaException("未填写 sourceRootPath");
    }
    // inputRootPath: .source + sourceRootPath 的最后一个层级路径
    String inputRootPath = fileConfig.getInputRootPath();
    String defaultInputRootPath = ".source/" +
        FileUtil.getLastPathEle(Paths.get(Paths.get(sourceRootPath).getFileName().toString()));
    if (StrUtil.isEmpty(inputRootPath)) {
        fileConfig.setInputRootPath(defaultInputRootPath);
    }

    // outputRootPath: 默认为当前路径下的generated
    String outputRootPath = fileConfig.getOutputRootPath();
    String defaultOutputRootPath = "generated";
    if (StrUtil.isEmpty(outputRootPath)) {
      fileConfig.setOutputRootPath(defaultOutputRootPath);
    }

    String fileConfigType = fileConfig.getType();
    String defaultFileConfigType = FileTypeEnum.DIR.getValue();
    if (StrUtil.isEmpty(fileConfigType)) {
      fileConfig.setType(defaultFileConfigType);
    }

    List<FileInfo> fileInfoList = fileConfig.getFiles();
    if (CollUtil.isEmpty(fileInfoList)) {
      return;
    }
    for (FileInfo fileInfo : fileInfoList) {
      //inputPath 必填项
      String inputPath = fileInfo.getInputPath();
      if (StrUtil.isEmpty(inputPath)) {
        throw new DateException("未填写 inputPath");
      }
      String outputPath = fileInfo.getOutputPath();
      if (StrUtil.isEmpty(outputPath)) {
        fileInfo.setOutputPath(inputPath);
      }
      // 默认为 dir
      String type = fileInfo.getType();
      if(StrUtil.isBlank(type)){
        // 判断是否有后缀 有后缀就是文件 否则就是目录
        if(StrUtil.isBlank(FileUtil.getSuffix(inputPath))){
          fileInfo.setType(FileTypeEnum.DIR.getValue());
        }else {
          fileInfo.setType(FileTypeEnum.FILE.getValue());
        }
      }
      String generateType = fileInfo.getGenerateType();
      // 如果有 .ftl 后缀 则是模板文件 Dynamic 否则就是 Static
      if(StrUtil.isBlank(generateType)){
        if(inputPath.endsWith(".ftl")){
          fileInfo.setType(FileGenerateTypeEnum.DYNAMIC.getValue());
        }else {
          fileInfo.setType(FileGenerateTypeEnum.STATIC.getValue());
        }
      }
    }
  }

  private static void validAndFillMetaRoot(Meta meta) {
    //基础信息校验和默认值
    String name = StrUtil.blankToDefault(meta.getName(), "my-generator");
    String description = StrUtil.emptyToDefault(meta.getDescription(), "我的模板代码生成器");
    String author = StrUtil.emptyToDefault(meta.getAuthor(), "lfyuoi");
    String basePackage = StrUtil.blankToDefault(meta.getBasePackage(), "com.lfyuoi");
    String version = StrUtil.emptyToDefault(meta.getVersion(), "1.0");
    String createTime = StrUtil.emptyToDefault(meta.getCreateTime(), DateUtil.now());
    meta.setName(name);
    meta.setDescription(description);
    meta.setAuthor(author);
    meta.setBasePackage(basePackage);
    meta.setVersion(version);
    meta.setCreateTime(createTime);
  }
}
