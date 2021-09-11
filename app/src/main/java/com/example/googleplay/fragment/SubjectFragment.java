package com.example.googleplay.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googleplay.adapter.MyBaseAdapter;
import com.example.googleplay.adapter.SubjectAdapter;
import com.example.googleplay.bean.Home;
import com.example.googleplay.bean.Subject;
import com.example.googleplay.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class SubjectFragment extends HomePcMacSubFragment<Subject> {


    @Override
    public MyBaseAdapter getAdapter() {
        return new SubjectAdapter(list);
    }

    @Override
    public String getUrl() {
        return "Subject";
    }

    @Override
    public void parseDataAndUpdata(String result) {
        List<Subject> subject = (List<Subject>) GsonUtil.parseJsonToList(result, new TypeToken<List<Subject>>() {
        }.getType());
        ArrayList<Subject> subjects = (ArrayList<Subject>)subject;
        if(subjects!=null){
            if(subjects.size()==0){
                stopLoadMore = 1;
                Toast.makeText(getContext(),"暂无更多资源!",Toast.LENGTH_SHORT).show();
                //取消刷新
                swipeToLoadLayout.setLoadingMore(false);
                query=null;
                return;
            }

            list.addAll(subjects);
            //获取数据后,通知homeAdapter更新listview界面
            baseadapter.notifyDataSetChanged();
            swipeToLoadLayout.setLoadingMore(false);
            stopLoadMore=0;
            query=null;
        }
    }
}
