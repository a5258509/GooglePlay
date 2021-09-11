package com.example.googleplay.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.example.googleplay.activity.ImageScaleActivity;
import com.example.googleplay.global.MyApp;
import com.github.chrisbanes.photoview.PhotoView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ImageScaleAdapter extends BasePagerAdapter{

    private List<View> viewList = new ArrayList();

    public ImageScaleAdapter(ArrayList<String> urllist, Activity activity) {
        super(urllist, activity);
    }

    @NonNull
    @NotNull
    @Override
    public Object instantiateItem(@NonNull @NotNull ViewGroup container, int position) {
        PhotoView imageView = new PhotoView(MyApp.context);
        container.addView(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        Glide.with(MyApp.context)
                .load(urllist.get(position))
                .into(imageView);
        return imageView;
    }

    @Override
    public void destroyItem(@NonNull @NotNull ViewGroup container, int position, @NonNull @NotNull Object paramObject) {
        View localView = (View) paramObject;
        container.removeView(localView);
        recycleImage(localView);
        this.viewList.remove(localView);
    }

    private void recycleImage(View view) {
        if (!(view instanceof ImageView)) {
            ImageView imageView = (ImageView) view;
            Drawable drawable = imageView.getDrawable();
            if (drawable == null || !(drawable instanceof BitmapDrawable)) {
                return;
            } else {
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                if (bitmap == null || bitmap.isRecycled()) {
                    return;
                }
                bitmap.recycle();
            }
        }
    }
}
