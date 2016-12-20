package com.xpf.shakeanimation.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.xpf.shakeanimation.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by xpf on 2016/12/18 :)
 * GitHub:xinpengfei520
 * Function:Text Tags Data Adapter
 */

public class MyChannelAdapter extends BaseAdapter {

    private boolean mNeedShake = false; // 是否需要晃动
    private boolean isShaking = false;  // 是否开始晃动动画(默认不晃动)

    public void setShaking(boolean shaking) {
        isShaking = shaking;
    }

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
    private List<String> list1;
    private List<String> list2 = new ArrayList<>(); // 全部频道

    public List<String> getList1() {
        if (list1 != null && list1.size() > 0) {
            return list1;
        }
        return null;
    }

    // 获取精选频道的集合数据
    public List<String> getList2() {
        if (list2 != null && list2.size() > 0) {
            return list2;
        }
        return null;
    }

    public MyChannelAdapter(Context mContext, List<String> list1, float mDensity) {
        this.mContext = mContext;
        this.list1 = list1;
        this.mDensity = mDensity;
    }

    @Override
    public int getCount() {
        return list1.size();
    }

    @Override
    public Object getItem(int position) {
        return list1.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_tag, null);
            ButterKnife.bind(convertView);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        String tag = list1.get(position);
        holder.tvTag.setText(tag);
        if (isShaking) { // 当为true时显示删除按钮并开启动画
            holder.ivDelete.setVisibility(View.VISIBLE);
            mNeedShake = true;
            shakeAnimation(convertView);
        } else {
            holder.ivDelete.setVisibility(View.GONE);
            mNeedShake = false;
        }

        // 设置删除按钮的点击事件
        holder.ivDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将删除的tag添加到精选频道中,此处必须要先添加再移除,不然会数据下标越界
                if (list1.size() > 1) { // 保证至少留一个频道信息
                    list2.add(list1.get(position));
                    list1.remove(position);
                    if (list2 != null && list2.size() > 0) {
                        if (onListSizeChangeListener != null) {
                            // 调用接口中的onSizeChange方法
                            onListSizeChangeListener.onSizeChange(true);
                        }
                    }
                    notifyDataSetChanged(); // 刷新适配器
                }
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.tv_tag)
        TextView tvTag;
        @BindView(R.id.iv_delete)
        ImageView ivDelete;
        @BindView(R.id.rl_tag)
        RelativeLayout rlTag;

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

    private OnListSizeChangeListener onListSizeChangeListener;

    public void setOnListSizeChangeListener(OnListSizeChangeListener onListSizeChangeListener) {
        this.onListSizeChangeListener = onListSizeChangeListener;
    }

    /**
     * 集合数据变化的监听器
     */
    public interface OnListSizeChangeListener {
        // 当集合数据变化的时候回调
        void onSizeChange(boolean state);
    }
}
