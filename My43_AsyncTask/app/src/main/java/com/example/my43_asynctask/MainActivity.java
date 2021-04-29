package com.example.my43_asynctask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

public class MainActivity extends AppCompatActivity {

    BackgroundTask backgroundTask;
    ProgressBar progressBar;
    int value = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);

        // 실행
        findViewById(R.id.btnExe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AsyncTask 객체 만들고 실행하기
                backgroundTask = new BackgroundTask(progressBar, value);
                backgroundTask.execute();
            }
        });

        // 취소
        findViewById(R.id.btnCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(backgroundTask != null) {
                    backgroundTask.cancel(true);
                }
            }
        });

    }
}