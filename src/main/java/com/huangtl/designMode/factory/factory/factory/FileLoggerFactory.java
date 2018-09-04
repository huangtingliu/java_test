package com.huangtl.designMode.factory.factory.factory;

import com.huangtl.designMode.factory.factory.entity.FileLogger;
import com.huangtl.designMode.factory.factory.entity.Logger;

/**
 * 具体子类工厂
 */
public class FileLoggerFactory implements LoggerFactory {
    @Override
    public Logger getLogger() {
        return new FileLogger();
    }
}
