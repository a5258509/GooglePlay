package com.example.googleplay.module;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;



import com.bumptech.glide.Glide;
import com.example.googleplay.R;
import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.activity.ImageScaleActivity;
import com.example.googleplay.bean.Home;
import com.example.googleplay.global.MyApp;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;

import butterknife.BindView;

public class DetailScreenModule extends BaseModule<Home> {


    @BindView(R.id.ll_image)
    LinearLayout llImage;

    DetailActivity activity;

    public void setActivity(DetailActivity activity){
        this.activity=activity;
    };

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_screen;
    }

    @Override
    public void bindData(Home appinfo) {
        ArrayList<String> descImg = appinfo.serverData.descImg;
        if(descImg!=null){
            if(descImg.size()>0){
                for (int i = 0; i < descImg.size(); i++) {
                    View screenView = View.inflate(MyApp.context, R.layout.screen_image, null);
                    ImageView imageView = (ImageView) screenView.findViewById(R.id.appdetail_screen_img_imageview);
                    //设置图片的放大模式
                    imageView.setScaleType(ImageView.ScaleType.FIT_START);

                    Glide.with(MyApp.context)
                            .load(descImg.get(i))
                            .error(R.mipmap.item01)
                            .into(imageView);
                    llImage.addView(screenView);

                    //图片点击事件
                    int finalI = i;
                    screenView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(activity, ImageScaleActivity.class);
                            intent.putStringArrayListExtra("urlList", (ArrayList<String>) descImg);
                            intent.putExtra("currentItem", finalI);
                            activity.startActivity(intent);
                        }
                    });
                }
            }else {
                loadDefaultImage();
            }
        }else{
            loadDefaultImage();
        }

    }

    /**
     * 没有图片的话,就加载默认图片
     */
    private void loadDefaultImage() {
        View screenView = View.inflate(MyApp.context, R.layout.screen_image, null);
        ImageView screenImageView = screenView.findViewById(R.id.appdetail_screen_img_imageview);
        //设置图片的放大模式
        screenImageView.setScaleType(ImageView.ScaleType.FIT_START);
        screenImageView.setImageResource(R.mipmap.item01);
        llImage.addView(screenView);
    }
}
