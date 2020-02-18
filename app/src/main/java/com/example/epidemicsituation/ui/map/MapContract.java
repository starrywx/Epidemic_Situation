package com.example.epidemicsituation.ui.map;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.epidemicsituation.entity.PoisArea;

import java.util.List;

import io.reactivex.Observable;

public interface MapContract {

    interface MapView {

        void showHeatMap(List<WeightedLatLng> latlngs);

        void drawTrajectory(List<LatLng> latLngs);

        void clearMap();

        void drawPoint(LatLng latLng, String place);

        void drawPolygon(List<LatLng> latLngs, String place);
    }

    interface MapPresent {

        void bindView(MapView mapView);

        void unBind();

        void pollingHeatMap();

        void showPersonalTrajectory();

        void stopPollHeatMap();

        void clearPersonalTrajectory();

        void showPoisArea();

        void hidePoisArea();

    }

    interface MapModel {

        Observable<List<WeightedLatLng>> getHeatPoint();

        Observable<List<LatLng>> getPersonalTrajectory();

        Observable<List<PoisArea>> getPoisArea();
    }
}
