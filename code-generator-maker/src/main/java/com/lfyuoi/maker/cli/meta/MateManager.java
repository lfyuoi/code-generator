package com.lfyuoi.maker.cli.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MateManager {

  //双检锁单例模式
  private static volatile Meta meta;

  public static Meta getMetaObject() {
    if (meta == null) {
      synchronized (MateManager.class) {
        if (meta == null) {
          meta = initMeta();
        }
      }
    }
    return meta;
  }

  private static Meta initMeta() {
    String metaJson = ResourceUtil.readUtf8Str("meta.json");
    Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
    Meta.FileConfig fileConfig = newMeta.getFileConfig();
    // todo 校验和处理默认值
    return newMeta;
  }
}
