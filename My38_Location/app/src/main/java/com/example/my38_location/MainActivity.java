package com.example.my38_location;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        textView = findViewById(R.id.textView);

        // 내 위치 확인하기
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startLocationService();
            }
        });

    }

    private void startLocationService() {
        // 위치관리자 객체 참조
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // 위치정보를 받을 리스너 생성
        GPSListener gpsListener = new GPSListener();
        long minTime = 10000; // 10초 마다 갱신
        float minDistance = 0;  // 위치변화가 없더라도 갱신하겠다

        try {
            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, // 위성
                    minTime,
                    minDistance,
                    gpsListener
            );

            /*manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER, // 기지국
                    minTime,
                    minDistance,
                    gpsListener
            );*/

            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                Double latitude = lastLocation.getLatitude();  // 위도
                Double longitude = lastLocation.getLongitude();  // 경도

                String msg = "Latitude : " + latitude + "\nLongitude : " + longitude;

                textView.append("내 위치2 => \n" + msg);
            }

        }catch (SecurityException e){

        }


    }

    // 위치정보 리스너 클래스
    private class GPSListener implements LocationListener{

        @Override
        public void onLocationChanged(@NonNull Location location) {
            Double latitude = location.getLatitude();  // 위도
            Double longitude = location.getLongitude();  // 경도

            String msg = "Latitude : " + latitude + "\nLongitude : " + longitude;

            textView.append("내 위치1 => \n" + msg);
        }
    }


    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_WIFI_STATE
        };

        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (int i = 0; i < permissions.length; i++) {
            permissionCheck = ContextCompat.checkSelfPermission(this, permissions[i]);
            if (permissionCheck == PackageManager.PERMISSION_DENIED) {
                break;
            }
        }

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "권한 있음", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "권한 없음", Toast.LENGTH_LONG).show();

            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                Toast.makeText(this, "권한 설명 필요함.", Toast.LENGTH_LONG).show();
            } else {
                ActivityCompat.requestPermissions(this, permissions, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, permissions[i] + " 권한이 승인됨.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(this, permissions[i] + " 권한이 승인되지 않음.", Toast.LENGTH_LONG).show();
                }
            }
        }
    }

}