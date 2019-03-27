package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import s2017s40.kr.hs.mirim.mirimitshow.R;


public class MyHomeFragment extends Fragment {
    public MyHomeFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_home, container, false);
        return view;
    }
}
