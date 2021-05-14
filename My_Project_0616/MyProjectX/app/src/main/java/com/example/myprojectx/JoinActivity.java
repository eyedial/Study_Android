package com.example.myprojectx;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myprojectx.ATask.JoinInsert;

import java.util.concurrent.ExecutionException;

public class JoinActivity extends AppCompatActivity {

    String state;

    EditText etId, etPasswd, etName, etPhoneNum, etAddress;
    Button btnJoin, btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        etId = findViewById(R.id.etId);
        etPasswd = findViewById(R.id.etPasswd);
        etName = findViewById(R.id.etName);
        etPhoneNum = findViewById(R.id.etPhoneNum);
        etAddress = findViewById(R.id.etAddress);
        btnJoin = findViewById(R.id.btnJoin);
        btnCancel = findViewById(R.id.btnCancel);

        // 가입하기
        btnJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 서버에 보낼 데이터를 가져온다
                String id = etId.getText().toString();
                String passwd = etPasswd.getText().toString();
                String name = etName.getText().toString();
                String phonenumber =  etPhoneNum.getText().toString();
                String address = etAddress.getText().toString();

                // 서버와 통신을 위해 AsyncTask를 상속한 클래스를 만들어 통신한다.
                JoinInsert joinInsert = new JoinInsert(id, passwd,
                        name, phonenumber, address);
                try {
                    state = joinInsert.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if(state.equals("1")){
                    Toast.makeText(JoinActivity.this,
                            "회원가입을 축하합니다.", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(JoinActivity.this,
                            "다시 회원가입을 해 주세요요", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // 취소
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}