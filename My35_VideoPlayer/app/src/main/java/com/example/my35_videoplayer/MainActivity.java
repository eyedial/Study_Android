package com.example.my35_videoplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends AppCompatActivity {

    public String VIDEO_URL =
            "http://sites.google.com/site/ubiaccessmobile/sample_video.mp4";
    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = findViewById(R.id.videoView);
        MediaController mc = new MediaController(this);
        videoView.setMediaController(mc);

        // 비디오뷰가 준비가 끝났을때
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                Toast.makeText(MainActivity.this,
                        "동영상이 준비됨", Toast.LENGTH_SHORT).show();
            }
        });

        // 비디오뷰의 재생이 완료됐을때
        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                Toast.makeText(MainActivity.this,
                        "동영상 재생 완료", Toast.LENGTH_SHORT).show();
            }
        });

        // 재생버튼 누르면 비디오가 재생됨
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoView.setVideoURI(Uri.parse(VIDEO_URL));
                videoView.requestFocus();
                videoView.start();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        Toast.makeText(this,
                "동영상이 준비중입니다...", Toast.LENGTH_SHORT).show();
    }

}