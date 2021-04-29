package com.example.my30_recyclerview2;

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

import java.util.ArrayList;

public class SingerAdapter extends
        RecyclerView.Adapter<SingerAdapter.ViewHolder>
            implements OnSingerItemClickListener{
    private static final String TAG = "main:SingerAdapter";

    // 1. 리스너 선언
    OnSingerItemClickListener listener;

    // 메인에서 넘겨 받는것
    ArrayList<SingerDto> dtos;
    Context context;

    LayoutInflater inflater;

    public SingerAdapter(ArrayList<SingerDto> dtos, Context context) {
        this.dtos = dtos;
        this.context = context;

        inflater = LayoutInflater.from(this.context);
    }

    // 2. 화면을 인플레이트 시키면서 클릭리스너를 달고 들어간다
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.singerview,
                                                parent, false);
        return new ViewHolder(itemView, this);
    }

    // 인플레이트 시킨 화면에 데이터를 셋팅시킨다
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + position);

        SingerDto dto = dtos.get(position);
        // 뷰홀더에 만들어 놓은 setDto에 선택된 dto를 넘긴다
        holder.setDto(dto);

        // 쓰레기통 클릭하여 항목 삭제하기
        holder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeDto(position);
                notifyDataSetChanged();
            }
        });

        // 항목을 클릭하여 그 항목 dto를 가져올수 있다
        /*holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SingerDto dto1 = dtos.get(position);
                Toast.makeText(context, "이름 : " + dto1.getName(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return dtos.size();
    }

    // ArrayList<SingerDto>에 dto를 추가할수 있도록 매소드를 만든다
    public void addDto(SingerDto dto){
        dtos.add(dto);
    }

    // ArrayList<SingerDto>에 특정위치에 dto를 제거할수 있도록 매소드를 만든다
    public void removeDto(int position){
        dtos.remove(position);
    }

    // 6. 메인에서 클릭한 위치에 있는 dto 가져오기
    public SingerDto getItem(int position){
        return dtos.get(position);
    }

    // 4. 메인에서 클릭리스너를 클릭했을때 어댑터의 리스너와 연결해준다
    public void setOnItemClickListener(OnSingerItemClickListener listener){
        this.listener = listener;
    }

    // 인터페이스에 정의된 매소드 구현
    // 5. 뷰홀더의 클릭리스너를 실행해 클릭한 위치를 메인에게 알려준다
    @Override
    public void onItemClick(ViewHolder holderm, View view, int position) {
        if(listener != null){
            listener.onItemClick(holderm, view, position);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        // singerview에 있는 모든 위젯을 정의한다
        TextView tvName, tvMobile;
        ImageView ivImage, ivTrash;
        LinearLayout parentLayout;

        // singerview에 정의한 아이디와 연결시킨다 (생성자)
        public ViewHolder(@NonNull View itemView, OnSingerItemClickListener listener) {
            super(itemView);

            parentLayout = itemView.findViewById(R.id.parentLayout);
            tvName = itemView.findViewById(R.id.tvName);
            tvMobile = itemView.findViewById(R.id.tvMobile);
            ivImage = itemView.findViewById(R.id.ivImage);
            ivTrash = itemView.findViewById(R.id.ivTrash);

            // 3. 클릭리스너를 달아준다
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this,
                                                view, position);
                    }
                }
            });
        }

        // 함수를 만들어서 singerview에 데이터를 셋팅시킨다.
        public void setDto(SingerDto dto){
            tvName.setText(dto.getName());
            tvMobile.setText(dto.getMobile());
            ivImage.setImageResource(dto.getResId());
        }

    }

}
