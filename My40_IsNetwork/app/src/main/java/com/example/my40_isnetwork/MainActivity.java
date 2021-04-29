package com.example.my40_isnetwork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    WifiReceiver wifiReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // 연결상태 확인
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnectivity();
            }
        });
        wifiReceiver = new WifiReceiver();
    }

    // Wifi나 일반망으로 연결되었는지 알아봄
    private void checkConnectivity() {
        ConnectivityManager manager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if(info != null){
            if(info.getType() == ConnectivityManager.TYPE_WIFI){
                println("Wifi로 연결됨");
            }else if(info.getType() == ConnectivityManager.TYPE_MOBILE){
                println("일반망으로 연결됨");
            }

            println("연결여부 : " + info.isConnected());
        }else {
            println("데이터 통신 불가");
        }

    }

    private void println(String str) {
        textView.append(str + "\n");
    }

    // 화면이 동작이 가능해질때 receiver를 등록함
    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(wifiReceiver, filter);
    }

    // 화면이 정지상태로 접어들때 receiver를 해지함
    @Override
    protected void onStop() {
        super.onStop();

        unregisterReceiver(wifiReceiver);
    }

    // 백그라운드에서 작동하는 BroadcastReceiver 클래스 생성
    class WifiReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // 와이파이 상태가 변할때 (와이파이켜짐, 꺼짐)
            if(action.equals(WifiManager.WIFI_STATE_CHANGED_ACTION)){
                int state = intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, -1);
                if(state == WifiManager.WIFI_STATE_ENABLED){
                    println("WiFi enabled");
                }else if(state == WifiManager.WIFI_STATE_DISABLED){
                    println("WiFi disabled");
                }
            // 네트워크 상태가 변할때 (와이파이 이름(SSID)이 변경될때)
            }else if(action.equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)){
                NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                WifiManager manager = (WifiManager) getApplicationContext()
                        .getSystemService(Context.WIFI_SERVICE);
                String ssid = manager.getConnectionInfo().getSSID();

                if(info.getState() == NetworkInfo.State.CONNECTED){
                    println("Connected : " + ssid);
                }else if(info.getState() == NetworkInfo.State.DISCONNECTED){
                    println("Disconneted : " + ssid);
                }

            }

        }
    }




}