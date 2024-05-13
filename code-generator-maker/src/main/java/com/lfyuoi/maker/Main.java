package com.lfyuoi.maker;

import java.io.IOException;

import com.lfyuoi.maker.generator.main.GenerateTemplate;
import com.lfyuoi.maker.generator.main.MainGenerator;

import com.lfyuoi.maker.generator.main.ZipGenerator;
import freemarker.template.TemplateException;

/**
 * 全局调用入口
 */
public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        GenerateTemplate generateTemplate = new ZipGenerator();
        generateTemplate.doGenerate();
    }
}
