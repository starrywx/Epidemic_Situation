package com.example.epidemicsituation.ui.map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Gradient;
import com.amap.api.maps.model.HeatmapTileProvider;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.model.TileOverlayOptions;
import com.amap.api.maps.model.WeightedLatLng;
import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.App;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.Constants;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.adapter.AdapterItemClick;
import com.example.epidemicsituation.service.LocationService;
import com.example.epidemicsituation.ui.RegisterLogin.RegisterLoginActivity;
import com.example.epidemicsituation.ui.dialog.ClickConfig;
import com.example.epidemicsituation.ui.dialog.LogOutDialog;
import com.example.epidemicsituation.ui.dialog.RequestPermissionsDialog;
import com.example.epidemicsituation.ui.dialog.TimerPickDialog;
import com.example.epidemicsituation.ui.history.HistoryActivity;
import com.github.ybq.android.spinkit.SpinKitView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.text.MessageFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.reactivex.functions.Consumer;

public class MapActivity extends BaseActivity implements AMap.OnMyLocationChangeListener, MapContract.MapView {

    @BindView(R.id.activity_map_mv)
    MapView mapMv;
    @BindView(R.id.iv_heat_map)
    ImageView heatMapIv;
    @BindView(R.id.iv_personal_trajectory)
    ImageView personalTrajectoryIv;

    RxPermissions rxPermissions;
    @BindView(R.id.civ_back_to_history)
    CircleImageView civBackToHistory;
    @BindView(R.id.rl_detail_info_card)
    RelativeLayout rlDetailInfoCard;
    @BindView(R.id.imv_log_out)
    ImageView imvLogOut;
    @BindView(R.id.imv_to_history)
    ImageView imvHistory;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.spin_kit)
    SpinKitView spinKit;

    private MapPresent present;

    private TimerPickDialog timerPickDialog;
    private AMap mAmap;
    private UiSettings mUiSettings;
    private boolean isHeatMapOpen;
    private boolean isPersonalTrajectory;
    private boolean isPoisArea;
    private MyLocationStyle myLocationStyle;
    //设置渐变颜色
    private int[] gradientColor = new int[]{Color.rgb(0, 225, 0),
            Color.rgb(255, 0, 0)};
    private float[] gradientNum = new float[]{0.0f, 1.0f};
    private Gradient heatMapGradient = new Gradient(gradientColor, gradientNum);

    private static final String TAG = "MapActivity";

    private boolean isFirst = true;

    private static final String TIME_FORMAT = "{0}-{1}-{2} {3}:00:00";

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
        mAmap.clear();

        present = new MapPresent();
        present.bindView(this);

        //启动前台服务
        if (!App.isIsStartingService()) {
            Intent intent = new Intent(this, LocationService.class);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                startForegroundService(intent);
            }else {
                startService(intent);
            }
            App.setIsStartingService(true);
        }
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        Intent intent = getIntent();
//        String flag = intent.getStringExtra("flag");
//        if (Objects.equals(flag, "detail")) {
//            showDetail();
//        }else{
//            showOrigin();
//        }
//    }
//
//    //展示卡片详情页
//    private void showDetail() {
//        heatMapIv.setVisibility(View.GONE);
//        personalTrajectoryIv.setVisibility(View.GONE);
//        rlTitle.setVisibility(View.GONE);
//        rlDetailInfoCard.setVisibility(View.VISIBLE);
//        civBackToHistory.setVisibility(View.VISIBLE);
//        //发送请求定位到感染地点，补充卡片信息
//    }
//
//    //展示原始地图页
//    private void showOrigin() {
//        heatMapIv.setVisibility(View.VISIBLE);
//        personalTrajectoryIv.setVisibility(View.VISIBLE);
//        rlTitle.setVisibility(View.VISIBLE);
//        rlDetailInfoCard.setVisibility(View.GONE);
//        civBackToHistory.setVisibility(View.GONE);
//    }

    @Override
    protected void onStart() {
        super.onStart();
        rxPermissions = new RxPermissions(this);
        //首次登录时，弹出权限申请对话框，向用户说明情况
        checkLocatePermission();
    }

    //缩放等级3~19
    private void moveAndZoomTo(LatLng latLng, int zoom) {
        CameraUpdate mCameraUpdate = CameraUpdateFactory.newCameraPosition(new CameraPosition(latLng, zoom, 30, 0));
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
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER);
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
        present.unBind();
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

    @OnClick({R.id.iv_heat_map, R.id.iv_personal_trajectory, R.id.civ_back_to_history, R.id.imv_log_out, R.id.imv_to_history})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_heat_map:
                if (isPersonalTrajectory) {
                    ToastUtils.showShort("请先关闭个人轨迹功能");
                    return;
                }
                poisArea();
               /* if (isHeatMapOpen) {
                    closeHeatMap();
                } else {
                    openHeatMap();
                }*/
                break;
            case R.id.iv_personal_trajectory:
                if (isHeatMapOpen) {
                    ToastUtils.showShort("请先关闭热力图功能");
                    return;
                }
                if (isPoisArea) {
                    ToastUtils.showShort("请先关闭个人轨迹功能");
                    return;
                }
                if (isPersonalTrajectory) {
                    closePersonalTrajectory();
                } else {
                    openPersonalTrajectory();
                }
                break;
