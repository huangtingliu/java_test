package com.huangtl.designMode.factory.abstractFactory.entity;

public class GreenText implements Text {
    @Override
    public void display() {
        System.out.println("green text display");
    }
}
