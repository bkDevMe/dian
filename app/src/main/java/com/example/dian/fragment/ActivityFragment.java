package com.example.dian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dian.bean.ActivityBean;
import com.example.dian.Adapter.ActivityAdapter;
import com.example.dian.R;

import java.util.ArrayList;
import java.util.List;


public class ActivityFragment extends Fragment {

    private List<ActivityBean> activityList = new ArrayList<>();


    public static ActivityFragment newInstance(String text) {
        ActivityFragment mActivityFragment = new ActivityFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        mActivityFragment.setArguments(bundle);
        return mActivityFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        initActivities();
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        ActivityAdapter adapter = new ActivityAdapter(activityList, getActivity());
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void initActivities() {
        for (int i = 0; i < 2; i++) {
            ActivityBean a1 = new ActivityBean(R.drawable.banner2, "学习十九大系列活动",
                    "时间:11/13 06:00~01/11 19:00",
                    "地点:致知楼、慎思楼", R.drawable.jingxingzhong);
            activityList.add(a1);
            ActivityBean a2 = new ActivityBean(R.drawable.banner1, "第六届科普进校园",
                    "时间:11/24 12:00~05/18 20:00",
                    "地点:慎思楼", R.drawable.jingxingzhong);
            activityList.add(a2);
            ActivityBean a3 = new ActivityBean(R.drawable.banner3, "全国管理决策模拟大赛",
                    "时间:12/11 19:00~01/11 19:00",
                    "地点:博学楼营销实验室", R.drawable.jieshu);
            activityList.add(a3);
            ActivityBean a4 = new ActivityBean(R.drawable.banner4, "第二届校园民谣歌曲大赛",
                    "时间:3/11 19:00~04/012 13:00",
                    "地点:艺术楼", R.drawable.jieshu);
            activityList.add(a4);
        }


    }


}
