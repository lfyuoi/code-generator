package com.lfyuoi.model;

import lombok.Data;

/**
 * 动态模板配置
 */
@Data
public class MainTemplateConfig {
    /**
     * 是否生成循环（开关）
     */
    private boolean loop;
    /**
     * 作者注释(字符串，填充值)
     */
    private String author;
    /**
     * 输出信息
     */
    private String outputText;
}
