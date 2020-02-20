package com.example.epidemicsituation.ui.history;

import com.example.epidemicsituation.Base.BasePresent;
import com.example.epidemicsituation.bean.HistoryInfo;

import java.util.List;

import io.reactivex.Observable;

public interface HistoryContract {

    interface HistoryView{
        void showHistoryList(List<HistoryInfo.DataBean> dataBeanList);
    }

    interface HistoryModel{
        Observable<HistoryInfo> getHistoryInfo();
    }

    interface HistoryPresent extends BasePresent<HistoryView> {
        void getHistoryInfo();
    }
}
