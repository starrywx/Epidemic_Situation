package com.example.epidemicsituation.service;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;

import androidx.core.app.NotificationCompat;

import com.example.epidemicsituation.App;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.Utils.ACache;
import com.example.epidemicsituation.Utils.OperationUtils;
import com.example.epidemicsituation.bean.HistoryInfo;
import com.example.epidemicsituation.net.RetrofitManager;
import com.example.epidemicsituation.ui.dialog.SuspiciousDialog;

import org.json.JSONArray;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AutoNotificationService extends Service {

    private ACache aCache;

    @Override
    public void onCreate() {
        super.onCreate();
        aCache= ACache.get(App.getContext());
        aCache.put("history","");
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            String channelId = "suspicious";
            String channelName = "接触历史预警";
            NotificationChannel channel = new NotificationChannel(channelId,channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timingEarlyWaring();
        AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
        int interval = 24*60*60*1000;  //任务时间间隔
        long triggerAtTime = SystemClock.elapsedRealtime()+interval;
        Intent i=new Intent(this,AutoNotificationService.class);
        PendingIntent pi = PendingIntent.getService(this,0,i,0);
        if (manager != null) {
            manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,triggerAtTime,pi);
        }
        return super.onStartCommand(intent, flags, startId);
    }

    //定时任务
    private void timingEarlyWaring(){
        RetrofitManager.getInstance()
                .getApiService()
                .getHistoryInfo()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HistoryInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HistoryInfo historyInfo) {
                        //对比本次和上一次的历史，若相同则不提示，不相同则提示
                        if(aCache.getAsJSONArray("history").equals("")||!aCache.getAsJSONArray("history").equals(historyInfo.getData())){
                            //第一次比较
                            if(OperationUtils.isBackground(App.getContext())){
                                //通知
                                NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                Notification notification = new NotificationCompat.Builder(App.getContext(),"suspicious")
                                        .setContentText("您为新冠患者的密切接触者！点击查看详情。")
                                        .setSmallIcon(R.mipmap.logo)
                                        .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.alert))
                                        .setWhen(System.currentTimeMillis())
                                        .setAutoCancel(true)
                                        .build();
                                manager.notify(2,notification);
                            }else {
                                //弹窗
                                SuspiciousDialog suspiciousDialog = new SuspiciousDialog(App.getContext());
                                suspiciousDialog.show();
                            }
                            aCache.clear();
                            aCache.put("history", (JSONArray) historyInfo.getData());
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
