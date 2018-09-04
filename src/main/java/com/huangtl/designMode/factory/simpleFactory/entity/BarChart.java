package com.huangtl.designMode.factory.simpleFactory.entity;

import com.huangtl.designMode.factory.simpleFactory.entity.Chart;

public class BarChart implements Chart {

    @Override
    public void display() {
        System.out.println("bar chart display");
    }
}
