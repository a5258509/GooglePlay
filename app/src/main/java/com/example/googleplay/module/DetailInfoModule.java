package com.example.googleplay.module;

import android.graphics.Color;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.BounceInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.ViewCompat;

import com.example.googleplay.R;
import com.example.googleplay.bean.Home;
import com.example.googleplay.global.MyApp;

import net.tsz.afinal.FinalBitmap;

import butterknife.BindView;
import butterknife.ButterKnife;

//完成app详情页模块的view加载和数据绑定
public class DetailInfoModule extends BaseModule<Home>{

    @BindView(R.id.app_icon)
    ImageView appIcon;
    @BindView(R.id.app_name)
    TextView appName;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.app_size)
    TextView appSize;
    @BindView(R.id.ll_info)
    LinearLayout llInfo;


    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_info;
    }

    /**
     * 绑定数据
     */
    @Override
    public void bindData(Home appinfo){
        FinalBitmap finalBitmap = FinalBitmap.create(MyApp.context);
        if (appinfo.serverData.imgUrl == null) {
            appIcon.setImageResource(R.mipmap.appinfo_pic_default);
        } else {
            finalBitmap.display(appIcon, appinfo.serverData.imgUrl);
        }
        appName.setText(appinfo.serverData.title);
        appSize.setText("大小: "+appinfo.serverData.size);
        String updatedAt = (appinfo.serverData.updatedAt).substring(0,10);
        time.setText("日期: "+updatedAt);

        //执行掉落动画
        //1.先让llInfo上去
        //添加一个布局渲染完成的监听器,以便在完成后获取llInfo高度
        llInfo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            /**
             * 当执行完布局之后,回调该方法,因此可以在该方法中获取宽高
             */
            @Override
            public void onGlobalLayout() {
                llInfo.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                llInfo.setTranslationY(-llInfo.getHeight());
                //再通过属性动画移动下来
                ViewCompat.animate(llInfo)
                        .translationY(0)
                        .setDuration(900)
                        .setStartDelay(400)
                        .setInterpolator(new BounceInterpolator())
                        .start();
            }
        });

    }
}
