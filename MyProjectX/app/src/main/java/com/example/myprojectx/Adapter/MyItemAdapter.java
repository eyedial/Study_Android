package com.example.myprojectx.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.myprojectx.Dto.MyItem;
import com.example.myprojectx.R;

import java.util.ArrayList;
import static com.example.myprojectx.Common.CommonMethod.selItem;

public class MyItemAdapter extends
        RecyclerView.Adapter<MyItemAdapter.ItemViewHolder> {
    private static final String TAG = "main:MyItemAdapter";

    // 메인에서 넘긴것
    Context mContext;
    ArrayList<MyItem> arrayList;
    //

    LayoutInflater inflater;

    public MyItemAdapter(Context mContext, ArrayList<MyItem> arrayList) {
        this.mContext = mContext;
        this.arrayList = arrayList;

        inflater = LayoutInflater.from(mContext);
    }

    // 화면 연결하기
    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater
                .inflate(R.layout.myitem_view, parent, false);

        return new ItemViewHolder(itemView);
    }

    // 데이터 연결하기
    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        MyItem item = arrayList.get(position);
        holder.setItem(item);

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: " + position);

                selItem = arrayList.get(position);

                Toast.makeText(mContext, "Onclick " + arrayList.get(position).getId(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // 어댑터에 매소드 만들기

    // 리사이클러뷰 내용 모두 지우기
    public void removeAllItem(){
        arrayList.clear();
    }

    // 특정 인덱스 항목 가져오기
    public MyItem getItem(int position) {
        return arrayList.get(position);
    }

    // 특정 인덱스 항목 셋팅하기
    public void setItem(int position, MyItem item){
        arrayList.set(position, item);
    }

    // arrayList 통째로 셋팅하기
    public void setItems(ArrayList<MyItem> arrayList){
        this.arrayList = arrayList;
    }


    // 1. myitem_view 에 있는 모든것을 정의하고 찾는다
    public class ItemViewHolder extends RecyclerView.ViewHolder{
        LinearLayout parentLayout;
        ImageView iv_img;
        TextView tv_id, tv_name, tv_date;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            iv_img = itemView.findViewById(R.id.iv_img);
            tv_id = itemView.findViewById(R.id.tv_id);
            tv_name = itemView.findViewById(R.id.tv_name);
            tv_date = itemView.findViewById(R.id.tv_date);
        }

        public void setItem(MyItem item){
            tv_id.setText(item.getId());
            tv_name.setText(item.getName());
            tv_date.setText(item.getDate());
            //                          url경로                  이미지뷰
            Glide.with(itemView).load(item.getImage_path()).into(iv_img);
        }

    }

}
