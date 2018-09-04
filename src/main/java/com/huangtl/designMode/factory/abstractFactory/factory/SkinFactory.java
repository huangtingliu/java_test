package com.huangtl.designMode.factory.abstractFactory.factory;

import com.huangtl.designMode.factory.abstractFactory.entity.Button;
import com.huangtl.designMode.factory.abstractFactory.entity.Text;

/**
 * 抽象工厂父接口
 */
public interface SkinFactory {
    public Button getButton();
    public Text getText();
}
