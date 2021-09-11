package com.example.googleplay.adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class BasePagerAdapter extends PagerAdapter {
    ArrayList<String> urllist;
    Activity activity;

    public BasePagerAdapter(ArrayList<String> urllist) {
        this.urllist = urllist;
    }

    public BasePagerAdapter(ArrayList<String> urllist, Activity activity) {
        this.urllist = urllist;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return urllist.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull @NotNull View view, @NonNull @NotNull Object object) {
        return view==object;
    }



    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object object) {
        //super.destroyItem(container, position, object);
        container.removeView((View) object);
    }
}
