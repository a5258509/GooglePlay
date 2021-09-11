package com.example.googleplay.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.googleplay.R;
import com.example.googleplay.adapter.ImageScaleAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageScaleActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    private ArrayList<String> urlList;
    private int currentItem;
    private int curOffset = -1;
    private ImageView[] choose;
    ViewPager viewPager;
    ViewGroup imgChoose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_scale);

        viewPager = findViewById(R.id.viewPager);
        imgChoose = findViewById(R.id.img_choose);

        urlList = getIntent().getStringArrayListExtra("urlList");
        currentItem = getIntent().getIntExtra("currentItem", 0);
        curOffset = currentItem;

        showView();


    }

    private void showView() {
        choose = new ImageView[urlList.size()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        for (int i = 0; i < urlList.size(); i++) {
            choose[i] = new ImageView(this);
            choose[i].setImageDrawable(getResources().getDrawable(R.drawable.detail_point_normal));
            if (i == curOffset)
                choose[i].setImageDrawable(getResources().getDrawable(R.drawable.detail_point_selected));
            layoutParams.leftMargin = (int) getResources().getDimension(R.dimen.detail_screen_point_margin);
            choose[i].setLayoutParams(layoutParams);

            imgChoose.addView(this.choose[i]);
        }

        initViewPager();

    }

    private void initViewPager() {
        ImageScaleAdapter imageScaleAdapter = new ImageScaleAdapter(urlList,this);
        viewPager.setAdapter(imageScaleAdapter);
        //设置默认显示第几张
        viewPager.setCurrentItem(currentItem);
        viewPager.addOnPageChangeListener(this);

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        choose[position].setImageDrawable(getResources().getDrawable(R.drawable.detail_point_selected));
        choose[curOffset].setImageDrawable(getResources().getDrawable(R.drawable.detail_point_normal));
        curOffset = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}