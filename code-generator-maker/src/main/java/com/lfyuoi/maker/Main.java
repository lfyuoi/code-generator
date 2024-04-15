package com.lfyuoi.maker;

import java.io.IOException;

import com.lfyuoi.maker.generator.main.MainGenerator;

import freemarker.template.TemplateException;

/**
 * 全局调用入口
 */
public class Main {

    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {
        MainGenerator mainGenerator = new MainGenerator();
        mainGenerator.doGenerate();
    }
}
