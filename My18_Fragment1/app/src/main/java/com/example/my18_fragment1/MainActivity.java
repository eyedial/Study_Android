package com.example.my18_fragment1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    MainFragment mainFragment;
    SubFragment subFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*mainFragment = (MainFragment) getSupportFragmentManager()
                                .findFragmentById(R.id.mainFragment);*/
        mainFragment = new MainFragment();
        subFragment = new SubFragment();

        // 메인화면에 프래그먼트 초기화 시키기
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, mainFragment).commit();

    }

    // 프래그먼트에서 접근할 수 있게 만든 매소드
    public void onFragmentChange(int state){
        if(state == 1){
            // 화면을 서브프래그먼트로 교체 : 메인프래그먼트 버튼 눌림
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, subFragment).commit();
        }else if(state == 2){
            // 화면을 메인프래그먼트로 교체 : 서브프래그먼트 버튼 눌림
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.contain, mainFragment).commit();
        }
    }

}