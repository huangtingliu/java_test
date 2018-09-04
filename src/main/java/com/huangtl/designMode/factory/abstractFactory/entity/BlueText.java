package com.huangtl.designMode.factory.abstractFactory.entity;

public class BlueText implements Text {
    @Override
    public void display() {
        System.out.println("blue text display");
    }
}
