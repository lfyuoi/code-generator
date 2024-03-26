package com.lfyuoi.maker.template;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.lfyuoi.maker.meta.Meta;
import com.lfyuoi.maker.meta.enums.FileGenerateTypeEnum;
import com.lfyuoi.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;



/**
 * 模板制作工具
 */
public class TemplateMaker {
    /**
     * 制作模板
     * @param newMeta
     * @param originProjectPath
     * @param fileInputPath
     * @param modelInfo
     * @param searchStr
     * @param id
     * @return
     */
    private static long makeTemplate(Meta newMeta,String originProjectPath,String fileInputPath,Meta.ModelConfig.ModelInfo modelInfo,String searchStr, Long id){
        //没有 id 则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }
        //复制目录
        String projectPath = System.getProperty("user.dir");
        String tempPath = projectPath + File.separator + ".temp";
        String templatePath = tempPath +File.separator + id ;
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(templatePath);
            FileUtil.copy(originProjectPath,templatePath,true);
        }


        //一、输入信息
        //要挖坑的项目根目录
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();
        //Windows 系统需要对路径进行转义
        sourceRootPath = sourceRootPath.replaceAll("\\\\", "/");
        //要挖坑的文件
        String fileOutputPath = fileInputPath + "ftl";



        //二、使用字符串替换，生成模板文件
        String fileInputAbsolutePath = sourceRootPath + File.separator + fileInputPath;
        String fileOutputAbsolutePath = sourceRootPath + File.separator + fileOutputPath;

        String fileContent;
        //如果已有模板文件，表示不是第一次制作，则这原有文件的基础上再挖坑
        if (FileUtil.exist(fileOutputAbsolutePath)) {
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        } else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }

        String replacement = String.format("${%s}",modelInfo.getFieldName());
        String newFileContent = StrUtil.replace(fileContent,searchStr,replacement);
        //输出模板文件
        FileUtil.writeUtf8String(newFileContent,fileOutputAbsolutePath);

        //文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());
        fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());

        //三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        //已有meta文件，不是第一次制作，则在meta基础上进行修改
        if (FileUtil.exist(metaOutputPath)){
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath),Meta.class);

            //1.追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = oldMeta.getFileConfig().getFiles();
            fileInfoList.add(fileInfo);

            List<Meta.ModelConfig.ModelInfo> modelInfoList = oldMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            //配置去重
            oldMeta.getFileConfig().setFiles(distinctFile(fileInfoList));
            oldMeta.getModelConfig().setModels(distinctModels(modelInfoList));

            //2.输出元信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(oldMeta),metaOutputPath);

        }else {
            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);

            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.add(fileInfo);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);

            //2.输出原信息文件
            FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        }
        return id;
    }
    public static void main(String[] args) {
        //1.构造配置参数对象
        Meta meta = new Meta();
        meta.setName( "acm-template-pro-generator");
        meta.setDescription("ACM 示例模板生成器");

        //2.指定原始项目路径
        String projectPath = System.getProperty("user.dir");
        String originProjectPath = new File(projectPath).getParent() + File.separator + "code-generator-demo-projects/acm-template";
        String fileInputPath = "src/com/yupi/acm/MainTemplate.java";

        //3.输入模型参数信息
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("outputText");
//        modelInfo.setType("String");
//        modelInfo.setDefaultValue("Sum = ");

        //输入模型参数信息（第二次）
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("className");
        modelInfo.setType("String");

        //4.要替换的字符串
//        String searchStr = "Sum:";
        String searchStr = "MainTemplate";

        TemplateMaker.makeTemplate(meta,originProjectPath,fileInputPath,modelInfo,searchStr,1L);
    }

    /**
     * 文件去重
     * 
     * @param fileInfoList
     * @return
     */
    private static List<Meta.FileConfig.FileInfo> distinctFile(List<Meta.FileConfig.FileInfo> fileInfoList) {
        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>(fileInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (e, r) -> r)
                ).values());
        return newFileInfoList;
    }

    /**
     * 文件去重
     *
     * @param modelInfoList
     * @return
     */
    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        List<Meta.ModelConfig.ModelInfo> newModelInfoList = new ArrayList<>(modelInfoList.stream()
                .collect(
                        Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                ).values());
        return newModelInfoList;
    }
}
