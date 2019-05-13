package com.huangtl.designMode.factory.abstractFactory;

import com.huangtl.designMode.factory.abstractFactory.entity.Button;
import com.huangtl.designMode.factory.abstractFactory.entity.Text;
import com.huangtl.designMode.factory.abstractFactory.factory.BlueSkinFactory;
import com.huangtl.designMode.factory.abstractFactory.factory.SkinFactory;

public class Test {

    public static void main1(String[] args) {
        //SkinFactory skinFactory = new GreenSkinFactory();
        SkinFactory skinFactory = new BlueSkinFactory(); //切换成蓝色皮肤只需要更改工厂引用即可
        Button button = skinFactory.getButton();
        Text text = skinFactory.getText();
        button.display();
        text.display();
    }
}
