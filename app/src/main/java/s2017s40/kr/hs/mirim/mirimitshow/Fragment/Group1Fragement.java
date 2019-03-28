package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.DTO.GroupDTO;
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.GroupSub1Adapter;
import s2017s40.kr.hs.mirim.mirimitshow.R;

public class Group1Fragement extends Fragment {
    public Group1Fragement() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SubGroup1DTO> myDataset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_1, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.sub_group1_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        //어탭터
        mAdapter = new GroupSub1Adapter(myDataset, new GroupSub1Adapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new SubGroup1DTO("게시글 1", "오늘의 공지사항"));
        myDataset.add(new SubGroup1DTO("게시글 2", "오늘의 공지사항"));
        myDataset.add(new SubGroup1DTO("게시글 3", "오늘의 공지사항"));
        myDataset.add(new SubGroup1DTO("게시글 4", "오늘의 공지사항"));

        return view;
    }
}
