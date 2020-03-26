package com.huangtl.aTestQuestion.冒泡排序;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        int num = 100;
        int[] arr = new int[num];
        //生成无序的数填充数组
        for (int i = 0; i < num; i++) {
            arr[i] = new Double(Math.random()*100).intValue();
        }
        System.out.println("原数组："+Arrays.toString(arr));

        //=======冒泡排序部分
        for (int i = 0; i < arr.length; i++) {
            for (int j = i; j < arr.length; j++) {
                if(arr[j] < arr[i]){
                    int temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
        System.out.println("排序数组："+ Arrays.toString(arr));
    }
}
