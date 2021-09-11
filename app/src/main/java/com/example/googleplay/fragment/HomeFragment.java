package com.example.googleplay.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.example.googleplay.R;
import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.adapter.HomeAdapter;
import com.example.googleplay.adapter.HomePagerAdapter;
import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.bean.Home;
import com.example.googleplay.bean.Pictures;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.SharedPreferencesTool;
import com.example.googleplay.utils.ToastUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class HomeFragment extends HomePcMacSubFragment<Home> implements OnRefreshListener, OnLoadMoreListener {



    private ViewPager viewPager;


    @SuppressLint("HandlerLeak")
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            //先让viewpager选中下一页
            viewPager.setCurrentItem(viewPager.getCurrentItem()+1);
            //接着发消息
            handler.sendEmptyMessageDelayed(0,3000);
        }
    };


    @Override
    public void onStart() {
        super.onStart();
        //发送一个延时消息
        handler.sendEmptyMessageDelayed(0,3000);
        //System.out.println("开始发送消息");
    }

    @Override
    public void onStop() {
        super.onStop();
        //停止自动轮播
        handler.removeMessages(0);
        //System.out.println("停止发送消息");
    }


    @Override
    protected void addHeaderView() {
        View headerView = View.inflate(getContext(), R.layout.layout_home_header, null);
        viewPager = headerView.findViewById(R.id.viewPager1);
        listView.addHeaderView(headerView);
    }

    @Override
    public MyBaseAdapter<Home> getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    public String getUrl() {
        return "Appinfo";
    }

    @Override
    public void parseDataAndUpdata(String result) {
        List<Home> home = (List<Home>) GsonUtil.parseJsonToList(result, new TypeToken<List<Home>>() {
        }.getType());
        ArrayList<Home> homes = (ArrayList<Home>) home;
        if (homes != null) {
            if (homes.size() == 0) {
                stopLoadMore = 1;
                Toast.makeText(getContext(), "暂无更多资源!", Toast.LENGTH_SHORT).show();
                //取消刷新
                swipeToLoadLayout.setLoadingMore(false);
                query = null;
                return;
            }

            //给viewpager填充adapter
            String home_picture = SharedPreferencesTool.getString(getContext(), "home_picture", "");
            if(!TextUtils.isEmpty(home_picture)){
                Pictures pictures = GsonUtil.parseJsonToBean(home_picture, Pictures.class);
                ArrayList<String> picture=pictures.serverData.picture_list;
                viewPager.setAdapter(new HomePagerAdapter(picture));
            }else {
                System.out.println("picture_list获取失败");
            }



            list.addAll(homes);
            //获取数据后,通知homeAdapter更新listview界面
            baseadapter.notifyDataSetChanged();
            swipeToLoadLayout.setLoadingMore(false);
            stopLoadMore = 0;
            query = null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开详情页
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("classname","Appinfo");
        //因为首页在listview上加了个viewpage,导致listview多了一个条目,所以list数组要减1才能与listview对应上
        intent.putExtra("objectId",list.get(position-1).serverData.objectId);
        startActivity(intent);
    }
}
