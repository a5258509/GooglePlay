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

import com.example.googleplay.R;
import com.example.googleplay.adapter.HomePagerAdapter;
import com.example.googleplay.bean.Home;
import com.example.googleplay.bean.Pictures;
import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.utils.DimenUtil;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.SharedPreferencesTool;
import com.example.googleplay.utils.ToastUtil;
import com.example.googleplay.view.randomlayout.StellarMap;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RecommendFragment extends BaseFragment {


    private StellarMap stellarMap;
    private ArrayList<String> list;

    @Override
    public View getSuccessView() {
        stellarMap = new StellarMap(getContext());
        //1.设置子view距离边框的距离
        int padding= DimenUtil.getDimens(R.dimen.dp15);
        stellarMap.setInnerPadding(padding,padding,padding,padding);
        return stellarMap;
    }

    @Override
    public void initData() {

        AVQuery<AVObject> query = new AVQuery<>("Recommend");
//        query.findInBackground().subscribe(new Observer<List<AVObject>>() {
//            @Override
//            public void onSubscribe(@NotNull Disposable d) {
//
//            }
//
//            @Override
//            public void onNext(@NotNull List<AVObject> avObjects) {
//                System.out.println(avObjects);
//                ArrayList<String> list= (ArrayList<String>) avObjects.get(0).getServerData().get("recommend_list");
//            }
//
//            @Override
//            public void onError(@NotNull Throwable e) {
//                System.out.println(e.getMessage());
//                ToastUtil.showToast("系统忙或网络错误,请稍候再试!");
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });


        AVQueryHelper.create().get(query,1,new AVQueryHelper.HttpCallback(){

            @Override
            public void onSuccess(String result) {
                stateLayout.showSuccessView();
                //将leancloud字符串转为List<AVObject>的list列表对象
                List<AVObject> avObjects = (List<AVObject>) GsonUtil.parseJsonToList(result, new TypeToken<List<AVObject>>() {}.getType());
                //取出Recommend表中的的字段recommend_list值
                list= (ArrayList<String>) avObjects.get(0).getServerData().get("recommend_list");
                stellarMap.setAdapter(new MyAdapter());

            }

            @Override
            public void onFail(Throwable e) {

            }
        });
    }

    class MyAdapter extends StellarMap.Adapter{

        /**
         * 返回有多少组
         * @return
         */
        @Override
        public int getGroupCount(){
            return list.size()/getCount();
        }

        /**
         * 这个组有多少个数据
         * @return
         */
        @Override
        public int getCount() {
            return 7;
        }

        /**
         * 返回每个子view对象
         * @param index
         * @param convertView
         * @return
         */
        @Override
        protected View getView(int index, View convertView) {
            TextView textView=new TextView(getContext());
            textView.setText(list.get(index).replace("\"",""));

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtil.showToast(textView.getText().toString());
                }
            });

            return textView;
        }

        /**
         *
         * @param group
         * @param degree
         * @return
         */
        @Override
        public int getNextGroupOnPan(int group,float degree){
            return 0;
        }

        /**
         * 当缩放动画完毕后下一组加载哪一组数据
         * @param group
         * @param isZoomIn
         * @return
         */
        @Override
        public int getNextGroupOnZoom(int group, boolean isZoomIn){
            return (group+1)%getGroupCount();
        }
    }




}
