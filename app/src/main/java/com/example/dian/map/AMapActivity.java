package com.example.dian.map;

import android.Manifest;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.example.dian.R;

import java.util.List;

import pub.devrel.easypermissions.EasyPermissions;

import static com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import static com.amap.api.location.AMapLocationClientOption.AMapLocationPurpose;


/**
 * Created by dell3020mt-49 on 2018/3/15.
 */

public class AMapActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, View.OnClickListener, EasyPermissions.PermissionCallbacks {

    private static final String TAG = AMapActivity.class.getSimpleName();
    private static final int REQUESTCODE_AMAP_PERMISSIONS = 1;
    private String[] mPerms = {Manifest.permission.ACCESS_COARSE_LOCATION};


    //    控件
    private MapView mMapView;
    private AMap mAMap;
    private ImageButton locationIbtn;
    private TextView mAddress;
    private Toolbar mToolbarAamp;
    private Button checkBtnMap;

    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mLocationClient;
    private AMapLocationClientOption mLocationClientOption;

    //    标记位
    private boolean isFirstLoc = true;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        setContentView(R.layout.activity_amap);
        mMapView = findViewById(R.id.map);
        mAddress = findViewById(R.id.location_txt);
        checkBtnMap = findViewById(R.id.check_btn_amap);
        locationIbtn = findViewById(R.id.location_Ibtn_amap);
        mToolbarAamp = findViewById(R.id.toolbar_amap);

        setSupportActionBar(mToolbarAamp);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        locationIbtn.setOnClickListener(this);
        checkBtnMap.setOnClickListener(this);

        mMapView.onCreate(savedInstanceState);
        if (hasGPSPermiss()) {
            init();
        } else {
            EasyPermissions.requestPermissions(this, "需要定位权限", REQUESTCODE_AMAP_PERMISSIONS, mPerms);
        }

    }

    /**
     * 初始化AMap对象
     */
    private void init() {
        if (mAMap == null) {
            mAMap = mMapView.getMap();
            setUpMap();
        }
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //        自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.location_marker));
        myLocationStyle.strokeColor(Color.BLACK);
        myLocationStyle.radiusFillColor(Color.argb(50, 0, 0, 0));
        myLocationStyle.strokeWidth(0.5f);
        mAMap.setMyLocationStyle(myLocationStyle);

        mAMap.setLocationSource(this);
        mAMap.getUiSettings().setMyLocationButtonEnabled(false);
        mAMap.setMyLocationEnabled(true);

        mAMap.getUiSettings().setZoomControlsEnabled(false);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
        Log.d(TAG, "onResume() " + hasGPSPermiss());
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
        deactivate();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    /**
     * 定位成功后回调函数
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (mListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                if (isFirstLoc) {
                    mAMap.moveCamera(CameraUpdateFactory.zoomTo(16));
                    mAMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                    isFirstLoc = false;
                    checkBtnMap.setEnabled(true);
                }

                mListener.onLocationChanged(aMapLocation); //显示系统小蓝点
                String address = getAddress(aMapLocation);
                mAddress.setText(address + "附近");
                //                Toast.makeText(this, address, Toast.LENGTH_SHORT).show();
                Log.d("AMapActivity", "onLocationChanged");
            } else {
                String errText = "定位失败，" + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                mAddress.setText("定位失败");
                Log.e("AmapErr", errText);
            }
        }
    }

    /**
     * 激活定位
     *
     * @param listener
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        Log.d(TAG, "activate");
        mListener = listener;
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(this);
            mLocationClientOption = new AMapLocationClientOption();
            //            设置定位监听
            mLocationClient.setLocationListener(this);
            //            设置为高精度定位模式
            mLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
            mLocationClientOption.setLocationPurpose(AMapLocationPurpose.SignIn);
            //            设置定位参数
            mLocationClient.setLocationOption(mLocationClientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();
        }
    }

    @Override
    public void deactivate() {
        Log.d(TAG, "deactivate");
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 获取位置信息
     *
     * @param aMapLocation
     * @return
     */
    private String getAddress(AMapLocation aMapLocation) {
        StringBuffer buffer = new StringBuffer();
        buffer.append(aMapLocation.getProvince() + "" + aMapLocation.getCity() + "" + aMapLocation.getDistrict() + "" + aMapLocation.getStreet() + "" + aMapLocation.getStreetNum());
        Log.d(TAG, buffer.toString());
        return buffer.toString();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.location_Ibtn_amap:
                if (mListener != null && mLocationClient != null) {
                    mLocationClient.startLocation();
                    isFirstLoc = true;
                }
                break;
            case R.id.check_btn_amap:
                setResult(RESULT_OK);
                finish();
                break;
        }
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

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        init();
    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }


    /**
     * 判断是否由此权限
     *
     * @return
     */
    private boolean hasGPSPermiss() {
        return EasyPermissions.hasPermissions(this, mPerms);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

}
