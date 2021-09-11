package com.example.googleplay.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class NoScrollViewpager extends ViewPager {
    public NoScrollViewpager(@NonNull Context context) {
        super(context);
    }

    public NoScrollViewpager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //因为viewpager本身是有滑动事件的，所以当有滑动事件到来的时候，如果onInterceptTouchEvent方法返回true,viewpager肯定是会拦截事件，所以返回false,让事件传递给子viewpager
        //但是viewpager中还包含有其他的viewpager，当滑动的时候，当前的viewpager没有滑动，滑动的是其中的子viewpager，所以表示当前的viewpager是没有拦截滑动事件的，而是放开的滑动事件
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        //return super.onTouchEvent(ev);

        //因为系统能过实现viewpager的滑动操作，是因为系统在viewpager的ontouchevnet方法中进行实现，
        //如果想要屏蔽viewpager的滑动操作，只要将系统的触摸事件重写，并且在触摸事件中不进行任何的操作即可
        return true;
    }
}