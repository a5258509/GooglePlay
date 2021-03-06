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
        //??????toolbar????????????
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
        //?????????????????????????????????
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

        // ??????Menu??????
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {

            @Override
            public boolean onMenuItemClick(MenuItem item) {
               return false;
            }
        });

        //????????????
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
        titles.add("??????");
        titles.add("PC");
        titles.add("MAC");
        titles.add("??????");
        titles.add("??????");
        titles.add("??????");
        titles.add("??????");

        mViewPager=findViewById(R.id.viewPager);
        mIndicator=findViewById(R.id.viewpager_indicator);
        //1.???viewpager????????????
        MainAdapter mainAdapter = new MainAdapter(getSupportFragmentManager(), 1, fragments,titles);
        mViewPager.setAdapter(mainAdapter);
        //???viewpager?????????????????????
        mIndicator.setViewPager(mViewPager);

        //??????????????????????????????????????????
        AVQuery<AVObject> query = new AVQuery<>("home_picture");
        System.out.println(1);
        query.getFirstInBackground().subscribe(new Observer<AVObject>() {
            public void onSubscribe(Disposable disposable) {}
            public void onNext(AVObject homepicture) {
                String home_picture=homepicture.toJSONString();
                SharedPreferencesTool.saveString(MyApp.context, "home_picture",home_picture);
//                    Pictures pictures = GsonUtil.parseJsonToBean(home_picture, Pictures.class);
//                    viewPager.setAdapter(new HomePagerAdapter(pictures.serverData.picture_list));
                System.out.println("initData??????");
            }
            public void onError(Throwable throwable) {
            }
            public void onComplete() {}
        });


    }


    //????????????enable????????????????????????????????????????????????????????????
    @Override
    public void onSearchStateChanged(boolean enabled) {

        System.out.println("?????????????????????????????????????????????");
        String isenabled = enabled ? "enabled" : "disabled";
        System.out.println("isenabled:"+isenabled);
    }

    @Override
    public void onSearchConfirmed(CharSequence text) {
        mSearch.closeSearch();
        System.out.println("?????????????????????");
        String string = text.toString();//??????????????????????????????
        System.out.println(string);

    }

    @Override
    public void onButtonClicked(int buttonCode) {
        System.out.println("??????????????????????????????");
    }



}