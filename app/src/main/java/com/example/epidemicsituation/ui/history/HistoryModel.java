package com.example.epidemicsituation.ui.history;

import com.example.epidemicsituation.bean.HistoryInfo;
import com.example.epidemicsituation.net.RetrofitManager;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class HistoryModel implements HistoryContract.HistoryModel {
    @Override
    public Observable<HistoryInfo> getHistoryInfo() {
        return RetrofitManager.getInstance()
                .getApiService()
                .getHistoryInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
