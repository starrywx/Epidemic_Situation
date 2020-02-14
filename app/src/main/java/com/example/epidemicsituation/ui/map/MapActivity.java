package com.example.epidemicsituation.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.ui.dialog.RequestPermissionsDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

public class MapActivity extends BaseActivity {

    @BindView(R.id.activity_map_mv)
    MapView mapMv;
    @BindView(R.id.iv_heat_map)
    ImageView heatMapIv;
    @BindView(R.id.iv_personal_trajectory)
    ImageView personalTrajectoryIv;

    RxPermissions rxPermissions;


    private AMap mAmap;
    private UiSettings mUiSettings;
    private boolean isHeatMapOpen;
    private boolean isPersonalTrajectory;
    private MyLocationStyle myLocationStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapMv.onCreate(savedInstanceState);
        mAmap = mapMv.getMap();
        mUiSettings = mAmap.getUiSettings();
        initMap();
        //打开定位
        openLocation();
        //设置缩放
        zoomTo(17);

    }

    @Override
    protected void onStart() {
        super.onStart();
        rxPermissions =  new RxPermissions(this);
        //首次登录时，弹出权限申请对话框，向用户说明情况
        checkLocatePermission();
    }

    //缩放等级3~19
    private void zoomTo(int zoom) {
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(zoom);
        mAmap.moveCamera(mCameraUpdate);
    }

    private void openLocation() {
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        mAmap.setMyLocationStyle(myLocationStyle);
        mAmap.setMyLocationEnabled(true);
    }

    public void initMap() {
        mUiSettings.setZoomControlsEnabled(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapMv.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapMv.onSaveInstanceState(outState);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapMv.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapMv.onResume();
    }

    @OnClick({R.id.iv_heat_map, R.id.iv_personal_trajectory})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_heat_map:
                if (isHeatMapOpen) {
                    closeHeatMap();
                }else {
                    openHeatMap();
                }
                break;
            case R.id.iv_personal_trajectory:
                if (isPersonalTrajectory) {
                    closePersonalTrajectory();
                }else {
                    openPersonalTrajectory();
                }
                break;
            default:
                break;
        }
    }

    private void closePersonalTrajectory() {
        isPersonalTrajectory = false;
    }

    private void openPersonalTrajectory() {

    }

    private void openHeatMap() {
        isHeatMapOpen = true;
        heatMapIv.setImageResource(R.mipmap.ic_heat_map_open);

    }

    private void closeHeatMap() {
        isHeatMapOpen = false;
        heatMapIv.setImageResource(R.mipmap.ic_heat_map_button);

    }

    private void checkLocatePermission(){
        if( ! rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)){
            popUpRequestPermissionsDialog();
        }
    }


    private void popUpRequestPermissionsDialog(){
/*        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder
                .setView(R.layout.dialog_request_permission)
                .create();
        dialog.show();
        Window dialogWindow = dialog.getWindow();
        if(dialogWindow != null) {
            dialogWindow.findViewById(R.id.btn_agree_dialog_request_permission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //同意，则申请权限
                    dialog.dismiss(); //关闭对话框
                    ToastUtils.showShort("同意获取权限");
                }
            });
            dialogWindow.findViewById(R.id.btn_disagree_dialog_request_permission).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //不同意获取权限，退出
                    dialog.dismiss(); //取消对话框
//                    finish();
                }
            });
        }*/
        RequestPermissionsDialog dialog = new RequestPermissionsDialog(this);
        dialog.show(); //在show方法后，才构建视图，否则findViewById 返回 null
        dialog.findViewById(R.id.btn_agree_dialog_request_permission)
                .setOnClickListener(new View.OnClickListener() {
                    @SuppressLint("CheckResult")
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); //关闭对话框
                        //动态获取拍摄,录音权限
                        rxPermissions.request(Manifest.permission.ACCESS_FINE_LOCATION)
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean aBoolean) throws Exception {
                                        //检查权限是否获取，提醒用户
                                        if (aBoolean) {
                                            ToastUtils.showShort("成功获取定位权限");
                                        } else {
                                            ToastUtils.showLong("应用未获取到定位权限，如需正常使用，请前往应用设置中开启");
                                        }
                                    }
                                });
                    }
                });
        dialog.findViewById(R.id.btn_disagree_dialog_request_permission)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss(); //关闭对话框
                        ToastUtils.showLong("应用未获取到定位权限，如需正常使用，请前往应用设置中开启");
                    }
                });

    }
}
