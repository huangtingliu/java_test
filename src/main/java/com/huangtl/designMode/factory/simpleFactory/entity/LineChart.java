package com.huangtl.designMode.factory.simpleFactory.entity;

public class LineChart implements Chart {

    @Override
    public void display() {
        System.out.println("line chart display");
    }
}
