package com.huangtl.designMode.factory.simpleFactory;

import com.huangtl.designMode.factory.simpleFactory.entity.Chart;
import com.huangtl.designMode.factory.simpleFactory.factory.ChartFactory;

public class Test {

    /**
     * 简单工厂测试
     * @param args
     */
    public static void main1(String[] args) {
        Chart chart = ChartFactory.getChart("line");
        chart.display();
    }
}
