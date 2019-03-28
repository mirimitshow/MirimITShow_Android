package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import s2017s40.kr.hs.mirim.mirimitshow.R;

public class GroupSubFragment extends Fragment {
    public static GroupSubFragment newInstance(){
        return new GroupSubFragment();
    }
    FragmentTabHost host;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_sub, container, false);

        host = (FragmentTabHost) view.findViewById(android.R.id.tabhost);
        host.setup(getContext(), getActivity().getSupportFragmentManager(), R.id.content);

        TabHost.TabSpec tabSpec1 = host.newTabSpec("게시글 보기"); // 구분자
        tabSpec1.setIndicator("1"); // 탭 이름
        host.addTab(tabSpec1, Group1Fragement.class, null);

        TabHost.TabSpec tabSpec2 = host.newTabSpec("시간표");
        tabSpec2.setIndicator("2");
        host.addTab(tabSpec2, Group2Fragement.class, null);

        host.getTabWidget().getChildAt(0)
                .setBackgroundColor(Color.parseColor("#3C989E"));
        host.getTabWidget().getChildAt(1)
                .setBackgroundColor(Color.parseColor("#5DB5A4"));

        host.setCurrentTab(0);
        TextView temp = (TextView) host.getTabWidget().getChildAt(0).findViewById(android.R.id.title);
        temp.setTextColor(Color.parseColor("#000000"));

        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) { // 탭 변경시 리스너
                for (int i = 0; i < host.getTabWidget().getChildCount(); i++) {
                    TextView tv = (TextView) host.getTabWidget().getChildAt(i).findViewById(android.R.id.title); // 탭에 있는 TextView 지정후
                    if (i == host.getCurrentTab())
                        tv.setTextColor(Color.parseColor("#000000")); // 탭이 선택되어 있으면 FontColor를 검정색으로
                    else
                        tv.setTextColor(Color.parseColor("#ffffff")); // 선택되지 않은 탭은 하얀색으로.
                }
            }
        });

        return view;
    }
}
