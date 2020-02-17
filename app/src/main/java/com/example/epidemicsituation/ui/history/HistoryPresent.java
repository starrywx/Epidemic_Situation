package com.example.epidemicsituation.ui.history;

public class HistoryPresent implements HistoryContract.HistoryPresent {

    private HistoryContract.HistoryModel model;
    private HistoryContract.HistoryView view;

    @Override
    public void getHistoryInfo() {
        if(model!=null){
            model.getHistoryInfo();
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
