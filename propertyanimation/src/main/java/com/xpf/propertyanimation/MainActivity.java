package com.xpf.propertyanimation;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 测试属性动画的基本使用
 */
public class MainActivity extends Activity {

    private ImageView iv_animation;
    private TextView tv_animation_msg;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iv_animation = (ImageView) findViewById(R.id.iv_animation);
        iv_animation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 补间(视图)动画
     *
     * @param v
     */
    public void testTweenAnimation(View v) {
        TranslateAnimation animation = new TranslateAnimation(0, iv_animation.getWidth(), 0, iv_animation.getHeight());
        animation.setDuration(3000);
        animation.setFillAfter(true);
        iv_animation.startAnimation(animation);
    }


    private AnimatorSet animatorSet;

    /**
     * testPropertyAnimation
     *
     * @param v
     */
    public void testPropertyAnimation(View v) {
//        v.setTranslationX(iv_animation.getWidth());
//        v.setTranslationY();

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(iv_animation, "translationX", 0, iv_animation.getWidth());
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(iv_animation, "translationY", 0, iv_animation.getHeight());

        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator3, animator4);

        set.setDuration(2000);
        set.setStartDelay(50);
        set.setInterpolator(new BounceInterpolator());
        set.start();

//        animator3.setDuration(2000);
//        animator3.setStartDelay(50);
//        animator3.start();

//
//        ObjectAnimator animator = ObjectAnimator.ofFloat(iv_animation, "translationX", 0,iv_animation.getWidth());
//        ObjectAnimator animator2 = ObjectAnimator.ofFloat(iv_animation, "translationY", 0,iv_animation.getHeight());
//        AnimatorSet animatorSet = new AnimatorSet();
//        animatorSet.setDuration(2000);
//        animatorSet.setInterpolator(new BounceInterpolator());
//        //两个动画一起播放
//        animatorSet.playTogether(animator, animator2);
//        //开始播放
//        animatorSet.start();

//      //另外一种写法
//        iv_animation.animate()
//                 .translationXBy(iv_animation.getWidth())
//                 .translationYBy(iv_animation.getWidth())
//                 .setDuration(2000)
//                 .setInterpolator(new BounceInterpolator())
//                 .start();
    }

    public void reset(View v) {
        iv_animation.clearAnimation();
    }

}