//            case R.id.civ_back_to_history:
            case R.id.imv_to_history:
                startActivity(new Intent(MapActivity.this, HistoryActivity.class));
                break;
            case R.id.imv_log_out:
                LogOutDialog logOutDialog = new LogOutDialog(this);
                logOutDialog.seOnItemClickListener(new AdapterItemClick() {
                    @Override
                    public void onClick(int position) {
                        //退出登录
                        if(SPUtils.getInstance().contains(Constants.USER_AUTHORIZATION)) {
                            SPUtils.getInstance().put(Constants.USER_AUTHORIZATION , "");
                        }
                        ActivityUtils.startActivity(RegisterLoginActivity.class);
                        finish();
                    }
                });
                logOutDialog.show();
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
        personalTrajectoryIv.setImageResource(R.mipmap.ic_personal_trajectory);
        clearMap();
    }

    /**
     * 打开个人轨迹
     */
    private void openPersonalTrajectory() {
        timerPickDialog = new TimerPickDialog(this);
        timerPickDialog.show();
        timerPickDialog.setConfigLinten(new ClickConfig() {
            @Override
            public void onClick(Dialog d) {
                isPersonalTrajectory = true;
                int[] start = timerPickDialog.getStart();
                int[] end = timerPickDialog.getEnd();
                String startTime = MessageFormat.format(TIME_FORMAT, start[0], start[1], start[2], start[3]).replace(",","");
                String endTime = MessageFormat.format(TIME_FORMAT, end[0], end[1], end[2], end[3]).replace(",","");
                Log.d(TAG, "starttime:" + startTime);
                Log.d(TAG, "endtime:" + endTime);
                present.showPersonalTrajectory(startTime, endTime);
                personalTrajectoryIv.setImageResource(R.mipmap.ic_personal_trajectory_open);
                d.cancel();
            }
        });
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
        if (isFirst) {
            isFirst = false;
            moveAndZoomTo(new LatLng(location.getLatitude(), location.getLongitude()), 12);
        }
    }

    /**
     * 展示热力图,(已测试)
     *
     * @param latlngs 经纬度数组
     */
    public void showHeatMap(List<WeightedLatLng> latlngs) {
        // 构建热力图 HeatmapTileProvider
        HeatmapTileProvider.Builder builder = new HeatmapTileProvider.Builder();
        builder.weightedData(latlngs)// 设置热力图绘制的数据
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
        clearMap();
    }

    private void checkLocatePermission() {
        if (!rxPermissions.isGranted(Manifest.permission.ACCESS_FINE_LOCATION)) {
            popUpRequestPermissionsDialog();
        }
    }

    /**
     * 弹出权限说明弹窗
     */
    private void popUpRequestPermissionsDialog() {
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
//                                            ToastUtils.showShort("成功获取定位权限");
                                            present.doKeepAliveBackground(); //引导用户设置保活
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

    @Override
    public void drawTrajectory(List<LatLng> latLngs) {
        Polyline polyline = mAmap.addPolyline(new PolylineOptions()
                .addAll(latLngs).width(10).color(Color.parseColor("#F80C0C")));
    }

    @Override
    public void clearMap() {
        if (mAmap != null) {
            mAmap.clear();
        }
    }

    @Override
    public void drawPoint(LatLng latLng, String palce) {
        mAmap.addMarker(new MarkerOptions().position(latLng).title(palce).snippet(palce));
    }

    @Override
    public void drawPolygon(List<LatLng> latLngs, String place) {
        if (latLngs != null) {
            if (latLngs.size() > 0) {
                drawPoint(latLngs.get(0), place);
                PolygonOptions polylineOptions = new PolygonOptions();
                polylineOptions.addAll(latLngs);
                polylineOptions.strokeWidth(3.0f)
                        .fillColor(Color.parseColor("#4169E1"))
                        .strokeColor(Color.parseColor("#000080"));
                mAmap.addPolygon(polylineOptions);
            }
        }
    }

    private void poisArea() {
        if (isPoisArea) {
            isPoisArea = false;
            heatMapIv.setImageResource(R.mipmap.ic_heat_map_button);
            present.hidePoisArea();
        } else {
            //打开疫情区域标注
            isPoisArea = true;
            heatMapIv.setImageResource(R.mipmap.ic_heat_map_open);
            present.showPoisArea();
        }
    }

    @Override
    public void showLoading() {
        spinKit.setVisibility(View.VISIBLE);
    }

    @Override
    public void hidLoading() {
        spinKit.setVisibility(View.GONE);
    }

    @Override
    public void setPerTraFalse() {
        isPersonalTrajectory = false;
        personalTrajectoryIv.setImageResource(R.mipmap.ic_personal_trajectory);
        clearMap();
    }

    @Override
    public void setHeatMapFalse() {
        isHeatMapOpen = false;
        heatMapIv.setImageResource(R.mipmap.ic_heat_map_button);
        clearMap();
    }

    @Override
    public void setPoisArea() {
        isPoisArea = false;

        clearMap();
    }
}
