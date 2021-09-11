package com.example.googleplay.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;

import com.example.googleplay.R;
import com.example.googleplay.adapter.MainAdapter;
import com.example.googleplay.fragment.CategoryFragment;
import com.example.googleplay.fragment.HomeFragment;
import com.example.googleplay.fragment.HotFragment;
import com.example.googleplay.fragment.MACFragment;
import com.example.googleplay.fragment.PCFragment;
import com.example.googleplay.fragment.RecommendFragment;
import com.example.googleplay.fragment.SubjectFragment;
import com.example.googleplay.global.MyApp;
import com.example.googleplay.utils.ScreenUtils;
import com.example.googleplay.utils.SharedPreferencesTool;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements MaterialSearchBar.OnSearchActionListener{

    private Toolbar toolbar;
    private MaterialSearchBar mSearch;
    private DrawerLayout drawer;
    private TabPageIndicator mIndicator;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerLayout);
        toolbar.setLogo(getResources().getDrawable(R.drawable.ic_launcher_small, null));
        toolbar.inflateMenu(R.menu.toolbar_case_menu);


        //DrawerLeftEdgeSize.setDrawerLeftEdgeSize(this,drawer,0.2f);
        //设置toolbar点击事件
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.d("main1","123");
                if(drawer.isDrawerOpen(Gravity.LEFT)){
                    drawer.closeDrawer(Gravity.LEFT);
                }else {
                    drawer.openDrawer(Gravity.LEFT);
                }
            }
        });

        mSearch = findViewById(R.id.searchBar);
        mSearch.setOnSearchActionListener(this);
        //监听输入中的字符串变化
        mSearch.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // 设置Menu监听
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
               return false;
            }
        });

        //声明权限
        String[] permissionString=new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.INTERNET,
                Manifest.permission.READ_EXTERNAL_STORAGE
        };

        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,permissionString,1);
        }

        initData();



    }

    private void initData() {
        ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new PCFragment());
        fragments.add(new MACFragment());
        fragments.add(new SubjectFragment());
        fragments.add(new RecommendFragment());
        fragments.add(new CategoryFragment());
        fragments.add(new HotFragment());
        ArrayList<String> titles= new ArrayList<>();
        titles.add("首页");
        titles.add("PC");
        titles.add("MAC");
        titles.add("专题");
        titles.add("推荐");
        titles.add("分类");
        titles.add("热门");

        mViewPager=findViewById(R.id.viewPager);
        mIndicator=findViewById(R.id.viewpager_indicator);
        //1.给viewpager填充数据
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), 1, fragments,titles);
        mViewPager.setAdapter(mainAdapter);
        //将viewpager和标签关联起来
        mIndicator.setViewPager(mViewPager);

        //获取首页轮播图图片并缓存起来
        AVQuery<AVObject> query = new AVQuery<>("home_picture");
        System.out.println(1);
        query.getFirstInBackground().subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVObject homepicture) {
                String home_picture=homepicture.toJSONString();
                SharedPreferencesTool.saveString(MyApp.context, "home_picture",home_picture);
//                    Pictures pictures = GsonUtil.parseJsonToBean(home_picture, Pictures.class);
//                    viewPager.setAdapter(new HomePagerAdapter(pictures.serverData.picture_list));
                System.out.println("initData完成");
            }
            public void onError(Throwable throwable) {
            }
            public void onComplete() {}
        });


    }


    //传过来的enable是用来判断搜索框处于启用还是未启用的状态
    @Override
    public void onSearchStateChanged(boolean enabled) {

        System.out.println("点击搜索框时的聚焦与失焦的操作");
        String isenabled = enabled ? "enabled" : "disabled";
        System.out.println("isenabled:"+isenabled);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        mSearch.closeSearch();
        System.out.println("点击搜索的操作");
        String string = text.toString();//获取搜索框输入的文字
        System.out.println(string);

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        System.out.println("左侧图标被点击的操作");
    }



}