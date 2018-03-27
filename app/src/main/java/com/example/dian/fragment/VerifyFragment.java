package com.example.dian.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.dian.R;
import com.example.dian.map.AMapActivity;
import com.example.dian.qrcode.QrcodeActivity;
import com.example.dian.verifyview.VerifyActivity;

import static android.app.Activity.RESULT_OK;

public class VerifyFragment extends Fragment implements View.OnClickListener {

    public static final String TAG = "VerifyFragment";
    private Button verifyBtn;
    public static final int REQUESTCODE_VERIFY = 1;
    public static final int REQUESTCODE_QRCODE = 2;
    public static final int REQUESTCODE_MAP = 3;
    private AlertDialog mDialog;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttch()");
    }

    public static VerifyFragment newInstance(String text) {
        VerifyFragment mSignFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("text", text);
        mSignFragment.setArguments(bundle);
        return mSignFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated()");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView()");
        View view = inflater.inflate(R.layout.fragment_verify, container, false);
        verifyBtn = view.findViewById(R.id.verify_btn_fragment);
        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
        return view;
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog, null);
        LinearLayout verifyLayout = view.findViewById(R.id.verify_layout_dialog);
        LinearLayout qrcodeLayout = view.findViewById(R.id.qrcoe_layout_dialog);
        LinearLayout mapLayout = view.findViewById(R.id.map_layout_dialog);
        verifyLayout.setOnClickListener(this);
        qrcodeLayout.setOnClickListener(this);
        mapLayout.setOnClickListener(this);
        builder.setView(view);

        mDialog = builder.show();
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        mDialog.getWindow().setLayout((int) (dm.widthPixels * 0.6), ViewGroup.LayoutParams.WRAP_CONTENT);
        Window window = mDialog.getWindow();
        WindowManager.LayoutParams windowParams = window.getAttributes();
        windowParams.dimAmount = 0.3f;
        window.setAttributes(windowParams);
        window.setBackgroundDrawableResource(R.drawable.backgroud_dialog);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach()");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult()");
        switch (requestCode) {
            case REQUESTCODE_VERIFY:
                if (resultCode == RESULT_OK) {
                    successToast();
                }
                break;
            case REQUESTCODE_QRCODE:
                if (resultCode == RESULT_OK) {
                    successToast();
                }
                break;
            case REQUESTCODE_MAP:
                if (resultCode == RESULT_OK) {
                    successToast();
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_layout_dialog:
                Intent verifyIntent = new Intent(getActivity(), VerifyActivity.class);
                startActivityForResult(verifyIntent, REQUESTCODE_VERIFY);
                mDialog.dismiss();
                break;
            case R.id.qrcoe_layout_dialog:
                Intent qrcodeIntent = new Intent(getActivity(), QrcodeActivity.class);
                startActivityForResult(qrcodeIntent, REQUESTCODE_QRCODE);
                mDialog.dismiss();
                break;

            case R.id.map_layout_dialog:
                Intent mapIntent = new Intent(getActivity(), AMapActivity.class);
                startActivityForResult(mapIntent, REQUESTCODE_MAP);
                mDialog.dismiss();
                break;
        }
    }

    private void successToast() {
        Toast.makeText(getActivity(), "签到成功", Toast.LENGTH_SHORT).show();
    }
}
