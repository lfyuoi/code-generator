package com.lfyuoi.maker.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.lfyuoi.maker.generator.file.FileGenerator;
import com.lfyuoi.maker.model.DataModel;
import java.util.concurrent.Callable;
import lombok.Data;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

  @Option(names = {"-l",
      "--loop"}, arity = "0..1", description = "是否循环", interactive = true, echo = true)
  private boolean loop;

  @Option(names = {"-a",
      "--author"}, arity = "0..1", description = "作者", interactive = true, echo = true)
  private String author = "yupi";

  @Option(names = {"-o",
      "--outputText"}, arity = "0..1", description = "输出文本", interactive = true, echo = true)
  private String outputText = "sum = ";

  public Integer call() throws Exception {
    DataModel dataModel = new DataModel();
    BeanUtil.copyProperties(this, dataModel);
    System.out.println("配置信息：" + dataModel);
    FileGenerator.doGenerate(dataModel);
    return 0;
  }
}
