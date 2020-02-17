package com.example.epidemicsituation.ui.map;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.example.epidemicsituation.Utils.GsonUtils;
import com.example.epidemicsituation.entity.PoisArea;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class MapPresent implements MapContract.MapPresent {

    MapContract.MapView view;
    MapContract.MapModel model;

    private static final String POLYGON = "Polygon";

    private static final String POINT = "Point";

    private static final String TAG = "MapPresent";

    public MapPresent() {
        model = new MapModel();
    }

    @Override
    public void bindView(MapContract.MapView mapView) {
        this.view = mapView;
    }

    @Override
    public void unBind() {
        this.view = null;
    }

    @Override
    public void pollingHeatMap() {

    }

    @Override
    public void showPersonalTrajectory() {

    }

    @Override
    public void stopPollHeatMap() {

    }

    @Override
    public void clearPersonalTrajectory() {

    }

    @Override
    public void showPoisArea() {
        Log.d(TAG, "present开始处理逻辑");
        model.getPoisArea()
                .subscribe(new Observer<List<PoisArea>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<PoisArea> poisAreas) {
                        if (poisAreas == null) {
                            return;
                        }
                        for (PoisArea poisArea : poisAreas) {
                            if (poisArea != null) {
                                Log.d(TAG, "拿到数据");
                                List<PoisArea.PoisBean> poisBeans = poisArea.getPois();
                                if (poisBeans != null && poisBeans.size() > 0) {
                                    for (PoisArea.PoisBean temp : poisBeans) {
                                        PoisArea.PoisBean.GeometryBean geo = temp.getGeometry();
                                        if (geo != null) {
                                            String type = geo.getType();
                                            if (type != null) {
                                                if (type.equals(POINT)) {
                                                    if (geo.getCoordinates() != null) {
                                                        List<Double> coordinates = GsonUtils.GsonToList(geo.getCoordinates().toString(),Double.class);
                                                        LatLng latLng = new LatLng(coordinates.get(1), coordinates.get(0));
                                                        Log.d(TAG, coordinates.get(0)
                                                                + ":" + coordinates.get(1));
                                                        if (view != null) {
                                                            view.drawPoint(latLng, temp.getName());
                                                        }
                                                    }
                                                }else if (type.equals(POLYGON)) {
                                                    Gson gson = new Gson();

                                                    List<List<List<Double>>> lan = gson.fromJson(geo.getCoordinates().toString(),
                                                            new TypeToken<List<List<List<Double>>>>(){}.getType());

                                                    if (lan != null && lan.size() > 0) {
                                                        List<List<Double>> latlngsDou = lan.get(0);
                                                        if (latlngsDou != null && latlngsDou.size() > 0) {
                                                            List<LatLng> latLngs = new ArrayList<>();
                                                            for (List<Double> t: latlngsDou) {
                                                                if (t.size() > 1) {
                                                                    latLngs.add(new LatLng(t.get(1), t.get(0)));
                                                                }
                                                            }
                                                            view.drawPolygon(latLngs, temp.getName());
                                                        }
                                                    }
                                                    /*List<Double> coordinates = geo.getCoordinates();
                                                    List<LatLng> latLngs = new ArrayList<>();
                                                    for (int i = 0; i < coordinates.size(); i += 2) {
                                                        Log.d(TAG, coordinates.get(i) + ":" + coordinates.get(i + 1));
                                                        LatLng latLng = new LatLng(coordinates.get(i), coordinates.get(i + 1));
                                                        latLngs.add(latLng);
                                                    }
                                                    if (view != null) {
                                                        view.drawPolygon(latLngs, temp.getName());
                                                    }*/
                                                }else {
                                                    Log.d(TAG, "pois遗漏的type: " + type);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Log.d(TAG, e.getMessage());

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void hidePoisArea() {
        view.clearMap();
    }
}
