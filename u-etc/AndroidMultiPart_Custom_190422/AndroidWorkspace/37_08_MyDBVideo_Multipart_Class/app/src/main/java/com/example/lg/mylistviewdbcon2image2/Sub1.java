package com.example.lg.mylistviewdbcon2image2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.lg.mylistviewdbcon2image2.ATask.ListDelete;
import com.example.lg.mylistviewdbcon2image2.ATask.ListSelect;
import com.example.lg.mylistviewdbcon2image2.Adapter.MyListAdapter;
import com.example.lg.mylistviewdbcon2image2.Dto.MyItem;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static com.example.lg.mylistviewdbcon2image2.Common.CommonMethod.isNetworkConnected;


public class Sub1 extends AppCompatActivity {

    ListSelect listSelect;

    ArrayList<MyItem> myItemArrayList;
    Button btn1, btn2, btn3, btn4;
    ListView listView;
    MyListAdapter adapter;

    MyItem selItem = null;
    String selName, selDate, selId, selImg, selUploadType, selVideoImage;

    ImageLoader imageLoader;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub1);

        // 이미지 로딩 써드파트
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.drawable.blank) // resource or drawable
                .showImageForEmptyUri(R.drawable.blank) // resource or drawable
                .showImageOnFail(R.drawable.blank)// resource or drawable
                .build();

        ImageLoaderConfiguration config =
                new ImageLoaderConfiguration.Builder(getApplicationContext())
                      //  .imageDownloader(new BaseImageDownloader(getApplicationContext(), 5 * 1000, 10 * 1000))
                        .defaultDisplayImageOptions(options)
                        .build();

        imageLoader.getInstance().init(config); // Get singleton instance

        //ListView시작
        myItemArrayList = new ArrayList<MyItem>();
        adapter = new MyListAdapter(this, R.layout.sub1_item, myItemArrayList);
        listView = findViewById(R.id.listView1);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 리스트뷰에 커스텀어댑터를 적용한거에서 각항목 가져오기
                selItem = (MyItem) adapter.getItem(position);

                selId = selItem.getId();
                selName = selItem.getName();
                selDate = selItem.getDate();
                selImg = selItem.getImage1();
                selUploadType = selItem.getUploadType();
                selVideoImage = selItem.getVideoimage();


                Log.d("Sub1 : selitem", selId + " : " + selImg);
            }
        });
        //ListView종료

        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);

        //myVideoview1 = findViewById(R.id.videoView1);

//        listThreadView();  // xml파싱하여 DB select 하기
        listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
        listSelect.execute();
    }

    //추가
    public void btn1Clicked(View v){
        if(isNetworkConnected(this) == true){
            Intent intent = new Intent(getApplicationContext(), Sub1Add.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }


    }

    // 수정
    public void btn2Clicked(View v){
        if(isNetworkConnected(this) == true){
            if(selId != null && !selId.equals("")){
                Intent intent = new Intent(getApplicationContext(), Sub1Updatee.class);
                intent.putExtra("id", selId);
                intent.putExtra("name", selName);
                intent.putExtra("date", selDate);
                intent.putExtra("img", selImg);
                intent.putExtra("uploadType", selUploadType);
                intent.putExtra("selVideoImage", selVideoImage);

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
            if(selImg != null && !selImg.equals("")) {
                Log.d("Sub1 : selImg => ", selImg);

                ListDelete listDelete = new ListDelete(selId, selImg, selUploadType);
                listDelete.execute();

                // 화면갱신
                Intent refresh = new Intent(this, Sub1.class);
                startActivity(refresh);
                this.finish(); //

                adapter.notifyDataSetChanged();
            }else {
                Toast.makeText(getApplicationContext(), "항목 선택을 해 주세요",
                        Toast.LENGTH_SHORT).show();
            }


        }else {
            Toast.makeText(this, "인터넷이 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT).show();
        }

    }

    // 돌아가기
    public void btn4Clicked(View v){
        finish();
    }

    // 이미 화면이 있을때 받는곳
    @Override
    protected void onNewIntent(Intent intent) {
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
            listSelect = new ListSelect(myItemArrayList, adapter, progressDialog);
            listSelect.execute();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

}