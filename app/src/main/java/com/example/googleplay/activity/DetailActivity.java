package com.example.googleplay.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.googleplay.R;
import com.example.googleplay.bean.Home;
import com.example.googleplay.http.AVQueryHelper;
import com.example.googleplay.module.DetailDesModule;
import com.example.googleplay.module.DetailInfoModule;
import com.example.googleplay.module.DetailScreenModule;
import com.example.googleplay.utils.GsonUtil;
import com.example.googleplay.utils.StringToInt;
import com.example.googleplay.view.StateLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.leancloud.AVObject;
import cn.leancloud.AVQuery;

public class DetailActivity extends AppCompatActivity implements StateLayout.OnReloadListener {

    @BindView(R.id.ll_container)
    LinearLayout llContainer;

    public StateLayout stateLayout;
    public String classname;
    public String objectId;

    private DetailInfoModule infoModule;
    private DetailScreenModule screenModule;
    private DetailDesModule desModule;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //1.获取intent数据
        classname = getIntent().getStringExtra("classname");
        objectId = getIntent().getStringExtra("objectId");

        //2.创建statelayout
        stateLayout = new StateLayout(this);
        //将stateLayout作为activity的view
        setContentView(stateLayout);
        //设置成功的view
        stateLayout.bindSuccessView(getSuccessView());
        stateLayout.showLoadingView();

        //3.请求数据
        initData();

        //设置重新加载按钮被点击的监听器
        stateLayout.setOnReloadListener(this);
    }

    /**
     * 获取成功的view
     *
     * @return
     */
    private View getSuccessView() {
        View view = View.inflate(this, R.layout.activity_detail, null);
        ButterKnife.bind(this, view);

        //1.加入info模块
        infoModule=new DetailInfoModule();
        llContainer.addView(infoModule.getModuleView());
        //2加入screen模块
        screenModule=new DetailScreenModule();
        screenModule.setActivity(DetailActivity.this);
        llContainer.addView(screenModule.getModuleView());
        //3.加入desc模块
        desModule=new DetailDesModule();
        llContainer.addView(desModule.getModuleView());
        return view;
    }

    /**
     * 加载数据
     */
    private void initData() {

        AVQuery<AVObject> query = new AVQuery<>(classname);
        query.whereEqualTo("objectId", objectId);
        AVQueryHelper.create().get(query, StringToInt.strToint(objectId), new AVQueryHelper.HttpCallback() {
            @Override
            public void onSuccess(String result) {
                //显示成功界面
                stateLayout.showSuccessView();
                //将leancloud字符串转为List<AVObject>的list列表对象
                //List<AVObject> avObjects = (List<AVObject>) GsonUtil.parseJsonToList(result, new TypeToken<List<AVObject>>() {}.getType());
                //因为使用leancloud返回的字符串数据带有[],不能解析为javabean,所以截掉首尾的中括号
                String str1 = result.substring(1, result.length() - 1);
                Home appinfo = GsonUtil.parseJsonToBean(str1, Home.class);
                if (appinfo != null) {
                    //更新UI
                    updataUI(appinfo);
                }

            }

            @Override
            public void onFail(Throwable e) {
                Toast.makeText(DetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                stateLayout.showErrorView();
            }
        });

    }

    private void updataUI(Home appinfo) {
        //绑定info模块数据
        infoModule.bindData(appinfo);
        //绑定screen模块数据
        screenModule.bindData(appinfo);
        //绑定desc模块数据
        desModule.bindData(appinfo);
    }


    @Override
    public void onReload() {
        initData();
    }
}
