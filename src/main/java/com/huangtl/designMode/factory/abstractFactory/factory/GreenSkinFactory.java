package com.huangtl.designMode.factory.abstractFactory.factory;

import com.huangtl.designMode.factory.abstractFactory.entity.Button;
import com.huangtl.designMode.factory.abstractFactory.entity.GreenButton;
import com.huangtl.designMode.factory.abstractFactory.entity.GreenText;
import com.huangtl.designMode.factory.abstractFactory.entity.Text;

/**
 * 绿色皮肤工厂类，生产绿色所用的组件
 */
public class GreenSkinFactory implements SkinFactory {
    @Override
    public Button getButton() {
        return new GreenButton();
    }

    @Override
    public Text getText() {
        return new GreenText();
    }
}
