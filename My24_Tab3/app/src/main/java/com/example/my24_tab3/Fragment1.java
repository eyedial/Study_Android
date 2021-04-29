package com.example.my24_tab3;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

public class Fragment1 extends Fragment {
    MainActivity activity;
    String sendData, receiveData;
    Person person1;

    Button button1;
    TextView textView1;

    // 화면이 붙을때 초기화 시키기
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        // 프래그먼트가 속한 액티비티 가져옴
        activity = (MainActivity) getActivity();
        // 프래그먼트1에서 보낼 문자열과 객체
        sendData = "프래그먼트1에서 보낸 데이터입니다.";
        person1 = new Person("HONG", 25);
        // 프래그먼트3에서 받을 문자열 변수 초기화
        receiveData = "";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment1,
                container, false);

            textView1 = viewGroup.findViewById(R.id.textView1);
            button1 = viewGroup.findViewById(R.id.button1);

        // 프래그먼트3에서 보낸 데이터 받기
        if(activity.mBundle != null){
            Bundle bundle = activity.mBundle;
            receiveData = bundle.getString("sendData");
            Person person3 = (Person) bundle.getSerializable("person3");
            String name = person3.getName();
            int age = person3.getAge();

            textView1.setText(receiveData + "\n");
            textView1.append("name : " + name + "\nage : " + age );

            activity.mBundle = null;
        }

            // 프래그먼트2로 데이터 보내기
            button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("sendData", sendData);
                    bundle.putSerializable("person1", person1);
                    bundle.putInt("index", 0);

                    // 메인 액티비티에 번들 만들어서 보내기
                    activity.fragBtnClicked(bundle);

                    // 메인액티비티에 프래그먼트2로 화면전환 요청
                    TabLayout.Tab tab = activity.tabs.getTabAt(1);
                    tab.select();
                }
            });

        return viewGroup;
    }
}
