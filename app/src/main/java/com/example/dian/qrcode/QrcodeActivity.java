package com.example.dian.qrcode;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.dian.R;

import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * Created by dell3020mt-49 on 2018/3/14.
 */

public class QrcodeActivity extends AppCompatActivity implements QRCodeView.Delegate, EasyPermissions.PermissionCallbacks {
    private static final String TAG = QrcodeActivity.class.getSimpleName();
    private static final int REQUEST_CODE_QRCODE_PERMISSIONS = 1;
    private QRCodeView mQRCodeView;
    private Toolbar mToolbarQrcode;
    private String[] mPerms = new String[]{Manifest.permission.CAMERA};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_qrcode);
        mQRCodeView = (ZXingView) findViewById(R.id.zingview);

        mToolbarQrcode = findViewById(R.id.toolbar_qrcode);
        setSupportActionBar(mToolbarQrcode);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mQRCodeView.setDelegate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        if (!hasCameraPermission()) {
            EasyPermissions.requestPermissions(this, "扫描二维码需要打开相机和散光灯的权限",
                    REQUEST_CODE_QRCODE_PERMISSIONS, mPerms);
            Log.d(TAG, "onStart()" + hasCameraPermission() + "");
        } else {
            Log.d(TAG, hasCameraPermission() + " onStart()");
            mQRCodeView.startCamera();
            mQRCodeView.showScanRect();
            mQRCodeView.startSpot();
        }

        //        mQRCodeView.startSpot();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        mQRCodeView.stopCamera();
        super.onStop();

    }


    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        vibrate();
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onScanQRCodeOpenCameraError() {

    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        Log.d(TAG, "onPermissionsGranted");
        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        switch (requestCode) {
            case REQUEST_CODE_QRCODE_PERMISSIONS:
                Log.d(TAG, "onPermissionsEdnied");
                Intent intent = new Intent();
                finish();
                break;
        }
    }

    /**
     * 权限的回调的处理
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    /**
     * 判断是否有此权限
     *
     * @return
     */
    private boolean hasCameraPermission() {
        mPerms = new String[]{Manifest.permission.CAMERA};
        return EasyPermissions.hasPermissions(this, mPerms);
    }

    /**
     * 震动0.2秒
     */
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
