package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.cli.util.ReflexUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DateModel;
import lombok.Data;
import lombok.SneakyThrows;
import picocli.CommandLine;

/**
 * @author ${author}
 * @date ${.now}
 * @description 生成命令
 */
@Data
@CommandLine.Command(name = "generate", mixinStandardHelpOptions = true, description = "生成命令")
public class GenerateCommand implements Runnable {

<#list modelConfig.models as modelInfo>
    <#if modelInfo.description??>
    /**
    * ${modelInfo.description}
    */
    </#if>
    @CommandLine.Option(
    names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}" ,</#if><#if modelInfo.fieldName??>"--${modelInfo.fieldName}"</#if>},
    arity = "0..1",
    description = "${modelInfo.description}",
    echo = true,
    interactive = true)
    private ${modelInfo.type} ${modelInfo.fieldName}<#if modelInfo.defaultValue??>= ${modelInfo.defaultValue?c}</#if>;;
</#list>

    @SneakyThrows
    @Override
    public void run() {
        ReflexUtil.setFieldsWithInteractiveAnnotation(this, this.getClass());

        DateModel config = new DateModel();
        BeanUtil.copyProperties(this, config);
        MainGenerator.doGenerate(config);
    }
}
