package com.example.my28_gridview;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class SingerAdapter extends BaseAdapter {
    private static final String TAG = "main:SingerAdapter";
    // 메인에서 보내주는것
    Context context;
    ArrayList<SingerDto> dtos;
    Point size;

    // 화면을 붙이기 위한 객체 생성
    LayoutInflater inflater;
    AlertDialog dialog;

    public SingerAdapter(Context context, ArrayList<SingerDto> dtos, Point size) {
        this.context = context;
        this.dtos = dtos;
        this.size = size;

        this.inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // 메소드는 무조건 여기에 생성한다
    // 하나의 dto 추가하기
    public void addDto(SingerDto dto){
        dtos.add(dto);
    }
    // ArrayList<SingerDto> 내용 모두 지우기
    public void removeDtos(){
        dtos.clear();
    }
    /////////////////////////////////

    // ArrayList 에 저장된 dto 갯수
    @Override
    public int getCount() {
        return dtos.size();
    }

    // ArrayList 특정위치에 있는 dto 가져오기
    @Override
    public Object getItem(int position) {
        return dtos.get(position);
    }

    //
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 가장 중요함 : 화면을 생성하고 클릭이벤트를 만들수 있다
    // 만약 화면 5개를 생성한다면 getView가 5번 실행된다
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.d(TAG, "getView: " + position);

        SingerViewHolder viewHolder;

        // 캐시된 뷰가 없을 경우 새로 뷰홀더를 생성한다
        if(convertView == null){
            // 화면을 붙인다
            convertView = inflater.inflate(R.layout.singerview,
                    parent, false);

            viewHolder = new SingerViewHolder();
            viewHolder.tvName = convertView.findViewById(R.id.tvName);
            viewHolder.tvMobile = convertView.findViewById(R.id.tvMobile);
            viewHolder.ivImage = convertView.findViewById(R.id.ivImage);
            viewHolder.ivTrash = convertView.findViewById(R.id.ivTrash);

            convertView.setTag(viewHolder);
        }else { // 캐시된 뷰가 있을 경우 저장된 뷰홀더를 사용한다
            viewHolder = (SingerViewHolder) convertView.getTag();
        }

        // 선택된 dto 데이터 가져오기
        SingerDto dto = dtos.get(position);
        String name = dto.getName();
        String mobile = dto.getMobile();
        int resImage = dto.getResId();

        // 화면에 데이터 연결하기
        viewHolder.tvName.setText(name);
        viewHolder.tvMobile.setText(mobile);
        viewHolder.ivImage.setImageResource(resImage);

        // 이미지 클릭시
        viewHolder.ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "선택 : " + position
                        + "\n이름 : " + dtos.get(position).getName(), Toast.LENGTH_SHORT).show();

                // 이미지뷰를 동적으로 생성해서 해당 이미지 보여줌
                // popUpImg(dtos.get(position).getResId());

                // xml파일 추가하여 붙이기
                popUpImgXml(dtos.get(position));
            }
        });

        // 휴지통 클릭시 그 항목 삭제하는 리스너
        viewHolder.ivTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dtos.remove(position);
                notifyDataSetChanged();
            }
        });

        // 만들어진 뷰를 반환
        return convertView;
    }

    private void popUpImgXml(SingerDto dto) {
        // 1. res에 xml파일을 만든다
        // 2. 그 xml파일을 inflate 시켜 setView 한다

        // 팝업창에 xml 붙이기
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popupimg, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView tvName = view.findViewById(R.id.tvName);
        TextView tvMobile = view.findViewById(R.id.tvMobile);

        imageView.setImageResource(dto.getResId());
        tvName.setText(dto.getName());
        tvMobile.setText(dto.getMobile());

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("이미지 띄우기").setView(view);

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();

        // 디바이스 사이즈를 받아 팝업크기창을 조절한다
        int sizeX = size.x; // 디바이스 넓이
        int sizeY = size.y; // 디바이스 높이

        // 다이얼로그의 창 위치와 크기 조절
        WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
        params.x = (int) Math.round(sizeX * 0.005); // x위치
        params.y = (int) Math.round(sizeY * 0.01);  // y위치
        params.width = (int) Math.round(sizeX * 0.9); // 창넓이
        params.height = (int) Math.round(sizeY * 0.8); // 창높이
        dialog.getWindow().setAttributes(params);

    }

    private void popUpImg(int resId) {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(resId);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("이미지 띄우기").setView(imageView);

        builder.setNegativeButton("종료", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(dialog != null){
                    dialog.dismiss();
                }
            }
        });
        dialog = builder.create();
        dialog.show();
    }


    public class SingerViewHolder{
        public ImageView ivImage, ivTrash;
        public TextView tvName, tvMobile;
    }


}
