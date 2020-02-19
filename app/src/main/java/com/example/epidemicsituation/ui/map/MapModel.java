package com.example.epidemicsituation.ui.map;

import android.util.Log;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.WeightedLatLng;
import com.example.epidemicsituation.Utils.GsonUtils;
import com.example.epidemicsituation.bean.PerTraReqInfo;
import com.example.epidemicsituation.bean.PersonalTraInfo;
import com.example.epidemicsituation.entity.PoisArea;
import com.example.epidemicsituation.net.PoisAreaManager;
import com.example.epidemicsituation.net.RetrofitManager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class MapModel implements MapContract.MapModel{

    private static final String TAG = "MapModel";

    @Override
    public Observable<List<WeightedLatLng>> getHeatPoint() {
        return null;
    }

    @Override
    public Observable<PersonalTraInfo> getPersonalTrajectory(String startTime, String endTime) {
        PerTraReqInfo reqInfo = new PerTraReqInfo();
        reqInfo.setStartTime(startTime);
        reqInfo.setEndTime(endTime);
        /*String reqJson = GsonUtils.GsonString(reqInfo);
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json;charset=UTF-8"),reqJson);*/
        return RetrofitManager.getInstance()
                .getApiService()
                .getPersonalTraInfo(reqInfo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<PoisArea>> getPoisArea() {
        Log.d(TAG, "model请求疫情区域数据");
        return PoisAreaManager.getInstance()
                .getApiService()
                .getPoisArea()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<ResponseBody, List<PoisArea>>() {
                    @Override
                    public List<PoisArea> apply(ResponseBody requestBody) throws Exception {
                        String json = requestBody.string();
                        Gson gson = new Gson();
                        Log.d(TAG, json);
                        List<PoisArea> list = null;
                        if (gson != null) {
                            list = gson.fromJson(json, new TypeToken<List<PoisArea>>() {
                            }.getType());
                        }
                        Log.d(TAG, list.get(0).getPois().get(0).getGeometry().getCoordinates().toString());
                        return list;
                    }
                });
    }
}
