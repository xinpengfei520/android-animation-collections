package com.xpf.shakeanimation.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.xpf.shakeanimation.R;
import com.xpf.shakeanimation.adapter.GridViewAdapter;
import com.xpf.shakeanimation.bean.AppBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

// 仿IOS卸载的晃动动画
public class LikeIosActivity extends Activity {

    @BindView(R.id.gridView)
    GridView gridView;
    private GridViewAdapter adapter;
    private List<AppBean> apps;
    private Context mContext;
    float mDensity;
    private String[] appNames = {"微信", "猫眼电影", "闪电资讯", "我的日历",
            "Facebook", "联系人", "电话", "日程表",
            "相机", "北京新闻", "P2P金融", "心动商城"};
    private int[] icons = {R.drawable.weixin, R.drawable.cat_icon, R.drawable.lightning, R.drawable.calender,
            R.drawable.facebook, R.drawable.contact, R.drawable.call, R.drawable.schedule,
            R.drawable.camera, R.drawable.news, R.drawable.jinrong, R.drawable.mall};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_like_ios);
        ButterKnife.bind(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm != null) {
            mDensity = dm.density; // 获取手机屏幕的密度比
            Log.e("TAG", "mDensity=" + mDensity);
        }

        initData();
        Toast.makeText(LikeIosActivity.this, "长按图标开启卸载", Toast.LENGTH_LONG).show();
    }

    private void initData() {
        apps = new ArrayList<>();
        for (int i = 0; i < appNames.length; i++) {
            AppBean appBean = new AppBean(appNames[i], icons[i]);
            apps.add(appBean);
        }
        if (apps != null && apps.size() > 0) {
            adapter = new GridViewAdapter(mContext, apps, mDensity);
            gridView.setAdapter(adapter);
        }
    }
}
