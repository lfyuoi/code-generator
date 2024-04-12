package com.lfyuoi.maker.template.model;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 封装所有和文件相关的配置
 */
@Data
public class TemplateMakerFileConfig {

    private List<FileInfoConfig> files;

    @NoArgsConstructor
    @Data
    public static class FileInfoConfig {

        private String path;

        private List<FileFilterConfig> filterConfigList;
    }
}
