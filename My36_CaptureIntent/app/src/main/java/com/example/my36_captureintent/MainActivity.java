package com.example.my36_captureintent;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    File imgFile = null;
    String imgFilePath = null;
    ImageView imageView;

    public int reqPicCode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkDangerousPermissions();

        imageView = findViewById(R.id.imageView);

        // 사진찍기 : API24이상부터는 file:// 접근불가, FileProvider를 사용
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                if(picIntent.resolveActivity(getPackageManager()) != null){
                    imgFile = null;
                    imgFile = createFile();

                    // 파일 프로바이더 정의
                    Uri imgUri = FileProvider.getUriForFile(getApplicationContext(),
                            getApplicationContext().getPackageName()
                                    + ".fileprovider", imgFile);

                    // 누가버전 이상이면 파일 프로바이더 사용
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){  // API 24 이상
                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
                    }else {
                        picIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imgFile));
                    }

                    startActivityForResult(picIntent, reqPicCode);
                }

            }
        });

    }

    // 파일을 생성할때 서로 다른 이름의 파일을 생성하기
    private File createFile() {
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                .format(new Date());
        String imageFileName = "My" + timestamp;
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File curFile = null;
        try {
            curFile = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        imgFilePath = curFile.getAbsolutePath();
        Log.d(TAG, "createFile: " + imgFilePath);

        return curFile;
    }

    // 사진찍은후 데이터를 받는곳 반드시 오버라이드 시켜야함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == reqPicCode && resultCode == RESULT_OK){
            // 이미지 저장하기
            galleryAddPic();
            // 이미지 가져오기
            setPic();
        }

    }

    // 갤러리에 사진 추가
    private void galleryAddPic() {
        Intent mediaScanIntent =
                new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(imgFilePath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    // 이미지뷰의 크기에 맞게 디코딩하여 이미지 가져오기
    private void setPic() {
        // 이미지뷰의 크기 알아오기
        int targetW = imageView.getWidth();
        int targetH = imageView.getHeight();

        // 비트맵 크기 알아오기
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // 얼마나 조정하여 이미지를 다운 스케일 할것인가 결정한다
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // 이미지파일을 비트맵 사이즈에 맞게 조정한다
        bmOptions.inJustDecodeBounds =false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(imgFilePath, bmOptions);
        imageView.setImageBitmap(bitmap);

    }


    // 위험권한 선언
    private void checkDangerousPermissions() {
        String[] permissions = {
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
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