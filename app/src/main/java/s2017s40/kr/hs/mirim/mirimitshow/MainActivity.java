package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import s2017s40.kr.hs.mirim.mirimitshow.Fragment.GroupFragment;
import s2017s40.kr.hs.mirim.mirimitshow.Fragment.MyHomeFragment;
import s2017s40.kr.hs.mirim.mirimitshow.Fragment.MyPaPerFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private GroupFragment groupFragment = new GroupFragment();
    private MyPaPerFragment mypaperFragment = new MyPaPerFragment();
    private MyHomeFragment myhomeFragment = new MyHomeFragment();
    private Animation fab_open, fab_close;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab, fab1, fab2, fab3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);//그룹 만들기
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);//글작성
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);//scan 하기

        fab.setImageResource(R.drawable.plus_btn);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                startActivity(intent);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(MainActivity.this, AddBoardActivity.class);
                startActivity(intent);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                anim();
                Intent intent = new Intent(MainActivity.this, ScanClassActivity.class);
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, groupFragment).commit();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_group: {
                        transaction.replace(R.id.frame_layout, groupFragment).commit();
                        break;
                    }
                    case R.id.navigation_my_paper: {
                        transaction.replace(R.id.frame_layout, mypaperFragment).commit();
                        break;
                    }
                    case R.id.navigation_my_home: {
                        transaction.replace(R.id.frame_layout, myhomeFragment).commit();
                        break;
                    }
                }
                return true;
            }
        });

    }
    public void anim() {
        if (isFabOpen) {
            fab.setImageResource(R.drawable.plus_btn);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab3.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            fab3.setClickable(false);
            isFabOpen = false;
        } else {
            fab.setImageResource(R.drawable.cancel_sign);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab3.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            fab3.setClickable(true);
            isFabOpen = true;
        }
    }
}
