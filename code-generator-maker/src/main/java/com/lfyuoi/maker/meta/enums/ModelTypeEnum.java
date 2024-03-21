package com.lfyuoi.maker.meta.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 模型类型枚举
 */
@AllArgsConstructor
@Getter
public enum ModelTypeEnum {
    STRING("字符串", "String"),
    BOOLEAN("布尔", "boolean");
    private final String text;
    private final String value;
}
