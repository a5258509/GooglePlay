package com.example.googleplay.fragment;


import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;
import com.example.googleplay.R;
import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.utils.ToastUtil;

import java.util.ArrayList;
import java.util.Arrays;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

/**
 * Home,PC,Mac,Sub四个fragment的父类
 */
public abstract class HomePcMacSubFragment<T> extends BaseFragment implements OnRefreshListener, OnLoadMoreListener, AdapterView.OnItemClickListener {
     View view1;
     SwipeToLoadLayout swipeToLoadLayout;
     ListView listView;
     ArrayList<T> list = new ArrayList<>();
     MyBaseAdapter<T>  baseadapter;
     int stopLoadMore;
     AVQuery<AVObject> query = null;


    @Override
    public View getSuccessView() {
        view1 = View.inflate(getContext(), R.layout.refresh, null);
        listView = view1.findViewById(R.id.swipe_target);
        swipeToLoadLayout = view1.findViewById(R.id.swipeToLoadLayout);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);

        //添加headerview到listview
        addHeaderView();
        
        //去掉listview自带的divider
        listView.setDividerHeight(0);
        baseadapter = getAdapter();

        //设置listview的数据适配器
        listView.setAdapter(baseadapter);

        //设置listview的item条目点击事件
        listView.setOnItemClickListener(this);

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




    /**
     * 让子类进行扩展,如果子类有添加HeaderView的需要,那就让子类实现
     */
    protected void addHeaderView() { }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    /**
     * 获取一个adapter对象,由每个子类实现
     * @return
     */
    public abstract MyBaseAdapter<T> getAdapter();


    @Override
    public void initData() {

        if (query != null) {
            return;
        }
        query = new AVQuery<>(getUrl());
        query.limit(10);
        query.skip(list.size());
        query.selectKeys(Arrays.asList("title", "imgUrl", "size", "index", "downUrl", "needVip","des","url"));
        //按 updatedAt 降序排列
        query.orderByDescending("updatedAt");
        //list.size()集合的长度刚好与需要分页展示的页数一致,即skip(num)中的参数num一致,所以直接用其替代num,
        AVQueryHelper.create().get(query, list.size(), new AVQueryHelper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                //显示成功界面
                stateLayout.showSuccessView();

                //解析数据,更新UI
                parseDataAndUpdata(result);

            }


            @Override
            public void onFail(Throwable e) {
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                swipeToLoadLayout.setLoadingMore(false);
                stopLoadMore = 0;
                query = null;
                stateLayout.showErrorView();
            }
        });

    }



    /**
     * 获取请求的数据库表名
     * @return
     */
    public abstract String getUrl();

    /**
     * 让子类实现解析json数据和更新ui,每个子类的实现是不一样的
     * @param result
     */
    public abstract void parseDataAndUpdata(String result);

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
                ToastUtil.showToast("已是最新数据");
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
