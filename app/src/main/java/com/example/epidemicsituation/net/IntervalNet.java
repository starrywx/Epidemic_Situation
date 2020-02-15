package com.example.epidemicsituation.net;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 轮询
 */
public class IntervalNet {

    private static final String TAG = "IntervalNet";

    private IntervalEventListener listener;
    private int invertalTime;
    private TimeUnit timeUnit;
    private Disposable d;

    public IntervalNet(IntervalEventListener listener, int invertalTime, TimeUnit timeUnit) {
         this.listener = listener;
         this.invertalTime = invertalTime;
         this.timeUnit = timeUnit;
    }

    public void startInvertal() {
        Observable.interval(0, invertalTime, timeUnit)
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        IntervalNet.this.d = d;
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "第" + aLong + "次轮询");
                        if (listener != null) {
                            listener.intervalDo();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void stop() {
        if (d != null) {
            if (!d.isDisposed()) {
                d.dispose();
            }
        }
    }
}
