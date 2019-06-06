package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import s2017s40.kr.hs.mirim.mirimitshow.Classes.NotiClass;
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.NotiListAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;


public class MyHomeFragment extends Fragment {
    public MyHomeFragment() {
        // Required empty public constructor
    }
    private Services service;
    SharedPreferences sharedPreference;
    public  String email;
    Utils utils = new Utils();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_home, container, false);
        ListView NotiListView = view.findViewById(R.id.NotiListView);
        NotiListAdapter adapter = new NotiListAdapter();
        NotiListView.setAdapter(adapter);

        adapter.setItem(new NotiClass("3-5", "게시물이 등록되었습니다", false));
        adapter.setItem(new NotiClass("3-6", "공지가 등록되었습니다", true)); // isNotice 가 true면 공지 -> 노란 표시

        return view;
    }
}
