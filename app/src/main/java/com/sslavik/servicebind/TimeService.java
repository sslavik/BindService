package com.sslavik.servicebind;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Chronometer;

import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class TimeService extends Service {

    public static String TAG = "TimeService";
    private IBinder iBinderListener;
    private Chronometer chronometer;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreateTimeService()");
        initBinder();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBindSuccessful()");
        return iBinderListener;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbindSuccessfully()");
        return true;
    }

    @Override
    public boolean stopService(Intent name) {
        Log.d(TAG, "servicesStopped()");
        return super.stopService(name);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommandTimeService()");
        chronometer.setBase(0);
        chronometer.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        chronometer.stop();
    }

    private void initBinder() {
        chronometer = new Chronometer(this);
        iBinderListener = new ChronometerBinder();

    }


    public String getTime(){
        return String.valueOf( new Date(chronometer.getBase()));
    }

    /***
     * Crea una clase interna que implementa la interfaz IBinder y devuleve la instancia del Servicio
     *
     * De esta manera tendremos acceso a los metodos implementados del IBinder desde la Activity
     */

    public class ChronometerBinder extends Binder{
        public TimeService getService(){
            return TimeService.this;
        }
    }
}
