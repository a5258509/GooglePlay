package com.example.googleplay.fragment;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.googleplay.R;
import com.example.googleplay.adapter.HomeAdapter;
import com.example.googleplay.adapter.HomePagerAdapter;
import com.example.googleplay.bean.Home;
import com.example.googleplay.bean.Pictures;
import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.SharedPreferencesTool;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

public class HomeFragmentCopy extends BaseFragment implements OnRefreshListener, OnLoadMoreListener {



    private ArrayList<Home> list = new ArrayList<>();
    private HomeAdapter homeAdapter;
    private SwipeToLoadLayout swipeToLoadLayout;
    private ListView listView;
    private int stopLoadMore;
    private AVQuery<AVObject> query = null;
    private ViewPager viewPager;
    private View view1;

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
    public View getSuccessView() {
        view1 = View.inflate(getContext(), R.layout.refresh, null);
        listView = view1.findViewById(R.id.swipe_target);
        swipeToLoadLayout = view1.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        //添加headerview到listview
        addHeaderView();


        //ListView listView = new ListView(getContext());
        //listView.addHeaderView(refresh);
        //去掉listview自带的divider
        listView.setDividerHeight(0);
        homeAdapter = new HomeAdapter(list);
        listView.setAdapter(homeAdapter);

        //设置ListView滑动监听,用于滑动到底部时,松开自动加载更多
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                //ViewCompat.canScrollVertically检查此视图是否可以沿某个方向垂直滚动
                if (scrollState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (!ViewCompat.canScrollVertically(view, 1)) {
                        swipeToLoadLayout.setLoadingMore(true);
                    }
                }

            }


            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (listView.getLastVisiblePosition() == listView.getAdapter().getCount() - 1 && stopLoadMore == 0) {
                    //swipeToLoadLayout.setLoadingMore(true);
                    //将getData中的query初始值设为null,然后判断,只有不为null,才查询服务器,查询完再设为null,防止在此处频繁调用查询服务器的方法
                    initData();
                }

            }

        });
        return view1;
    }

    private void addHeaderView() {
        View headerView = View.inflate(getContext(), R.layout.layout_home_header, null);
        viewPager = headerView.findViewById(R.id.viewPager1);

        listView.addHeaderView(headerView);

    }

    @Override
    public void initData() {
        if (query != null) {
            return;
        }
        query = new AVQuery<>("Appinfo");
        query.limit(10);
        query.skip(list.size());
        query.selectKeys(Arrays.asList("title", "imgUrl", "size", "index", "downUrl", "needVip"));
        //按 updatedAt 降序排列
        query.orderByDescending("updatedAt");
        //list.size()集合的长度刚好与需要分页展示的页数一致,即skip(num)中的参数num一致,所以直接用其替代num,
        AVQueryHelper.create().get(query, list.size(), new AVQueryHelper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                //显示成功界面
                stateLayout.showSuccessView();
                List<Home> home = (List<Home>) GsonUtil.parseJsonToList(result, new TypeToken<List<Home>>() {}.getType());
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
                    Pictures pictures = GsonUtil.parseJsonToBean(home_picture, Pictures.class);
                    ArrayList<String> picture=pictures.serverData.picture_list;
                    if(picture!=null&&picture.size()>0){
                        viewPager.setAdapter(new HomePagerAdapter(picture));
                    }


                    list.addAll(homes);
                    //获取数据后,通知homeAdapter更新listview界面
                    homeAdapter.notifyDataSetChanged();
                    swipeToLoadLayout.setLoadingMore(false);
                    stopLoadMore = 0;
                    query = null;
                }
            }


            @Override
            public void onFail(Throwable e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                swipeToLoadLayout.setLoadingMore(false);
                stopLoadMore = 0;
                query = null;
            }
        });

    }


    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();
                initData();
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 1500);

    }

    /**
     * 加载更多
     */
    @Override
    public void onLoadMore() {
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
                swipeToLoadLayout.setLoadingMore(false);

            }
        }, 1500);
    }


}
