package com.example.my17_orientation1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button button1;
    String name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showToast("onCreat() 호출됨");

        editText = findViewById(R.id.editText);
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = editText.getText().toString();
                showToast("입력된 값을 변수에 저장하였습니다 : " + name);
            }
        });

        if(savedInstanceState != null){
            name = savedInstanceState.getString("name");
            showToast("값을 복원하였습니다 : " + name);
        }

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putString("name", name);
    }

    @Override
    protected void onStart() {
        super.onStart();
        showToast("onStart() 호출됨");
    }

    @Override
    protected void onStop() {
        super.onStop();
        showToast("onStop() 호출됨");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        showToast("onDestroy() 호출됨");
    }

    private void showToast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }
}