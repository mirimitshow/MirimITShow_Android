package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.UserHandle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.net.URL;
import java.util.ArrayList;


public class GroupFragment extends Fragment {
    public GroupFragment() {
        // Required empty public constructor
    }
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<GroupDTO> myDataset;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.group_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        mAdapter = new GroupAdapter(myDataset);
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new GroupDTO("d",String.valueOf(R.mipmap.ic_launcher)));
        return view;
    }
}
