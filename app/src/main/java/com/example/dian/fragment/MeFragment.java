package com.example.dian.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dian.R;


public class MeFragment extends Fragment {

    public static MeFragment newInstance(String text) {
        MeFragment mMeFragment = new MeFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        mMeFragment.setArguments(bundle);
        return mMeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        //        textView= (TextView) view.findViewById(R.id.textView);
        //        textView.setText(getArguments().getString("text"));
        return view;
    }
}
