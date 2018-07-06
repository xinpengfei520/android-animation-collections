package com.xpf.shakeanimation.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xpf.shakeanimation.R;
import com.xpf.shakeanimation.bean.AppBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/12/18 :)
 * GitHub:xinpengfei520
 * Function:Apps Data Adapter
 */

public class GridViewAdapter extends BaseAdapter {

    private boolean mNeedShake = false; // 是否需要晃动
    private boolean isShaking = false;  // 是否开始晃动动画(默认不晃动)

    private static final int ICON_WIDTH = 80;
    private static final int ICON_HEIGHT = 94;
    private static final float DEGREE_0 = 1.8f;
    private static final float DEGREE_1 = -2.0f;
    private static final float DEGREE_2 = 2.0f;
    private static final float DEGREE_3 = -1.5f;
    private static final float DEGREE_4 = 1.5f;
    private static final int ANIMATION_DURATION = 100;
    private int mCount = 0;
    private float mDensity;

    private Context mContext;
    private List<AppBean> apps;

    public GridViewAdapter(Context mContext, List<AppBean> apps, float mDensity) {
        this.mContext = mContext;
        this.apps = apps;
        this.mDensity = mDensity;
    }

    @Override
    public int getCount() {
        return apps.size();
    }

    @Override
    public Object getItem(int position) {
        return apps.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_app, null);
            ButterKnife.bind(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        AppBean appBean = apps.get(position);
        holder.appName.setText(appBean.getAppName());
        holder.appIcon.setImageResource(appBean.getAppIcon());
        if (isShaking) { // 当为true时显示删除按钮并开启动画
            holder.delete.setVisibility(View.VISIBLE);
            mNeedShake = true;
            shakeAnimation(convertView);
        }

        // 当长按某个Item时,设置isShaking为true(开始晃动)并刷新适配器
        holder.llApp.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isShaking = true;
                notifyDataSetChanged();
//                Toast.makeText(mContext, "我被长按了position=" + position, Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, "我被单击了position=" + position, Toast.LENGTH_SHORT).show();
                removeApp(position);
            }
        });
        return convertView;
    }

    private void removeApp(final int position) {
        new AlertDialog.Builder(mContext)
                .setTitle("确定要删除此应用吗?")
                .setNegativeButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        apps.remove(position);
                        notifyDataSetChanged();
                    }
                })
                .setPositiveButton("取消", null)
                .show();
    }

    static class ViewHolder {
        @BindView(R.id.appIcon)
        ImageView appIcon;
        @BindView(R.id.delete)
        ImageView delete;
        @BindView(R.id.appName)
        TextView appName;
        @BindView(R.id.ll_app)
        LinearLayout llApp;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    // 晃动动画
    private void shakeAnimation(final View v) {
        float rotate = 0;
        int c = mCount++ % 5;
        if (c == 0) {
            rotate = DEGREE_0;
        } else if (c == 1) {
            rotate = DEGREE_1;
        } else if (c == 2) {
            rotate = DEGREE_2;
        } else if (c == 3) {
            rotate = DEGREE_3;
        } else {
            rotate = DEGREE_4;
        }
        final RotateAnimation ra1 = new RotateAnimation(rotate, -rotate,
                ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);
        final RotateAnimation ra2 = new RotateAnimation(-rotate, rotate,
                ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);

        ra1.setDuration(ANIMATION_DURATION);
        ra2.setDuration(ANIMATION_DURATION);

        // 设置旋转动画的监听
        ra1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mNeedShake) {
                    ra1.reset(); // 重置动画
                    v.startAnimation(ra2); // 第一个动画结束开始第二个旋转动画
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });

        ra2.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation animation) {
                if (mNeedShake) {
                    ra2.reset();
                    v.startAnimation(ra1);// 第二个动画结束开始第一个旋转动画
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }
        });
        v.startAnimation(ra1);
    }

}
