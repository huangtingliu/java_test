package com.huangtl.designMode.factory.simpleFactory.factory;

import com.huangtl.designMode.factory.simpleFactory.entity.BarChart;
import com.huangtl.designMode.factory.simpleFactory.entity.Chart;
import com.huangtl.designMode.factory.simpleFactory.entity.LineChart;

/**
 * 简单工厂模式工厂类
 */
public class ChartFactory {

    /**
     * 简单工厂统一获取实例静态方法
     * @param chartType
     * @return
     */
    public static Chart getChart(String chartType){
        Chart chart = null;
        if(chartType.equals("line")){
            chart = new LineChart();
            System.out.println("初始化 line chart");
        }else if(chartType.equals("bar")){
            chart = new BarChart();
            System.out.println("初始化 bar chart");
        }

        return chart;
    }
}
