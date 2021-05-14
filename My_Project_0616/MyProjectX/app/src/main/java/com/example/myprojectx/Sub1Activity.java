package com.example.myprojectx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.myprojectx.ATask.ListDelete;
import com.example.myprojectx.ATask.ListSelect;
import com.example.myprojectx.Adapter.MyItemAdapter;
import com.example.myprojectx.Dto.MyItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.myprojectx.Common.CommonMethod.isNetworkConnected;
import static com.example.myprojectx.Common.CommonMethod.selItem;

public class Sub1Activity extends AppCompatActivity {

    ListSelect listSelect;

    Button btn1, btn2, btn3, btn4;

    RecyclerView recyclerView;
    ArrayList<MyItem> arrayList;
    MyItemAdapter adapter;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        // 리사이클러 뷰 시작
        arrayList = new ArrayList();
        adapter = new MyItemAdapter(this, arrayList);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this,
                RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setAdapter(adapter);
        //////////

        // 서버에 접근하여 데이터를 가져와 리사이클러뷰에 넣고 갱신한다
        ListSelect listSelect = new ListSelect(arrayList, adapter, progressDialog);
        try {
            listSelect.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    //추가
    public void btn1Clicked(View v){
        if(isNetworkConnected(this) == true){
            Intent intent = new Intent(getApplicationContext(), Sub1Insert.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


    }

    // 수정
    public void btn2Clicked(View v){
        if(isNetworkConnected(this) == true){

            if(selItem != null){
                Log.d("sub1:update1", selItem.getId());

                Intent intent = new Intent(getApplicationContext(), Sub1Update.class);
                intent.putExtra("selItem", selItem);
                startActivity(intent);

            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 삭제
    public void btn3Clicked(View v){
        if(isNetworkConnected(this) == true){
            if(selItem != null){
                Log.d("Sub1 : selImg => ", selItem.getImage_path());

                ListDelete listDelete = new ListDelete(selItem.getId(), selItem.getImage_path());
                try {
                    listDelete.execute().get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                // 화면갱신
                Intent refresh = new Intent(this, Sub1Activity.class);
                startActivity(refresh);
                this.finish(); // 화면끝내기

                adapter.notifyDataSetChanged(); // adapter 갱신
            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요(항목선택)",
                        Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show(); // 테스트 111
        }

    }

    // 돌아가기
    public void btn4Clicked(View v){
        finish();
    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d("Sub1", "onNewIntent() 호출됨");

        // 새로고침하면서 이미지가 겹치는 현상 없애기 위해...
        adapter.removeAllItem();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("데이터 업로딩");
        progressDialog.setMessage("데이터 업로딩 중입니다\n" + "잠시만 기다려주세요 ...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        processIntent(intent);

    }

    private void processIntent(Intent intent){
        if(intent != null){
            listSelect = new ListSelect(arrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}