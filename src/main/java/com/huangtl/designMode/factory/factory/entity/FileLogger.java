package com.huangtl.designMode.factory.factory.entity;

public class FileLogger implements Logger {

    @Override
    public void writeLog() {
        System.out.println("file logger writeLog...");
    }
}
