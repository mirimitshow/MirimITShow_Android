package s2017s40.kr.hs.mirim.mirimitshow;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class Group1Fragement extends Fragment {
    public Group1Fragement() {
        // Required empty public constructor
    }

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<GroupDTO> myDataset;

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

        myDataset.add(new GroupDTO("d",String.valueOf(R.mipmap.ic_launcher)));

        return view;
    }
}
