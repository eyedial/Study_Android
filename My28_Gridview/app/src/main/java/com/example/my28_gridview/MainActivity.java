package com.example.my28_gridview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnAdd;
    GridView gridView;

    SingerAdapter adapter;
    ArrayList<SingerDto> dtos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 디바이스 사이즈 구하기
        Point size = getDeviceSize();

        dtos = new ArrayList<>();

        btnAdd = findViewById(R.id.btnAdd);
        gridView = findViewById(R.id.gridView);

        // 어댑터 객체를 생성한다
        adapter = new SingerAdapter(MainActivity.this, dtos, size);

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
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SingerDto dto = (SingerDto) adapter.getItem(position);

                Toast.makeText(MainActivity.this, "선택 : " + position
                        + "\n이름 : " + dto.getName() + "\n전화번호 : " + dto.getMobile()
                        + "\n나이 : " + dto.getAge() + "\n이미지 : " + dto.getResId()
                        , Toast.LENGTH_SHORT).show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 어댑터에 있는 ArrayList에 dto를 1개 추가
                adapter.addDto(new SingerDto("박효신", "010-1111-6666",
                        31, R.drawable.dream03));
                // 화면갱신
                adapter.notifyDataSetChanged();
            }
        });
    }

    // 디바이스 가로 세로 사이즈 구하기
    // getRealSize()는 status bar 등 system insets을
    // 포함한 스크린 사이즈를 가져오는 방법이고,
    // getSize()는 status bar 등 system insets를
    // 제외한 스크린 사이즈를 가져오는 함수 입니다.
    // 단위는 픽셀입니다
    private Point getDeviceSize() {
        Display display = getWindowManager().getDefaultDisplay(); // in Activity
        /*getActivity().getWindowManager().getDefaultDisplay()*/ // in fragment
        Point size = new Point();
        display.getRealSize(size); // or getSize()
        int width = size.x; // 디바이스 넓이
        int height = size.y; // 디바이스 높이

        return size;
    }
}