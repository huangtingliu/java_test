package com.huangtl.designMode.factory.abstractFactory.entity;

public class GreenButton implements Button {
    @Override
    public void display() {
        System.out.println("green button display");
    }
}
