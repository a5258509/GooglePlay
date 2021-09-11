package com.example.googleplay.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.googleplay.R;
import com.example.googleplay.bean.Home;

import net.tsz.afinal.FinalBitmap;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeAdapter extends MyBaseAdapter<Home> {


    public HomeAdapter(ArrayList<Home> list) {
        super(list);
    }

    @Override
    public Object createViewHolder(View convertView, int position) {
        return new HomeHolder(convertView);
    }

    @Override
    public int getItemLayoutId(int position) {
        return R.layout.adapter_home;
    }

    @Override
    protected void bindViewHolder(Object holder, int position,ViewGroup parent) {
        HomeHolder homeHolder= (HomeHolder) holder;
        //绑定数据
        homeHolder.mTitle.setText((String) list.get(position).serverData.title);
        String updatetime = (String) list.get(position).serverData.updatedAt;
        String substring = updatetime.substring(0, 10);
        homeHolder.mTime.setText(substring);
        homeHolder.mSize.setText((String) list.get(position).serverData.size);
        int index = (int) list.get(position).serverData.index;
        String downUrl = (String) list.get(position).serverData.downUrl;
        Boolean needVip = (Boolean) list.get(position).serverData.needVip;

        String listimage = (String) list.get(position).serverData.imgUrl;
        FinalBitmap finalBitmap = FinalBitmap.create(parent.getContext());
        if (listimage == null) {
            homeHolder.mIcon.setImageResource(R.mipmap.appinfo_pic_default);
        } else {
            finalBitmap.display(homeHolder.mIcon, listimage);
        }
        if(needVip){
            homeHolder.mDown.setText("VIP");
        }else{
            homeHolder.mDown.setText("下载");
        }
        //设置下载按钮的点击事件
        homeHolder.mDown.setOnClickListener(v -> {

            if (!needVip) {
                //判断下载链接是否为百度网盘,如果是,复制下载码,弹出复制信息,提取码: 8fae,从码的index加3开始提,
                if (downUrl.contains("码")) {
                    String tiquma = downUrl.substring(downUrl.indexOf("码") + 3);
                    //复制文本到剪切板
                    ClipboardManager clipboard = (ClipboardManager) parent.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("tiquma", tiquma);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(parent.getContext(), "提取码复制成功,打开浏览器粘贴即可!", Toast.LENGTH_LONG).show();
                }
                //打开下载网页
                //2、通过隐式意图 开启 系统浏览器
                Intent intent = new Intent();
                //3、设置开启浏览器动作
                intent.setAction("android.intent.action.VIEW");
                //4、设置要打开的网页
                intent.setData(Uri.parse(downUrl));
                //5、开启页面
                parent.getContext().startActivity(intent);
            } else {
                Toast.makeText(parent.getContext(), "本资源为vip资源,请充值vip后再下载!...", Toast.LENGTH_SHORT).show();
            }

        });

    }

//    @RequiresApi(api = Build.VERSION_CODES.M)
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        HomeHolder holder;
//        if (convertView == null) {
//           convertView  = View.inflate(parent.getContext(), R.layout.adapter_home, null);
//            holder = new HomeHolder(convertView);
//            convertView.setTag(holder);
//        } else {
//            holder = (HomeHolder) convertView.getTag();
//        }
//
//        //绑定数据
//        holder.mTitle.setText((String) list.get(position).serverData.title);
//        String updatetime = (String) list.get(position).serverData.updatedAt;
//        String substring = updatetime.substring(0, 10);
//        holder.mTime.setText(substring);
//        holder.mSize.setText((String) list.get(position).serverData.size);
//        int index = (int) list.get(position).serverData.index;
//        String downUrl = (String) list.get(position).serverData.downUrl;
//        Boolean needVip = (Boolean) list.get(position).serverData.needVip;
//
//        String listimage = (String) list.get(position).serverData.imgUrl;
//        FinalBitmap finalBitmap = FinalBitmap.create(parent.getContext());
//        if (listimage == null) {
//            holder.mIcon.setImageResource(R.mipmap.appinfo_pic_default);
//        } else {
//            finalBitmap.display(holder.mIcon, listimage);
//        }
//        if(needVip){
//            holder.mDown.setText("VIP");
//        }else{
//            holder.mDown.setText("下载");
//        }
//
//        //设置下载按钮的点击事件
//        holder.mDown.setOnClickListener(v -> {
//
//            if (!needVip) {
//                //判断下载链接是否为百度网盘,如果是,复制下载码,弹出复制信息,提取码: 8fae,从码的index加3开始提,
//                if (downUrl.contains("码")) {
//                    String tiquma = downUrl.substring(downUrl.indexOf("码") + 3);
//                    //复制文本到剪切板
//                    ClipboardManager clipboard = (ClipboardManager) parent.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
//                    ClipData clip = ClipData.newPlainText("tiquma", tiquma);
//                    clipboard.setPrimaryClip(clip);
//                    Toast.makeText(parent.getContext(), "提取码复制成功,打开浏览器粘贴即可!", Toast.LENGTH_LONG).show();
//                }
//                //打开下载网页
//                //2、通过隐式意图 开启 系统浏览器
//                Intent intent = new Intent();
//                //3、设置开启浏览器动作
//                intent.setAction("android.intent.action.VIEW");
//                //4、设置要打开的网页
//                intent.setData(Uri.parse(downUrl));
//                //5、开启页面
//                parent.getContext().startActivity(intent);
//            } else {
//                Toast.makeText(parent.getContext(), "本资源为vip资源,请充值vip后再下载!...", Toast.LENGTH_SHORT).show();
//            }
//
//        });
//
//        return convertView;
//    }


    static
    class HomeHolder {
        @BindView(R.id.item_iv_icon)
        ImageView mIcon;
        @BindView(R.id.item_tv_titles)
        TextView mTitle;
        @BindView(R.id.item_tv_size)
        TextView mSize;
        @BindView(R.id.item_tv_time)
        TextView mTime;
        @BindView(R.id.item_btn_download)
        Button mDown;

        HomeHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
