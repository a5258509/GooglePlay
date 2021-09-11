package com.example.googleplay.anim;

//更改一个view的高度的动画

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;

public class HeightAnim {
    //值动画器,专门负责帮我们执行一个动画的过程,我们需要指定一个起始值,但是它本身没有动画效果,只是让这个值进行缓慢的变化
    private ValueAnimator animator;

    public HeightAnim(int startVal, int endVal, View target){
        animator=ValueAnimator.ofInt(startVal,endVal);//ValueAnimator值动画
        //我们需要监听值变化的过程,根据当前的值,来实现自己的动画逻辑
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //1.获取动画的值
                int animatedValue = (int) animation.getAnimatedValue();
                //2.将view的值设置给view的高度
                ViewGroup.LayoutParams params = target.getLayoutParams();
                params.height=animatedValue;
                target.setLayoutParams(params);
            }
        });

    }

    /**
     * 开启高度改变的动画
     * @param duration 持续时间
     */
    public void start(long duration){
        animator.setDuration(duration);
        animator.start();
    }

}
