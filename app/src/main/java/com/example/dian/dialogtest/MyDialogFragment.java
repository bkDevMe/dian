package com.example.dian.dialogtest;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.example.dian.R;
import com.example.dian.map.AMapActivity;
import com.example.dian.qrcode.QrcodeActivity;
import com.example.dian.verifyview.VerifyActivity;

/**
 * Created by dell3020mt-49 on 2018/3/13.
 */

public class MyDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String TAG = "MyDialogFragment";
    public static final int QRCODE_REQUEST = 1;
    public static final int AMAP_REQUEST = 2;
    public static final int VERIFI_REQUEST = 3;
    private LinearLayout verifyLayout;
    private LinearLayout qrcodeLayout;
    private LinearLayout mapLayout;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog()");
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_dialog, null);
        verifyLayout = view.findViewById(R.id.verify_layout_dialog);
        qrcodeLayout = view.findViewById(R.id.qrcoe_layout_dialog);
        mapLayout = view.findViewById(R.id.map_layout_dialog);
        verifyLayout.setOnClickListener(this);
        qrcodeLayout.setOnClickListener(this);
        mapLayout.setOnClickListener(this);

        builder.setView(view);
        AlertDialog dialog = builder.create();
        Log.d(TAG, dialog.toString());
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        Dialog dialog = getDialog();
        if (dialog != null) {
            Log.d(TAG, dialog.toString());
            DisplayMetrics dm = new DisplayMetrics();
            getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
            dialog.getWindow().setLayout((int) (dm.widthPixels * 0.6), ViewGroup.LayoutParams.WRAP_CONTENT);
            Window window = dialog.getWindow();
            WindowManager.LayoutParams windowParams = window.getAttributes();
            windowParams.dimAmount = 0.3f;
            window.setAttributes(windowParams);
            window.setBackgroundDrawableResource(R.drawable.backgroud_dialog);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.verify_layout_dialog:
                Intent intent = new Intent(getActivity(), VerifyActivity.class);
                getActivity().startActivityForResult(intent, VERIFI_REQUEST);
                getDialog().dismiss();
                break;
            case R.id.qrcoe_layout_dialog:
                Intent qrcodeIntent = new Intent(getActivity(), QrcodeActivity.class);
                startActivityForResult(qrcodeIntent, QRCODE_REQUEST);
                getDialog().dismiss();
                break;

            case R.id.map_layout_dialog:
                Intent mapIntent = new Intent(getActivity(), AMapActivity.class);
                startActivityForResult(mapIntent, AMAP_REQUEST);
                getDialog().dismiss();
                break;
        }
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
}
