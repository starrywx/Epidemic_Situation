package com.example.epidemicsituation.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.Gradient;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.TileOverlayOptions;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.ui.dialog.RequestPermissionsDialog;
import com.example.epidemicsituation.ui.dialog.TimerPickDialog;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener {

    @BindView(R.id.activity_map_mv)
    MapView mapMv;
    @BindView(R.id.iv_heat_map)
    ImageView heatMapIv;
    @BindView(R.id.iv_personal_trajectory)
    ImageView personalTrajectoryIv;

    RxPermissions rxPermissions;
    @BindView(R.id.civ_back_to_history)
    CircleImageView civBackToHistory;


    private AMap mAmap;
    private UiSettings mUiSettings;
    private boolean isHeatMapOpen;
    private boolean isPersonalTrajectory;
    private MyLocationStyle myLocationStyle;
    //设置渐变颜色
    private int[] gradientColor = new int[]{Color.rgb(0, 225, 0),
            Color.rgb(255, 0, 0)};
    private float[] gradientNum = new float[]{0.0f, 1.0f};
    private Gradient heatMapGradient = new Gradient(gradientColor, gradientNum);

    private static final String TAG = "MapActivity";

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
        mAmap.clear();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rxPermissions = new RxPermissions(this);
        //首次登录时，弹出权限申请对话框，向用户说明情况
        checkLocatePermission();
    }

    //缩放等级3~19
    private void zoomTo(int zoom) {
        CameraUpdate mCameraUpdate = CameraUpdateFactory.zoomTo(zoom);
        mAmap.moveCamera(mCameraUpdate);
    }

    /**
     * 打开定位
     */
    private void openLocation() {
        mAmap.setOnMyLocationChangeListener(this);
        myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(2000);
        //隐藏精确圈
        myLocationStyle.strokeWidth(0);
        myLocationStyle.radiusFillColor(android.R.color.transparent);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);
        mAmap.setMyLocationStyle(myLocationStyle);
        mAmap.setMyLocationEnabled(true);
    }

    /**
     * 初始化地图
     */
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

    @OnClick({R.id.iv_heat_map, R.id.iv_personal_trajectory, R.id.civ_back_to_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_heat_map:
                if (isHeatMapOpen) {
                    closeHeatMap();
                } else {
                    openHeatMap();
                }
                break;
            case R.id.iv_personal_trajectory:
                if (isPersonalTrajectory) {
                    closePersonalTrajectory();
                } else {
                    openPersonalTrajectory();
                }
                break;
            case R.id.civ_back_to_history:
                //返回历史界面

                break;
            default:
                break;
        }
    }

    /**
     * 关闭个人轨迹
     */
    private void closePersonalTrajectory() {
        isPersonalTrajectory = false;
    }

    /**
     * 打开个人轨迹
     */
    private void openPersonalTrajectory() {
        TimerPickDialog timerPickDialog = new TimerPickDialog(this);
        timerPickDialog.show();
    }

    /**
     * 打开热力图
     */
    private void openHeatMap() {
        isHeatMapOpen = true;
        heatMapIv.setImageResource(R.mipmap.ic_heat_map_open);
        //showHeatMap(latlngs);
    }

    /**
     * 获取经纬度信息
     *
     * @param location 精度信息
     */
    @Override
    public void onMyLocationChange(Location location) {

    }

    /**
     * 展示热力图,(已测试)
     *
     * @param latlngs 经纬度数组
     */
    private void showHeatMap(LatLng[] latlngs) {
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.data(Arrays.asList(latlngs))// 设置热力图绘制的数据
                .gradient(heatMapGradient); // 设置热力图渐变，有默认值 DEFAULT_GRADIENT，可不设置该接口
        HeatmapTileProvider heatmapTileProvider = builder.build();
        // 初始化 TileOverlayOptions
        TileOverlayOptions tileOverlayOptions = new TileOverlayOptions();
        tileOverlayOptions.tileProvider(heatmapTileProvider); // 设置瓦片图层的提供者
        // 向地图上添加 TileOverlayOptions 类对象
        mAmap.addTileOverlay(tileOverlayOptions);
    }

    /**
     * 关闭热力图
     */
    private void closeHeatMap() {
        isHeatMapOpen = false;
        heatMapIv.setImageResource(R.mipmap.ic_heat_map_button);
        mAmap.clear();
    }

    private void checkLocatePermission() {
        if (!rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            popUpRequestPermissionsDialog();
        }
    }


    private void popUpRequestPermissionsDialog() {
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
