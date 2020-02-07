package com.sslavik.servicebind;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.FileDescriptor;

public class MainActivity extends AppCompatActivity {

    // FIELDS
    Button btnStartService;
    Button btnGetTime;
    Button btnStopService;
    TextView tvTime;

    // INTENT
    Intent timeServiceIntent;

    // LISTENER
    OnClickListener onClickListener;

    // SERVICE CONNECTION
    TimeService timeService;
    ServiceConnection serviceConnection = new ServiceConnection() {
        // Se ejecuta tiene que ser después del bindService
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            timeService = ((TimeService.ChronometerBinder) service).getService();
            Log.d(TimeService.TAG, "OnServiceConnectedInMainActivity()");
        }
        // No se ejecuta después de un UnBindService. Tiene que ser otra manera
        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TimeService.TAG, "OnServiceDisconnectedInMainActivity()");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // START VIEWS
        btnStartService = findViewById(R.id.btnStartService);
        btnGetTime = findViewById(R.id.btnGetTime);
        btnStopService = findViewById(R.id.btnStopService);
        tvTime = findViewById(R.id.tvTime);

        // START INTENT
        timeServiceIntent = new Intent(MainActivity.this, TimeService.class);

        initOnClickListener();

        // SET LISTENERS

        btnStartService.setOnClickListener(onClickListener);
        btnGetTime.setOnClickListener(onClickListener);
        btnStopService.setOnClickListener(onClickListener);

        //serviceConnection.onServiceConnected(, timeBinder);
    }



    // START ONCLICKLISTENER
    private void initOnClickListener(){
        onClickListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btnStartService:
                        bindService(timeServiceIntent, serviceConnection, BIND_AUTO_CREATE);
                        break;
                    case R.id.btnGetTime:
                        tvTime.setText(timeService.getTime());
                        break;
                    case R.id.btnStopService:
                        break;
                }
            }
        };
    }

}
