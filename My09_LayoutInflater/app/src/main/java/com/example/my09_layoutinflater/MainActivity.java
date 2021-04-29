package com.example.my09_layoutinflater;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button button1;
    LinearLayout linear;
    RelativeLayout relative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        linear = findViewById(R.id.linear);
        relative = findViewById(R.id.relative);

        // 화면 인플레이트
        button1 = findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LayoutInflater inflater =
                        (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                inflater.inflate(R.layout.sub1_layout, linear, true);
                inflater.inflate(R.layout.sub2_layout, relative, true);

                Button btnLinear = linear.findViewById(R.id.btnLinear);
                btnLinear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,
                                "button Linear 클릭", Toast.LENGTH_SHORT).show();
                    }
                });

                Button btnRelative = relative.findViewById(R.id.btnSub2);
                btnRelative.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this,
                                "button Relative 클릭", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }
}