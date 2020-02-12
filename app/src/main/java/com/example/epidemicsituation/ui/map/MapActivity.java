package com.example.epidemicsituation.ui.map;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.epidemicsituation.Base.BaseActivity;
import com.example.epidemicsituation.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends BaseActivity {

    @BindView(R.id.activity_map_mv)
    MapView mapMv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ButterKnife.bind(this);
        mapMv.onCreate(savedInstanceState);
        AMap aMap;
            aMap = mapMv.getMap();
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
}
