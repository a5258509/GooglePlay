package com.example.googleplay.utils;

public class StringToInt {

    public static int strToint(String s){
        int length = s.length();
        int num = 0;
        int number = 0;
        for(int i = 0; i < length; i++) {
            char ch = s.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        return Math.abs(number);
    }


}
