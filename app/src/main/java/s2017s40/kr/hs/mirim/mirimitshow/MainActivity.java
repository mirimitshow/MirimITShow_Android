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

import s2017s40.kr.hs.mirim.mirimitshow.Fragment.GroupFragment;
import s2017s40.kr.hs.mirim.mirimitshow.Fragment.MyHomeFragment;
import s2017s40.kr.hs.mirim.mirimitshow.Fragment.MyPaPerFragment;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private GroupFragment groupFragment = new GroupFragment();
    private MyPaPerFragment mypaperFragment = new MyPaPerFragment();
    private MyHomeFragment myhomeFragment = new MyHomeFragment();
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab = findViewById(R.id.main_fab_FABtn);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddGroupActivity.class);
                Intent intent = new Intent(MainActivity.this, WritePostActivity.class); // test용으로 바꿈
                startActivity(intent);
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        // 첫 화면 지정
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_layout, groupFragment).commitAllowingStateLoss();

        // bottomNavigationView의 아이템이 선택될 때 호출될 리스너 등록
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (item.getItemId()) {
                    case R.id.navigation_group: {
                        transaction.replace(R.id.frame_layout, groupFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_my_paper: {
                        transaction.replace(R.id.frame_layout, mypaperFragment).commitAllowingStateLoss();
                        break;
                    }
                    case R.id.navigation_my_home: {
                        transaction.replace(R.id.frame_layout, myhomeFragment).commitAllowingStateLoss();
                        break;
                    }
                }

                return true;
            }
        });

    }
}
