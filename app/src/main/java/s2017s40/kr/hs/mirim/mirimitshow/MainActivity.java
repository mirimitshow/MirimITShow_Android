package s2017s40.kr.hs.mirim.mirimitshow;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager = getSupportFragmentManager();
    // 4개의 메뉴에 들어갈 Fragment들
    private GroupFragment groupFragment = new GroupFragment();
    private MyPaPerFragment mypaperFragment = new MyPaPerFragment();
    private MyHomeFragment myhomeFragment = new MyHomeFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
