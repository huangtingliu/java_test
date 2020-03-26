package com.huangtl.aTestQuestion.交替打印;

public class TestFor {

    public static void main(String[] args) {
        String[] arr1 = {"a","b","c","d","e"};
        String[] arr2 = {"1","2","3","4","5"};

        for (int i = 0; i < arr1.length; i++) {
            System.out.print(arr1[i]);
            System.out.print(arr2[i]);
        }
    }
}
