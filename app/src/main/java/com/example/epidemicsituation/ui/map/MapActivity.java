package com.example.epidemicsituation.ui.map;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.MyLocationStyle;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapActivity extends BaseActivity {

    @BindView(R.id.activity_map_mv)
    MapView mapMv;
    @BindView(R.id.iv_heat_map)
    ImageView heatMapIv;
    @BindView(R.id.iv_personal_trajectory)
    ImageView personalTrajectoryIv;


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
}
