package com.example.googleplay.fragment;


import android.view.View;

import android.widget.ListView;

import com.example.googleplay.R;
import com.example.googleplay.adapter.CategoryAdapter;
import com.example.googleplay.bean.Category;

import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.utils.GsonUtil;

import com.google.gson.reflect.TypeToken;


import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

public class CategoryFragment extends BaseFragment {


    private ListView listView;
    //存放title和subcategory的集合
    ArrayList<Object> list=new ArrayList<>();


    @Override
    public View getSuccessView() {
        listView = (ListView) View.inflate(getContext(), R.layout.listview, null);
        return listView;
    }

    @Override
    public void initData() {
        AVQuery<AVObject> query = new AVQuery<>("Cages");
        AVQueryHelper.create().get(query,1,new AVQueryHelper.HttpCallback(){
            @Override
            public void onSuccess(String result) {
                stateLayout.showSuccessView();
                //将leancloud字符串转为List<AVObject>的list列表对象
                List<AVObject> avObjects = (List<AVObject>) GsonUtil.parseJsonToList(result, new TypeToken<List<AVObject>>() {}.getType());
                //取出Recommend表中的的字段recommend_list值
                String data= (String) avObjects.get(0).getServerData().get("data");
                ArrayList<Category> categories= (ArrayList<Category>) GsonUtil.parseJsonToList(data, new TypeToken<List<Category>>() {}.getType());
                //将所有categories的title和subcategory对象放入list中
                for (Category cate : categories){
                    //1.将title放入list中
                    list.add(cate.title);
                    //2.将infos的所有subcategory放入list中
                    list.addAll(cate.infos);
                }

                //设置adapter
                listView.setAdapter(new CategoryAdapter(list));

            }

            @Override
            public void onFail(Throwable e) {

            }
        });

    }
}
