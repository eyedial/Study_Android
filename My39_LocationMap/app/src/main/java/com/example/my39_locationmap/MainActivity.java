package com.example.my39_locationmap;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Telephony;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    SupportMapFragment mapFragment;
    GoogleMap map;
    EditText editText;

    MarkerOptions myMarker;

    LatLng myLoc, markerLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        editText = findViewById(R.id.editText);

        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                Log.d(TAG, "onMapReady: Google Map is Ready !!!" );

                map = googleMap;
                try{
                    map.setMyLocationEnabled(true);
                }catch (SecurityException e){

                }

            }
        });

        // 구글맵 초기화
        MapsInitializer.initialize(this);

        // 내위치 찾기
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestMyLocation();
            }
        });

        // 한글주소를 지도로 보여주기
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().length() > 0){
                    Location location = getLocationFromAddress
                            (getApplicationContext(), editText.getText().toString());

                    showCurrentLocation(location);
                }
            }
        });

    }

    // 한글주소를 받아서 Location 형태로 변경시켜서 보내주는 매소드
    private Location getLocationFromAddress(Context context, String address) {
        Geocoder geocoder = new Geocoder(context);
        List<Address> addresses;
        Location resLocation = new Location("");

        try{
            addresses = geocoder.getFromLocationName(address, 5);
            if((addresses == null) && (addresses.size() == 0)){
                return null;
            }
            Address addressLoc = addresses.get(0);
            resLocation.setLatitude(addressLoc.getLatitude());
            resLocation.setLongitude(addressLoc.getLongitude());

        }catch (IOException e){

        }
        return resLocation;
    }

    private void requestMyLocation() {
        LocationManager manager =
                (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        try{
            long minTime = 10000;
            float minDistance = 0;

            manager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }
                    }
            );

            /*manager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    minTime,
                    minDistance,
                    new LocationListener() {
                        @Override
                        public void onLocationChanged(@NonNull Location location) {
                            showCurrentLocation(location);
                        }
                    }
            );*/

            Location lastLocation =
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if(lastLocation != null){
                String msg = "Latitude : " + lastLocation.getLatitude()
                        + "\nLongitude : " + lastLocation.getLongitude();
                Log.d(TAG, "showCurrentLocation2: " + msg);
            }

        }catch (SecurityException e){

        }

    }

    private void showCurrentLocation(Location location) {
        LatLng curPoint =
                new LatLng(location.getLatitude(), location.getLongitude());
        // 현재 내위치 전역변수에 넣음
        myLoc = curPoint;

        String msg = "Latitude : " + curPoint.latitude
                + "\nLongitude : " + curPoint.longitude;
        Log.d(TAG, "showCurrentLocation1: " + msg);

        map.animateCamera(CameraUpdateFactory.newLatLngZoom(curPoint, 18));

        // 마커찍기
        Location targetLocation = new Location("");
        targetLocation.setLatitude(35.153817);
        targetLocation.setLongitude(126.8889);
        showMyLocationMarker(targetLocation);

        Location location2 = new Location("");
        location2.setLatitude(35.153825);
        location2.setLongitude(126.8885);
        showMyLocationMarker(location2);
    }

    // location 받아서 마커 생성하여 지도에 표시하기
    private void showMyLocationMarker(Location location){

        markerLoc = new LatLng(location.getLatitude(), location.getLongitude());

        int distance = (int)getDistance(myLoc, markerLoc);
        Log.d(TAG, "onMarkerClick: distance => " + distance + "M");

        if(myMarker == null){
            myMarker = new MarkerOptions();
            myMarker.position(
                    new LatLng(location.getLatitude(), location.getLongitude()));
            myMarker.title("◎ 내 위치\n");
            myMarker.snippet("거리 => " + distance + " 미터");
            myMarker.icon
                    (BitmapDescriptorFactory.fromResource(R.drawable.mylocation));
            map.addMarker(myMarker);
            myMarker = null;
        }

        /*this.map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                markerLoc = marker.getPosition();

                Double distance = getDistance(myLoc, markerLoc);
                Log.d(TAG, "onMarkerClick: distance => " + distance + "M");
                return true;
            }
        });*/
    }

    // 두 지점간의 거리 구하기
    private double getDistance(LatLng myLocation, LatLng markerLocation){
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(myLocation.latitude);
        locationA.setLongitude(myLocation.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(markerLocation.latitude);
        locationB.setLongitude(markerLocation.longitude);
        distance = locationA.distanceTo(locationB);

        return distance;
    }

    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
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