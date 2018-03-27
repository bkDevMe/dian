package com.example.dian;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.dian.fragment.ActivityFragment;
import com.example.dian.fragment.MeFragment;
import com.example.dian.fragment.VerifyFragment;
import com.heima.tabview.library.TabView;
import com.heima.tabview.library.TabViewChild;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private TabViewChild mVerifyFragment;
    private TabViewChild mActivityFragment;
    private TabViewChild mMeFragment;

    List<TabViewChild> tabViewChildList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate()");
        //        初始化数据
        init();


    }


    public int dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 初始化数据
     */
    private void init() {
        mVerifyFragment = new TabViewChild(R.drawable.radiobutton_sign_checked, R.drawable
                .radiobutton_sign_unchecked, "签到",
                VerifyFragment.newInstance("签到"));
        mActivityFragment = new TabViewChild(R.drawable.radiobutton_activity_checked, R
                .drawable.radiobutton_activity_unchecked, "活动",
                ActivityFragment.newInstance("活动"));
        mMeFragment = new TabViewChild(R.drawable.radiobutton_me_checked, R.drawable
                .radiobutton_me_unchecked, "我的",
                MeFragment.newInstance("我的"));
        tabViewChildList.add(mVerifyFragment);
        tabViewChildList.add(mActivityFragment);
        tabViewChildList.add(mMeFragment);

        TabView tabView = (TabView) findViewById(R.id.tabView);
        tabView.setTextViewSelectedColor(R.color.textcolor);
        tabView.setTextViewUnSelectedColor(Color.BLACK);
        tabView.setTabViewHeight(dip2px(25));
        tabView.setImageViewTextViewMargin(10);
        tabView.setTextViewSize(10);
        tabView.setImageViewWidth(dip2px(20));
        tabView.setImageViewHeight(dip2px(20));
        tabView.setTabViewGravity(Gravity.TOP);
        tabView.setTabViewDefaultPosition(0);
        tabView.setTabViewChild(tabViewChildList, getSupportFragmentManager());
        tabView.setOnTabChildClickListener(new TabView.OnTabChildClickListener() {
            @Override
            public void onTabChildClick(int position, ImageView currentImageIcon, TextView currentTextView) {
                //                Toast.makeText(getApplicationContext(),"position:"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity", "onActivityResult()");
    }
}
