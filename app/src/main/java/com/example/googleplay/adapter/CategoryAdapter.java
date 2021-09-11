package com.example.googleplay.adapter;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.googleplay.R;
import com.example.googleplay.bean.Category;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends MyBaseAdapter<Object> {


    public CategoryAdapter(ArrayList<Object> list) {
        super(list);
    }


    //1.定义条目类型的变量
    public final int ITEM_TITLE = 0;
    public final int ITEM_SUB = 1;

    /**
     * 返回条目类型的总数
     *
     */
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    /**
     * 获取指定position的条目是什么类型的
     */
    @Override
    public int getItemViewType(int position) {
        //根据集合中的数据类型来判断
        Object obj = list.get(position);
        if (obj instanceof String) {
            //说明是标题类型
            return ITEM_TITLE;
        } else {
            //说明是子分类类型
            return ITEM_SUB;
        }
        // return super.getItemViewType(position);
    }

    @Override
    public int getItemLayoutId(int position) {
        //根据条目类型返回布局
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //返回标题布局
                return R.layout.adapter_category_title;
            case ITEM_SUB:
                //返回子分类的布局
                return R.layout.adapter_category_sub;
        }

        return 0;
    }

    @Override
    public Object createViewHolder(View convertView, int position) {
        //根据条目类型返回对应的holder对象
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //返回标题holder
                return new TitleHolder(convertView);
            case ITEM_SUB:
                //返回子分类holder
                return new SubHolder(convertView);
        }
        return null;
    }

    @Override
    protected void bindViewHolder(Object holder, int position, ViewGroup parent) {
        //根据条目类型绑定数据
        int itemViewType = getItemViewType(position);
        switch (itemViewType) {
            case ITEM_TITLE:
                //绑定标题数据
                TitleHolder titleHolder = (TitleHolder) holder;
                titleHolder.tvTitle.setText((String) list.get(position));
                break;
            case ITEM_SUB:
                //绑定子分类数据
                SubHolder subHolder = (SubHolder) holder;
                Category.SubCategory subCategory= (Category.SubCategory) list.get(position);
                //显示第一个
                subHolder.tvTitle1.setText(subCategory.name1);
                FinalBitmap.create(parent.getContext()).display(subHolder.ivImage1, subCategory.url1);

                //由于第2和第3个竖向条目可能没有,所以需要判断
                ViewGroup parent2 = (ViewGroup) subHolder.ivImage2.getParent();
                if (! TextUtils.isEmpty(subCategory.name2) && ! TextUtils.isEmpty(subCategory.url2)){
                    //当需要显示的时候,重新设置为可见
                    parent2.setVisibility(View.VISIBLE);

                    subHolder.tvTitle2.setText(subCategory.name2);
                    FinalBitmap.create(parent.getContext()).display(subHolder.ivImage2, subCategory.url2);
                }else {
                    //说明需要隐藏第2个
                    parent2.setVisibility(View.INVISIBLE);
                }
                ViewGroup parent3 = (ViewGroup) subHolder.ivImage3.getParent();
                if (! TextUtils.isEmpty(subCategory.name3) && ! TextUtils.isEmpty(subCategory.url3)){
                    parent3.setVisibility(View.VISIBLE);
                    subHolder.tvTitle3.setText(subCategory.name3);
                    FinalBitmap.create(parent.getContext()).display(subHolder.ivImage3, subCategory.url3);
                }else {
                    //说明需要隐藏第3个
                    parent3.setVisibility(View.INVISIBLE);
                }

                break;
        }
    }

    static
    @SuppressLint("NonConstantResourceId")
    class TitleHolder {
        @BindView(R.id.tv_title)
        TextView tvTitle;

        TitleHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    static
    @SuppressLint("NonConstantResourceId")
    class SubHolder {
        @BindView(R.id.iv_image1)
        ImageView ivImage1;
        @BindView(R.id.tv_title1)
        TextView tvTitle1;
        @BindView(R.id.iv_image2)
        ImageView ivImage2;
        @BindView(R.id.tv_title2)
        TextView tvTitle2;
        @BindView(R.id.iv_image3)
        ImageView ivImage3;
        @BindView(R.id.tv_title3)
        TextView tvTitle3;

        SubHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
