package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import s2017s40.kr.hs.mirim.mirimitshow.EnterGroupActivity;
import s2017s40.kr.hs.mirim.mirimitshow.GroupAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.GroupDTO;
import s2017s40.kr.hs.mirim.mirimitshow.R;


public class GroupFragment extends Fragment {
    public static GroupFragment newInstance() {
            return new GroupFragment();
    }
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<GroupDTO> myDataset;
    Button enterGroup;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        enterGroup = view.findViewById(R.id.enter_group_btn);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.group_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        //어탭터
        mAdapter = new GroupAdapter(myDataset, new GroupAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
              //클릭 이벤트
                Fragment fg;
                fg = GroupSubFragment.newInstance();
                setChildFragment(fg);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        myDataset.add(new GroupDTO("3학년6반",String.valueOf(R.mipmap.ic_launcher),"17"));
        myDataset.add(new GroupDTO("3학년5반",String.valueOf(R.mipmap.ic_launcher),"30"));
        myDataset.add(new GroupDTO("2학년3반",String.valueOf(R.mipmap.ic_launcher),"12"));
        myDataset.add(new GroupDTO("2학년1반",String.valueOf(R.mipmap.ic_launcher),"17"));

        enterGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnterGroupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();

        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }
}
