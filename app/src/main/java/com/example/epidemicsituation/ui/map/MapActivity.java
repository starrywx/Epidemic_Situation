package com.example.epidemicsituation.ui.map;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapMv.onCreate(savedInstanceState);
        mAmap = mapMv.getMap();
        mUiSettings = mAmap.getUiSettings();
        initMap();
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
                break;
            case R.id.iv_personal_trajectory:
                break;
        }
    }
}
