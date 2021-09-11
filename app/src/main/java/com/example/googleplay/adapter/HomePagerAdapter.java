package com.example.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.googleplay.R;

import net.tsz.afinal.FinalBitmap;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomePagerAdapter extends BasePagerAdapter {

    public HomePagerAdapter(ArrayList<String> urllist) {
        super(urllist);
    }

    @Override
    public int getCount() {
        return urllist.size()*100000;
    }

    @NonNull
    @Override
    public @NotNull Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        ImageView imageView = new ImageView(container.getContext());
        FinalBitmap finalBitmap = FinalBitmap.create(container.getContext());
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        //显示图片
        finalBitmap.display(imageView, urllist.get(position%urllist.size()));

        container.addView(imageView);
        return imageView;
    }
}
