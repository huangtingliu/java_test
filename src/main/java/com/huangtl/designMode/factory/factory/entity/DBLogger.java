package com.huangtl.designMode.factory.factory.entity;

public class DBLogger implements Logger {

    @Override
    public void writeLog() {
        System.out.println("db logger writeLog...");
    }
}
