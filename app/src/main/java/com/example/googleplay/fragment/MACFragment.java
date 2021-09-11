package com.example.googleplay.fragment;


import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;



import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;

import com.example.googleplay.activity.DetailActivity;
import com.example.googleplay.adapter.HomeAdapter;
import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.bean.Home;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.ToastUtil;
import com.google.gson.reflect.TypeToken;



import java.util.ArrayList;
import java.util.List;


public class MACFragment extends HomePcMacSubFragment<Home> implements OnRefreshListener, OnLoadMoreListener {

    @Override
    public MyBaseAdapter getAdapter() {
        return new HomeAdapter(list);
    }

    @Override
    public String getUrl() {
        return "Macinfo";
    }

    @Override
    public void parseDataAndUpdata(String result) {
        List<Home> home = (List<Home>) GsonUtil.parseJsonToList(result, new TypeToken<List<Home>>() {
        }.getType());
        ArrayList<Home> homes = (ArrayList<Home>)home;
        if(homes!=null){
            if(homes.size()==0){
                stopLoadMore = 1;
                Toast.makeText(getContext(),"暂无更多资源!",Toast.LENGTH_SHORT).show();
                //取消刷新
                swipeToLoadLayout.setLoadingMore(false);
                query=null;
                return;
            }

            list.addAll(homes);
            //获取数据后,通知homeAdapter更新listview界面
            baseadapter.notifyDataSetChanged();
            swipeToLoadLayout.setLoadingMore(false);
            stopLoadMore=0;
            query=null;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        //打开详情页
        Intent intent = new Intent(getContext(), DetailActivity.class);
        intent.putExtra("classname","Macinfo");
        //因为首页在listview上加了个viewpage,导致listview多了一个条目,所以list数组要减1才能与listview对应上
        intent.putExtra("objectId",list.get(position).serverData.objectId);
        startActivity(intent);
    }

}
