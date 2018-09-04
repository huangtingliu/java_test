package com.huangtl.designMode.factory.abstractFactory.factory;

import com.huangtl.designMode.factory.abstractFactory.entity.BlueButton;
import com.huangtl.designMode.factory.abstractFactory.entity.BlueText;
import com.huangtl.designMode.factory.abstractFactory.entity.Button;
import com.huangtl.designMode.factory.abstractFactory.entity.Text;

/**
 * 蓝色皮肤工厂类，生产蓝色皮肤的组件
 */
public class BlueSkinFactory implements SkinFactory {
    @Override
    public Button getButton() {
        return new BlueButton();
    }

    @Override
    public Text getText() {
        return new BlueText();
    }
}
