package com.example.googleplay.adapter;


import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.BaseAdapter;

import androidx.core.view.ViewCompat;

import java.util.ArrayList;

public  abstract class MyBaseAdapter<T> extends BaseAdapter {
    ArrayList<T> list;

    public MyBaseAdapter(ArrayList<T> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //抽取原则:共同的操作留下,动态变化的可以用方法来获取
        Object holder;
        if (convertView == null) {
            convertView  = View.inflate(parent.getContext(), getItemLayoutId(position), null);
            holder = createViewHolder(convertView,position);//通过方法来获取,子类重写方法实现不一样的结果
            convertView.setTag(holder);
        } else {
            holder = convertView.getTag();
        }

        //绑定数据
        bindViewHolder(holder,position,parent);

        //给convertView添加动画效果
        animateConvertView(convertView);


        return convertView;


    }


    /**
     * 返回adapter布局
     * @param convertView
     * @param position
     * @return
     */
    public abstract Object createViewHolder(View convertView,int position);

    /**
     * 返回adaptor的布局
     * @return
     */
    public abstract int getItemLayoutId(int position);

    /**
     * 子类实现绑定数据
      * @param holder
     * @param position
     */
    protected abstract void bindViewHolder(Object holder, int position,ViewGroup parent);

    /**
     * 给convertView添加动画效果
     * @param convertView
     */
    protected void animateConvertView(View convertView) {
        //1.先让convertView缩小
        convertView.setScaleX(0.5f);
        convertView.setScaleY(0.5f);
        //2.执行放大的动画
//        ObjectAnimator animator = ObjectAnimator.ofFloat(convertView, "scaleX", 1f);
//        animator.setDuration(500).start();
        ViewCompat.animate(convertView)
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new OvershootInterpolator())//会让运动轨迹超过一点再弹回来
                .setDuration(500)
                .start();
    }



}
