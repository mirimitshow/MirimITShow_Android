package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.MyPaPerDTO;
import s2017s40.kr.hs.mirim.mirimitshow.PaPerAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;


public class MyPaPerFragment extends Fragment {
    public MyPaPerFragment() {
        // Required empty public constructor
    }
    private Services service;
    SharedPreferences sharedPreference;
    public  String email;
    Utils utils = new Utils();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<MyPaPerDTO> myDataset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_paper, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.paper_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        //어탭터
        mAdapter = new PaPerAdapter(myDataset, new PaPerAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                //클릭 이벤트
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new MyPaPerDTO("수학","100장"));
        myDataset.add(new MyPaPerDTO("국어","10장"));
        myDataset.add(new MyPaPerDTO("NMT","1장"));
        myDataset.add(new MyPaPerDTO("가통","20장"));

        return view;
    }
}
