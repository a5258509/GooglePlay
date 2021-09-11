package com.example.googleplay.module;

import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.bean.Home;
import com.example.googleplay.http.download.DownloadManager;

import butterknife.BindView;

public class DetailDesModule extends BaseModule<Home> {

    @BindView(R.id.tv_desc)
    TextView tvDesc;

    @Override
    public int getLayoutId() {
        return R.layout.layout_detail_des;
    }

    @Override
    public void bindData(Home appinfo) {
        tvDesc.setText(appinfo.serverData.desc);
    }

}
