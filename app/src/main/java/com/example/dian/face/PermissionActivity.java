package com.example.dian.face;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.dian.MyApplication;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell3020mt-49 on 2018/3/25.
 */

public class PermissionActivity extends AppCompatActivity {
    private static final String TAG = PermissionActivity.class.getSimpleName();
    public static int PERMISSION_REQ = 0x0001;

    private String[] mPermission = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    private List<String> mRequestPermission = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String one : mPermission) {
                if (PackageManager.PERMISSION_GRANTED != this.checkPermission(one, Process.myPid(), Process.myUid())) {
                    mRequestPermission.add(one);
                }
            }

            if (!mRequestPermission.isEmpty()) {
                requestPermissions(mRequestPermission.toArray(new String[mRequestPermission.size()]), PERMISSION_REQ);
                return;
            }
        }

        startActiviy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            return;
        }
        if (requestCode == PERMISSION_REQ) {
            for (int i = 0; i < grantResults.length; i++) {
                for (String one : mPermission) {
                    if (permissions[i].equals(one) && grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        mRequestPermission.remove(one);
                    }
                }
            }
            startActiviy();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult");
        if (requestCode == PERMISSION_REQ) {
            if (resultCode == 0) {
                finish();
            }
        }
    }

    public void startActiviy() {
        Log.d(TAG, "startActivity()");
        if (mRequestPermission.isEmpty()) {
            final ProgressDialog mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setTitle("loading register data...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.show();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    MyApplication app = (MyApplication) PermissionActivity.this.getApplicationContext();
                    app.mFaceDB.loadFaces();
                    PermissionActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.cancel();
                            Intent intent = new Intent(PermissionActivity.this, TestActivity.class);
                            startActivityForResult(intent, PERMISSION_REQ);
                        }
                    });
                }
            }).start();
        } else {
            Toast.makeText(this, "PERMISSION DENIED!", Toast.LENGTH_LONG).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    PermissionActivity.this.finish();
                }
            }, 3000);
        }
    }


}
