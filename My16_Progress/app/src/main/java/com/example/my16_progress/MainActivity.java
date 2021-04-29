package com.example.my16_progress;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "main:MainActivity";

    ProgressBar progressBar;
    EditText editText;
    ProgressDialog dialog;
    SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        editText = findViewById(R.id.editText);
        seekBar = findViewById(R.id.seekBar);

        // 정해진 값을 사용하는지?
        progressBar.setIndeterminate(false);
        progressBar.setMax(100);
        progressBar.setProgress(20);

        findViewById(R.id.button1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editText.getText().toString().length() > 0) {
                    int value = Integer.parseInt(editText.getText().toString());
                    progressBar.setProgress(value);
                }else {
                    Toast.makeText(MainActivity.this, "숫자를 입력하세요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 다이얼로그 보여주기
        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(MainActivity.this);
                dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                dialog.setMessage("데이터를 확인하는 중입니다 ...");
                dialog.setCanceledOnTouchOutside(false);
                dialog.show();
            }
        });

        // 다이얼로그 닫기
        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override //                                  내가 변경한 값,  유저가 변경했는가
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                //editText.setText("" + progress);
                editText.setText(String.valueOf(progress));
                Log.d(TAG, "onProgressChanged: " + fromUser);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }
}