package com.example.my32_navigationdrawer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    Toolbar toolbar;
    Fragment1 fragment1;
    Fragment2 fragment2;
    Fragment3 fragment3;

    FloatingActionButton fab;
    NavigationView navigationView;
    DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.nav_view);
        // implement Listener 할때는 반드시 아래와 같이 표기한다
        navigationView.setNavigationItemSelectedListener(this);

        // toolbar를 적용시킬려면 theme에 가서 NoActionBar로 변경해야 한다.
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.drawer_open
                ,  R.string.drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, fragment1).commit();

        // 헤드 드로어에 접근해서 로그인 정보 표시하기
        int userLevel = 1;  // 일반유저:0,   관리자:1
        String loginID = "BTS";
        View headerView = navigationView.getHeaderView(0);

        TextView navLoginID = headerView.findViewById(R.id.loginID);
        navLoginID.setText("반갑습니다" + loginID + "님!!!");
        ImageView imageView = headerView.findViewById(R.id.loginImage);
        Glide.with(this)
                .load(R.drawable.dream03)
                .circleCrop()
                .into(imageView);
        //imageView.setImageResource(R.drawable.dream03);

        if(userLevel == 0){
            navigationView.getMenu().findItem(R.id.communi).setVisible(false);
        }else if(userLevel == 1){
            navigationView.getMenu().findItem(R.id.communi).setVisible(true);
        }

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action",
                        Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    // 메뉴에 있는 아이템을 클릭했을때
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 클릭한 아이템의 id를 얻는다.
        int id = item.getItemId();

        if(id == R.id.nav_home){
            Bundle bundle = new Bundle();
            bundle.putString("id", "KIM");
            onFragmentSelected(0, bundle);
        }else if(id == R.id.nav_gallery){
            onFragmentSelected(1, null);
        }else if(id == R.id.nav_slideshow){
            onFragmentSelected(2, null);
        }else if(id == R.id.nav_tools){
            onFragmentSelected(3, null);
        }else if(id == R.id.nav_share){
            onFragmentSelected(4, null);
        }else if(id == R.id.nav_send){
            onFragmentSelected(5, null);
        }

        // 메뉴 선택 후 드로어가 사라지게 한다
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void onFragmentSelected(int position, Bundle bundle) {
        Fragment curFragment = null;

        if(position == 0){
            curFragment = fragment1;
            toolbar.setTitle("첫번째 화면");
        }else if(position == 1){
            curFragment = fragment2;
            toolbar.setTitle("두번째 화면");
        }else if(position == 2){
            curFragment = fragment3;
            toolbar.setTitle("세번째 화면");
        }else if(position == 3){
            curFragment = fragment1;
            toolbar.setTitle("네번째 화면");
        }else if(position == 4){
            curFragment = fragment2;
            toolbar.setTitle("다섯번째 화면");
        }else if(position == 5){
            curFragment = fragment3;
            toolbar.setTitle("여섯번째 화면");
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.contain, curFragment).commit();
    }

    // 뒤로가기 눌렀을때 만약 드로어 창이 열려 있으면 드로어 창을 닫고
    // 아니면 그냥 뒤로가기 원래 작업을 한다(여기서는 앱 종료)
    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }

    }

}