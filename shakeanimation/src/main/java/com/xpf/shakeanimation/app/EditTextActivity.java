package com.xpf.shakeanimation.app;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.xpf.shakeanimation.R;
import com.xpf.shakeanimation.adapter.MyChannelAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

// 文本标签编辑的晃动动画
public class EditTextActivity extends Activity {

    public static final int EDIT = 1; // 编辑状态
    public static final int DONE = 2; // 完成状态
    private int currentState = EDIT;  // 默认为编辑状态

    @BindView(R.id.tv_edit)
    TextView tvEdit;
    @BindView(R.id.gridView1)
    GridView gridView1;
    @BindView(R.id.gridView2)
    GridView gridView2;
    private Context mContext;
    private float mDensity;
    private List<String> list1; // 我的频道
    private List<String> list2 = new ArrayList<>(); // 全部频道
    private MyChannelAdapter adapter;
    private MyChannelAdapter adapter2;
    private boolean isClickable = true;
    private String[] myChannels = {"推荐", "娱乐", "天下", "校园",
            "生活", "视频", "体育", "商业", "八卦", "科技", "军事", "闺蜜", "社交", "段子", "搞笑", "政治"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_edit_text);
        ButterKnife.bind(this);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm != null) {
            mDensity = dm.density; // 获取手机屏幕的密度比
        }
        initData();
    }

    private void initListener() {

        // 设置所有频道Item的点击事件
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(EditTextActivity.this, "position=" + position, Toast.LENGTH_SHORT).show();
                if (isClickable) { // 如果是编辑状态就不让其进行添加删除的操作
                    list2 = adapter.getList2();
                    if (list2 != null && list2.size() > 0) {
                        list1.add(list2.get(position));
                        list2.remove(position);
                        adapter.notifyDataSetChanged();
                        adapter2.notifyDataSetChanged();
                    }
                }
            }
        });

        if (adapter != null) {
            // 设置集合数据变化的监听器
            adapter.setOnListSizeChangeListener(new MyChannelAdapter.OnListSizeChangeListener() {
                @Override
                public void onSizeChange(boolean state) {
                    if (state) {
                        // 获取所有频道的集合数据
                        list2 = adapter.getList2();
                        if (list2 != null && list2.size() > 0) {
                            adapter2 = new MyChannelAdapter(mContext, list2, mDensity);
                            gridView2.setAdapter(adapter2);
                        }
                    }
                }
            });
        }
    }

    private void initData() {
        // 初始化我的频道的集合数据
        list1 = new ArrayList<>();
        for (int i = 0; i < myChannels.length; i++) {
            list1.add(myChannels[i]);
        }
        if (list1 != null && list1.size() > 0) {
            adapter = new MyChannelAdapter(mContext, list1, mDensity);
            // 设置适配器
            gridView1.setAdapter(adapter);
            initListener(); // 设置监听器
        }
    }

    @OnClick(R.id.tv_edit)
    public void onClick() {
        switch (currentState) {
            case EDIT: // 当点击编辑的时候开始摇晃并刷新适配器
                if (adapter != null) {
                    adapter.setShaking(true);
                    adapter.notifyDataSetChanged();
                    tvEdit.setText("完成");
                    isClickable = true;
                    currentState = DONE;
                }
                break;

            case DONE: // 当点击完成的时候不让其摇晃并刷新适配器
                if (adapter != null) {
                    adapter.setShaking(false);
                    adapter.notifyDataSetChanged();
                    tvEdit.setText("编辑");
                    isClickable = false;
                    currentState = EDIT;
                }
                break;
        }
    }
}
