package com.example.my33_audioplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    String AUDIO_URL =
            "http://sites.google.com/site/ubiaccessmobile/sample_audio.amr";

    MediaPlayer mediaPlayer;
    int position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 재생
        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //playAudio(AUDIO_URL);
                playAudio1(R.raw.m01);
                Toast.makeText(MainActivity.this,
                        "음악파일 재생 시작!!!", Toast.LENGTH_SHORT).show();
            }
        });

        // 중지
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    mediaPlayer.stop();
                    Toast.makeText(MainActivity.this,
                            "음악파일 재생 중지!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 일시정지
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null){
                    position = mediaPlayer.getCurrentPosition();
                    mediaPlayer.pause();

                    Toast.makeText(MainActivity.this,
                            "음악파일 일시정지!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 재시작
        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer != null && !mediaPlayer.isPlaying()){
                    mediaPlayer.start();
                    mediaPlayer.seekTo(position);

                    Toast.makeText(MainActivity.this,
                            "음악파일 재시작!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void playAudio(String AUDIO_URL) {
        killMediaPlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(AUDIO_URL);
            mediaPlayer.prepare();
            mediaPlayer.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void playAudio1(int resId) {
        killMediaPlayer();

        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer = MediaPlayer.create(getApplicationContext(), resId);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void killMediaPlayer() {
        if(mediaPlayer != null){
            mediaPlayer.release();
        }
    }

}