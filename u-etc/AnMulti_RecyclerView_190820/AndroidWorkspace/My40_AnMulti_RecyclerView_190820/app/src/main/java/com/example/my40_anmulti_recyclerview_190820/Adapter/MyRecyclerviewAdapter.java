package com.example.my40_anmulti_recyclerview_190820.Adapter;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my40_anmulti_recyclerview_190820.Dto.MyItem;
import com.example.my40_anmulti_recyclerview_190820.OnItemClickListener;
import com.example.my40_anmulti_recyclerview_190820.R;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;


public class MyRecyclerviewAdapter extends RecyclerView.Adapter<MyRecyclerviewAdapter.ItemViewHolder>
                                        implements OnItemClickListener {

    ArrayList<MyItem> arrayList;
    OnItemClickListener listener;

    public MyRecyclerviewAdapter(ArrayList<MyItem> arrayList) {
        this.arrayList = arrayList;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.myitem_view, parent, false);

        return new ItemViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Log.d("main:adater", "" + position);

        MyItem item = arrayList.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    // OnItemClickListener 를 implements 해서 반드시 구현해야 하는 매소드
    @Override
    public void onItemClick(ItemViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder, view, position);
        }
    }
    // 어댑터에 매소드 만들기
    // 모든 항목 백그라운드 색 흰색으로 바꿈
    /*public void setBackgroundColor(ItemViewHolder holder){
        for(int i = 0; i < arrayList.size(); i++){
            onBindViewHolder(holder, i);
        }

    }*/

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


    // 외부에서 클릭리스너를 부를 수 있도록 매소드를 만들어 놓는다
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener = listener;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{

        public TextView id;
        public TextView name;
        public TextView date;
        public ImageView iv_img;
        public ProgressBar progressBar;

        public ItemViewHolder(@NonNull final View itemView, final OnItemClickListener listener) {
            super(itemView);

            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            date = itemView.findViewById(R.id.tv_date);
            iv_img = itemView.findViewById(R.id.iv_img);
            progressBar = itemView.findViewById(R.id.progressBar);

            /* 아이템 클릭되었을때 */
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null){
                        listener.onItemClick(ItemViewHolder.this, view, position);
                    }

                    // 선택된 항목 배경색 바꾸기
                    //itemView.setBackgroundColor(Color.parseColor("#567845"));
                    //itemView.setBackgroundColor(Color.GREEN);
                }
            });
        }

        public void setItem(MyItem item){

            // 모든배경색 흰색 으로
            itemView.setBackgroundColor(Color.WHITE);

            id.setText(item.getId());
            name.setText(item.getName());
            date.setText(item.getDate());

            ImageLoader.getInstance().displayImage(item.getImage_path(), iv_img, new ImageLoadingListener() {
                    @Override
                    public void onLoadingStarted(String s, View view) {
                        progressBar.setVisibility(View.VISIBLE);
                        // Log.d("Sub1 : String s", s);
                    }

                    @Override
                    public void onLoadingFailed(String s, View view, FailReason failReason) {
                        progressBar.setVisibility(View.GONE);
//                            Log.d("Sub1:ImageFail", failReason.getCause().toString());
                    }

                    @Override
                    public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                        progressBar.setVisibility(View.GONE);
                    }

                    @Override
                    public void onLoadingCancelled(String s, View view) {
                        progressBar.setVisibility(View.GONE);
                    }
                });
        }
    }

}



