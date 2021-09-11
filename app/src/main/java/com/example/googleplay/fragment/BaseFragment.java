package com.example.googleplay.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googleplay.utils.LogUtil;
import com.example.googleplay.view.StateLayout;

import org.jetbrains.annotations.NotNull;

public abstract class BaseFragment extends Fragment implements StateLayout.OnReloadListener {

    public StateLayout stateLayout;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        LogUtil.e("onCreateView");
        //判断如果statelayout为空,才进行创建
        if(stateLayout==null){
            stateLayout = new StateLayout(getContext());
            //设置成功的view
            stateLayout.bindSuccessView(getSuccessView());
            stateLayout.showLoadingView();
            //设置重新加载按钮被点击的监听器
            stateLayout.setOnReloadListener(this);


            initData();
        }
        return stateLayout;
    }

    public abstract void initData();

    /**
     * 获取成功的SuccessView
     * @return
     */
    public abstract View getSuccessView();


    @Override
    public void onReload() {
        //处理数据的重新加载
        initData();
    }

}



