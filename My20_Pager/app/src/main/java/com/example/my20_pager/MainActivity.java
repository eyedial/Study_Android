package com.example.my20_pager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pager = findViewById(R.id.pager);
        // viewpager 사용시 미리 로딩하는 페이지 수
        pager.setOffscreenPageLimit(3);

        MyPagerAdapter adapter =
                new MyPagerAdapter(getSupportFragmentManager());
        Fragment1 fragment1 = new Fragment1();
        Fragment2 fragment2 = new Fragment2();
        Fragment3 fragment3 = new Fragment3();

        adapter.addItem(fragment1);
        adapter.addItem(fragment2);
        adapter.addItem(fragment3);

        pager.setAdapter(adapter);

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pager.setCurrentItem(1);
            }
        });

    }

    class MyPagerAdapter extends FragmentStatePagerAdapter{
        // 뷰페이저에 보일 프래그먼트들을 담는 공간
        ArrayList<Fragment> items = new ArrayList<>();

        public MyPagerAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        // 프래그먼트를 추가하는 매소드
        public void addItem(Fragment item){
            items.add(item);
        }

        // items 인덱스 위치에 있는 프래그먼트를 가져온다
        @NonNull
        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        // items가 가지고 있는 프래그먼트 갯수
        @Override
        public int getCount() {
            return items.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return "페이지 " + (position + 1);
        }
    }

}