package com.example.googleplay.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.googleplay.R;
import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.utils.ColorUtil;
import com.example.googleplay.utils.DimenUtil;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.ToastUtil;
import com.example.googleplay.view.FlowLayout;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

public class HotFragment extends BaseFragment {

    ScrollView scrollView;
    private ArrayList<String> list;
    private FlowLayout flowLayout;

    int dp15,dp6,padding;

    @Override
    public View getSuccessView() {
        scrollView=new ScrollView(getContext());
        flowLayout = new FlowLayout(getContext());
        dp15=DimenUtil.getDimens(R.dimen.dp15);
        dp6=DimenUtil.getDimens(R.dimen.dp6);
        padding=DimenUtil.getDimens(R.dimen.dp23);

        flowLayout.setPadding(padding,padding,padding,padding);
        scrollView.addView(flowLayout);
        return scrollView;
    }

    @Override
    public void initData() {
        AVQuery<AVObject> query = new AVQuery<>("Hot");
        AVQueryHelper.create().get(query,1,new AVQueryHelper.HttpCallback(){

            @Override
            public void onSuccess(String result) {
                stateLayout.showSuccessView();
                //将leancloud字符串转为List<AVObject>的list列表对象
                List<AVObject> avObjects = (List<AVObject>) GsonUtil.parseJsonToList(result, new TypeToken<List<AVObject>>() {}.getType());
                //取出Recommend表中的的字段recommend_list值
                list= (ArrayList<String>) avObjects.get(0).getServerData().get("hot_list");
                //遍历list给flowlayout添加子view
                for (int i = 0; i < list.size(); i++) {
                    TextView textView=new TextView(getContext());
                    textView.setText(list.get(i).replace("\"",""));
                    textView.setTextColor(Color.WHITE);
                    textView.setTextSize(16);
                    textView.setBackgroundColor(ColorUtil.randomColor());
                    textView.setPadding(dp15,dp6,dp15,dp6);
                    int finalI = i;
                    textView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            ToastUtil.showToast(list.get(finalI).replace("\"",""));
                        }
                    });

                    flowLayout.addView(textView);
                }
            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }
}
