package com.lfyuoi.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

public class MetaManager {

  //双检锁单例模式
  private static volatile Meta meta;

  public static Meta getMetaObject() {
    if (meta == null) {
        synchronized (MetaManager.class) {
        if (meta == null) {
          meta = initMeta();
        }
      }
    }
    return meta;
  }

  private static Meta initMeta() {
       String metaJson = ResourceUtil.readUtf8Str("meta.json");
//      String metaJson = ResourceUtil.readUtf8Str("springboot-init-meta.json");
    Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
    Meta.FileConfig fileConfig = newMeta.getFileConfig();
    MetaValidator.doValidate(newMeta);
    return newMeta;
  }
}
