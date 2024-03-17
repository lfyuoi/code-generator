package com.lfyuoi.maker.cli.command;

import cn.hutool.core.util.ReflectUtil;
import com.lfyuoi.maker.model.DataModel;
import java.lang.reflect.Field;
import picocli.CommandLine;

@CommandLine.Command(name = "config", description = "查看参数信息", mixinStandardHelpOptions = true)
public class ConfigCommand implements  Runnable{

  @Override
  public void run() {
    Field[] fields = ReflectUtil.getFields(DataModel.class);
    for(Field field : fields){
      System.out.println("字段类型" + field.getType());
      System.out.println(field.getName());
    }
  }
}
