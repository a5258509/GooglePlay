package com.example.googleplay.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.googleplay.R;

public class StateLayout extends FrameLayout {

    private View loadingView;
    private View errorView;
    private View successView;

    public StateLayout(@NonNull Context context) {
        this(context,null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //初始化view
        initView();
    }


    private void initView() {
        loadingView = View.inflate(getContext(), R.layout.page_loading, null);
        addView(loadingView);

        errorView = View.inflate(getContext(), R.layout.page_error, null);
        Button btn_reload = errorView.findViewById(R.id.btn_reload);
        btn_reload.setOnClickListener(v -> {
            showLoadingView();
            if (listener!=null){
                listener.onReload();
            }

        });


        addView(errorView);
        hideAll();

    }

    /**
     * 设置一个成功的view进来
     */
    public void bindSuccessView(View view){
        successView=view;
        if(successView!=null){
            successView.setVisibility(View.INVISIBLE);
            addView(successView);
        }
    }

    public void showSuccessView(){
        hideAll();
        if(successView!=null){
            successView.setVisibility(View.VISIBLE);
        }

    }

    public void showErrorView(){
        hideAll();
        errorView.setVisibility(View.VISIBLE);
    }

    public void showLoadingView(){
        hideAll();
        loadingView.setVisibility(View.VISIBLE);
    }


    /**
     * 隐藏所有的view
     */
    public void hideAll(){
        loadingView.setVisibility(View.INVISIBLE);
        errorView.setVisibility(View.INVISIBLE);
        if(successView!=null) {
            successView.setVisibility(View.INVISIBLE);
        }
    }


    public interface OnReloadListener{

        /**
         * 当重新加载按钮被点击调用
         */
        void onReload();
    }

    private OnReloadListener listener;
    public void setOnReloadListener(OnReloadListener listener){
        this.listener=listener;
    }


}
