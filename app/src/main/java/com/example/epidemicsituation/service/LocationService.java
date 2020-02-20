package com.example.epidemicsituation.service;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.example.epidemicsituation.App;
import com.example.epidemicsituation.R;
import com.example.epidemicsituation.bean.RealTimeLocation;
import com.example.epidemicsituation.bean.RiskInfo;
import com.example.epidemicsituation.net.RetrofitManager;
import com.example.epidemicsituation.ui.dialog.RealTimeRiskDialogActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class LocationService extends Service implements AMapLocationListener {

    private static final String TAG = "LocationService";

    private static final String CHANNEL_ID = "com.example.epidemicsituation";

    private static final String CHANNEL_NAME = "获取用户位置";

    private static final String DESCRIPTION = "该服务将记录您的实时轨迹，以便在您进入疫情区域时提醒您";

    private static final String TITLE = "疫情监控服务";

    public AMapLocationClient mLocationClient;

    //声明AMapLocationClientOption对象
    public AMapLocationClientOption mLocationOption;

    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /*
     * @param intent
     * @param flags
     * @param startId
     * @return
     */
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //处理任务
        return START_STICKY;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("foreground", "onCreate");
        //重启定位
        mLocationClient = new AMapLocationClient(App.getContext());
        mLocationClient.setLocationListener(this);
        //重启定位
        mLocationClient.stopLocation();
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationPurpose(AMapLocationClientOption.AMapLocationPurpose.Transport);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //五分钟轮询一次
        mLocationOption.setInterval(300 * 1000);
        mLocationClient.setLocationOption(mLocationOption);
        mLocationClient.startLocation();
        //如果API在26以上即版本为O则调用startForefround()方法启动服务
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            setForegroundService();
        }
    }

    /**
     *通过通知启动服务
     */
    @TargetApi(Build.VERSION_CODES.O)
    public void  setForegroundService()
    {
        //设置通知的重要程度
        int importance = NotificationManager.IMPORTANCE_LOW;
        //构建通知渠道
        NotificationChannel channel;
        channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, importance);

        channel.setDescription(DESCRIPTION);
        //在创建的通知渠道上发送通知
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle(TITLE)//设置通知标题
                .setContentText(DESCRIPTION)//设置通知内容
                .setAutoCancel(false) //用户触摸时，自动关闭
                .setOngoing(true);//设置处于运行状态
        //向系统注册通知渠道，注册后不能改变重要性以及其他通知行为
        NotificationManager notificationManager = (NotificationManager) getSystemService( Context.NOTIFICATION_SERVICE);
        if (notificationManager != null) {
            notificationManager.createNotificationChannel(channel);
        }
        //将服务置于启动状态 NOTIFICATION_ID指的是创建的通知的ID
        startForeground(1,builder.build());
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        double lat = aMapLocation.getLatitude();
        double lng = aMapLocation.getLongitude();
        Log.d(TAG, lat + ":" + lng);
        RealTimeLocation realTimeLocation = new RealTimeLocation();
        realTimeLocation.setLat(lat);
        realTimeLocation.setLon(lng);
        //获取当前时间
        Date date = new Date();
        String strDateFormat = "yyyy-MM-dd HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);
        String time = sdf.format(date);
        Log.d(TAG, time);
        realTimeLocation.setTime(time);
        RetrofitManager.getInstance()
                .getApiService()
                .uploadLocationInfo(realTimeLocation)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<RiskInfo>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(RiskInfo riskInfo) {
                        if (riskInfo != null && riskInfo.isData()) {
                            Intent intent = new Intent(LocationService.this, RealTimeRiskDialogActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }

                        Log.d(TAG, "code:" + riskInfo.getCode() + "\n" + "data:" + riskInfo.isData());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
