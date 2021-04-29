package com.example.lg.my07_alllayout;


import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    ImageView imageView01;
    ImageView imageView02;
    ImageView imageView03;
    ScrollView scrollView01;

    int imageIndex = 0;
    int index = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView01 = findViewById(R.id.imageView01);
        imageView02 = findViewById(R.id.imageView02);
        imageView03 = findViewById(R.id.imageView03);
        //scrollView01 = (ScrollView) findViewById(R.id.scrollView01);

        //scrollView01.setHorizontalScrollBarEnabled(true);

    }

    public void btnImgChangedClicked(View view){
        if (imageIndex == 0) {
            imageView01.setVisibility(View.VISIBLE);
            imageView02.setVisibility(View.INVISIBLE);
            imageIndex = 1;
        } else if (imageIndex == 1) {
            imageView01.setVisibility(View.INVISIBLE);
            imageView02.setVisibility(View.VISIBLE);
            imageIndex = 0;
        }
    }


    public void onButton1Clicked(View v) {
        index = (index == 1) ? 2 : 1;
        changeImage(index);
    }

    private void changeImage(int index) {
        if(index == 1) {
            imageView03.setImageResource(R.drawable.image01);
        }
        if(index == 2) {
            imageView03.setImageResource(R.drawable.image02);
        }
    }
}
