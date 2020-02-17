package com.example.epidemicsituation.ui.history;

import com.example.epidemicsituation.Base.BasePresent;
import com.example.epidemicsituation.bean.HistoryInfo;

import io.reactivex.Observable;

public interface HistoryContract {

    interface HistoryView{
        void showHistoryList(HistoryInfo.DataBean dataBean);
    }

    interface HistoryModel{
        Observable<HistoryInfo> getHistoryInfo();
    }

    interface HistoryPresent extends BasePresent<HistoryView> {
        void getHistoryInfo();
    }
}
