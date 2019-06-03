package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class GroupSubFragment extends Fragment {
    public static GroupSubFragment newInstance(){
        return new GroupSubFragment();
    }
    FragmentTabHost host;
    private Services service;
    SharedPreferences sharedPreference;
    public  String email;
    Utils utils = new Utils();
    String token;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_sub, container, false);

        token = getArguments().getString("groupToken"); // 전달한 key 값

        host = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        host.setup(getContext(), getActivity().getSupportFragmentManager(), R.id.content);

        Bundle args = new Bundle();
        args.putString("groupToken", token);
        Log.e("token", token);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("게시글 보기"); // 구분자
        tabSpec1.setIndicator("게시글 보기"); // 탭 이름
        host.addTab(tabSpec1, Group1Fragement.class, args);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("시간표");
        tabSpec2.setIndicator("시간표");
        host.addTab(tabSpec2, Group2Fragement.class, args);

        host.getTabWidget().getChildAt(0)
                .setBackgroundColor(Color.parseColor("#ffffff"));
        host.getTabWidget().getChildAt(1)
                .setBackgroundColor(Color.parseColor("#ffffff"));

        host.setCurrentTab(0);
        TextView temp = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        temp.setTextColor(Color.parseColor("#000000"));

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) { // 탭 변경시 리스너
                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); // 탭에 있는 TextView 지정후
                    if (i == host.getCurrentTab())
                        tv.setTextColor(Color.parseColor("#000000"));
                    else
                        tv.setTextColor(Color.parseColor("#FF9800"));
                }
            }
        });

        return view;
    }
}
