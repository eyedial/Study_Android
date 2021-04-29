package com.example.my30_recyclerview2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    SingerAdapter adapter;
    ArrayList<SingerDto> dtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 반드시 생성해서 어댑터에 넘겨야 함
        dtos = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);

        // 리사이클러뷰에서 반드시 초기화 시켜야함
        LinearLayoutManager layoutManager = new LinearLayoutManager(
               this, RecyclerView.VERTICAL, false
        );
        recyclerView.setLayoutManager(layoutManager);

        // 어댑터 객체를 생성한다
        adapter = new SingerAdapter(dtos, MainActivity.this);

        // 어댑터에 있는 ArrayList에 dto를 5개 추가한다
        adapter.addDto(new SingerDto("블랙핑크", "010-1111-1111",
                25, R.drawable.singer1));
        adapter.addDto(new SingerDto("걸스데이", "010-1111-2222",
                27, R.drawable.singer2));
        adapter.addDto(new SingerDto("방탄소년단", "010-1111-3333",
                22, R.drawable.singer3));
        adapter.addDto(new SingerDto("마마무", "010-1111-4444",
                30, R.drawable.singer4));
        adapter.addDto(new SingerDto("소녀시대", "010-1111-5555",
                28, R.drawable.singer5));
        // 만든 어댑터를 리스트뷰에 붙인다
        recyclerView.setAdapter(adapter);

        // 어댑터에 있는 클릭리스너를 이용해 클릭한 위치의 dto를 가져온다
        adapter.setOnItemClickListener(new OnSingerItemClickListener() {
            @Override
            public void onItemClick(SingerAdapter.ViewHolder holderm, View view, int position) {
                // 클릭하면 어댑터에게 받은 클릭위치를 이용해 dto 가져온다
                SingerDto dto = adapter.getItem(position);

                Toast.makeText(MainActivity.this, dto.getMobile(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}