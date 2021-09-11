package com.example.googleplay.adapter;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.bean.Subject;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubjectAdapter extends MyBaseAdapter<Subject> {


    public SubjectAdapter(ArrayList<Subject> list) {
        super(list);
    }

    @Override
    public Object createViewHolder(View convertView, int position) {
        return new SubjectHolder(convertView);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_subject;
    }

    @Override
    protected void bindViewHolder(Object holder, int position, ViewGroup parent) {
        SubjectHolder subjectHolder= (SubjectHolder) holder;
        //绑定数据
        subjectHolder.tvTitle.setText((String) list.get(position).serverData.des);
        String url = (String) list.get(position).serverData.url;
        FinalBitmap finalBitmap = FinalBitmap.create(parent.getContext());
        finalBitmap.display(subjectHolder.ivImage, url);

    }

    static
    class SubjectHolder {
        @BindView(R.id.iv_image)
        ImageView ivImage;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        SubjectHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
