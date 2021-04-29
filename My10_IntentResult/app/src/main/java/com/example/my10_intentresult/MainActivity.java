package com.example.my10_intentresult;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Button btnMain;
    TextView tvMain;
    final int Reqcode = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvMain = findViewById(R.id.tvMain);
        btnMain = findViewById(R.id.btnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 인텐트 만들어서 서브1에 데이터 보내기
                PersonDTO personDTO1 = new PersonDTO("HONG", 5678);
                Intent intent = new Intent(MainActivity.this,
                                                        Sub1Activity.class);
                intent.putExtra("id", "KIM");
                intent.putExtra("pw", 1234);
                intent.putExtra("personDTO1", personDTO1);
                startActivityForResult(intent, Reqcode);
            }
        });

    }

    // 4. 서브에서 보낸 데이터 받는곳 : 반드시 오버라이드를 시켜야 함
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //                              1004,         RESULT_OK(-1),    서브에서 보낸 인텐트
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Reqcode){
            if(data != null){
                 String key = data.getStringExtra("key");
                 tvMain.setText(key);
            }
        }else if(requestCode == 1002){

        }

    }


}