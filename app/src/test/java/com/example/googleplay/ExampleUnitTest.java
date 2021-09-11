package com.example.googleplay;

import com.example.googleplay.global.MyApp;
import com.example.googleplay.utils.ScreenUtils;

import org.junit.Test;

import static java.lang.Integer.parseInt;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void strToint(){
        String letter="60580464986e9365f49f8df1";
        int length = letter.length();
        int num = 0;
        int number = 0;
        for(int i = 0; i < length; i++) {
            char ch = letter.charAt(length - i - 1);
            num = (int) (ch - 'A' + 1);
            num *= Math.pow(26, i);
            number += num;
        }
        System.out.println(Math.abs(number));
    }

    @Test
    public void getScreen(){
        int screenWidth = ScreenUtils.getScreenWidth(MyApp.context);
        System.out.println(screenWidth);
    }


}