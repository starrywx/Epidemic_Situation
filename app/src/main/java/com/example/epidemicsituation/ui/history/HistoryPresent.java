package com.example.epidemicsituation.ui.history;

import com.example.epidemicsituation.bean.HistoryInfo;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HistoryPresent implements HistoryContract.HistoryPresent {

    private HistoryContract.HistoryModel model;
    private HistoryContract.HistoryView view;

    @Override
    public void getHistoryInfo() {
        if(model!=null){
            model.getHistoryInfo().subscribe(new Observer<HistoryInfo>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(HistoryInfo historyInfo) {
                    if(historyInfo.getCode()==1&&historyInfo.getData()!=null){
                        view.showHistoryList(historyInfo.getData());
                    }
                }

                @Override
                public void onError(Throwable e) {

                }

                @Override
                public void onComplete() {

                }
            });
        }
    }

    @Override
    public void attachView(HistoryContract.HistoryView view) {
        this.view = view;
        model = new HistoryModel();
    }

    @Override
    public void detachView() {
        view = null;
        model = null;
    }
}
