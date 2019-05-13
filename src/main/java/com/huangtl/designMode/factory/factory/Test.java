package com.huangtl.designMode.factory.factory;

import com.huangtl.designMode.factory.factory.entity.Logger;
import com.huangtl.designMode.factory.factory.factory.DBLoggerFactory;
import com.huangtl.designMode.factory.factory.factory.LoggerFactory;

public class Test {

    /**
     * 工厂方法模式
     * @param args
     */
    public static void main1(String[] args) {
        LoggerFactory factory = new DBLoggerFactory();
        Logger logger = factory.getLogger();
        logger.writeLog();
    }

}
