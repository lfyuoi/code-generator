package ${basePackage}.cli.command;

import cn.hutool.core.util.ReflectUtil;
import ${basePackage}.model.DateModel;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * @author ${author}
 * @date ${.now}
 * @description 配置命令
 */
@CommandLine.Command(name = "config", description = "配置命令",mixinStandardHelpOptions = true)
public class ConfigCommand implements Runnable{

    @Override
    public void run() {
        Field[] fields =
                ReflectUtil.getFields(DateModel.class);
        for (Field field : fields) {
            System.out.println(field.getName() + " : " + field.getType().getName());
        }
    }
}
