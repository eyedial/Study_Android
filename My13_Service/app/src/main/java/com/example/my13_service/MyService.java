package com.example.my13_service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    private static final String TAG = "main:MyService";

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: 실행됨");
    }

    // 서비스가 실행되면 무조건 onStartCommand를 실행한다.
    @Override //        메인에서 넘겨준 인텐트, 플래그, 실행될때 보이는 숫자
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d(TAG, "onStartCommand: " + flags + ", " + startId);
        // 비정상적으로 종료되었을때
        if(intent == null){
            return Service.START_STICKY; // 재시작해서 정상적으로 만든다
        }else {
            processCommand(intent);
        }

        return super.onStartCommand(intent, flags, startId);
    }

    private void processCommand(Intent intent) {
        String command = intent.getStringExtra("command");
        String name = intent.getStringExtra("name");
        Log.d(TAG, "processCommand: " + name + ", " + command);

        // background에서 실행되는 것을 log로 찍어서 보여줌
        for(int i=0; i<5; i++){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "Waiting " + (i+1) + " seconds...");
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 실행됨");
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}