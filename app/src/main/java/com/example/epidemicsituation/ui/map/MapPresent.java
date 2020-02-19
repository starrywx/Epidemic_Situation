package com.example.epidemicsituation.ui.map;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.amap.api.maps.model.LatLng;
import com.blankj.utilcode.util.ToastUtils;
import com.example.epidemicsituation.Utils.GsonUtils;
import com.example.epidemicsituation.Utils.KeepAliveBackgroundUtil;
import com.example.epidemicsituation.bean.PerTraReqInfo;
import com.example.epidemicsituation.bean.PersonalTraInfo;
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
    public void showPersonalTrajectory(String startTime, String endTime){
        model.getPersonalTrajectory(startTime,endTime)
                .subscribe(new Observer<PersonalTraInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PersonalTraInfo personalTraInfo) {
                        if(personalTraInfo == null) {
                            return;
                        }

                        view.showLoading();

                        if (personalTraInfo.getCode() == 0 && personalTraInfo.getMessage().equals("success")) {
                            List<PersonalTraInfo.DataBean> dataBeans = personalTraInfo.getData();
                            if (dataBeans != null && dataBeans.size() > 0) {
                                List<LatLng> latLngs = new ArrayList<>();
                                for (PersonalTraInfo.DataBean temp: dataBeans) {
                                    latLngs.add(new LatLng(temp.getLat(), temp.getLon()));
                                    Log.d(TAG, temp.getLat() + ":" + temp.getLon());
                                }
                                view.drawTrajectory(latLngs);
                            }
                        }else {
                            view.setPerTraFalse();
                            ToastUtils.showShort(personalTraInfo.getMessage());
                            view.hidLoading();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setPerTraFalse();
                        ToastUtils.showShort("网络异常");
                        view.hidLoading();
                    }

                    @Override
                    public void onComplete() {
                        view.hidLoading();
                    }
                });
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
                        view.showLoading();
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
                        ToastUtils.showShort("网络异常");
                        view.hidLoading();
                        view.setPoisArea();
                    }

                    @Override
                    public void onComplete() {
                        view.hidLoading();
                    }
                });
    }

    @Override
    public void hidePoisArea() {
        view.clearMap();
    }




    /****************************应用保活方法***********************************/
    public void doKeepAliveBackground(){
        //大于Android 6.0 执行弹窗申请电池白名单优化
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ) {
            requestBatteryWhiteList();
        }
        //跳转不同手机厂商系统的后台应用管理页面
        showBackgroundSettingPage();

    }

    /**
     * 设置后台电池白名单优化
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestBatteryWhiteList(){
        if(KeepAliveBackgroundUtil.getInstance().isIgnoringBatteryOptimizations()) {
            ToastUtils.showShort("您已在电池优化白名单中");
        } else {
            KeepAliveBackgroundUtil.getInstance()
                    .requestIgnoreBatteryOptimizations();
        }
    }

    /**
     * 跳转应用设置页面
     */
    private void showBackgroundSettingPage(){
        if(KeepAliveBackgroundUtil.getInstance().isXiaomi()) {
            //前往小米应用设置保活
            KeepAliveBackgroundUtil.getInstance().goXiaomiSetting();
            ToastUtils.showLong("请将应用加入手机自启动名单");
        }else if(KeepAliveBackgroundUtil.getInstance().isHuawei()) {
            //前往华为应用设置保活
            KeepAliveBackgroundUtil.getInstance().goHuaweiSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isOPPO()) {
            //前往Oppo应用设置
            KeepAliveBackgroundUtil.getInstance().goOPPOSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isVIVO()) {
            //前往Vivo应用设置
            KeepAliveBackgroundUtil.getInstance().goVIVOSetting();
        }else if (KeepAliveBackgroundUtil.getInstance().isSamsung()){
            //前往三星应用设置
            KeepAliveBackgroundUtil.getInstance().goSamsungSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isMeizu()) {
            //前往魅族应用设置
            KeepAliveBackgroundUtil.getInstance().goMeizuSetting();
        }else if (KeepAliveBackgroundUtil.getInstance().isSmartisan()) {
            //前往锤子应用设置
            KeepAliveBackgroundUtil.getInstance().goSmartisanSetting();
        }else if(KeepAliveBackgroundUtil.getInstance().isLeTV()) {
            //前往乐视应用设置
            KeepAliveBackgroundUtil.getInstance().goLetvSetting();
        }

    }
}
