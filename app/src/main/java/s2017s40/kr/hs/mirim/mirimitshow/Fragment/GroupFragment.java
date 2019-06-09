package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.Activities.EnterGroupActivity;
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.GroupAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class GroupFragment extends Fragment {
    private Services service;
    SharedPreferences sharedPreference;
    public  String email;
    Utils utils = new Utils();
    public static GroupFragment newInstance() {
            return new GroupFragment();
    }
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Group> myDataset;
    private ArrayList<String> myToken;
    Button enterGroup;
    TextView title;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        enterGroup = view.findViewById(R.id.enter_group_btn);

        mRecyclerView = view.findViewById(R.id.group_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        myToken= new ArrayList<>();
        title = view.findViewById(R.id.groupListTitle);
        title.setText("그룹 목록");
        //어탭터

        enterGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EnterGroupActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        showItemList();
    }

    private void setChildFragment(Fragment child) {
        FragmentTransaction childFt = getChildFragmentManager().beginTransaction();
        if (!child.isAdded()) {
            childFt.replace(R.id.child_fragment_container, child);
            childFt.addToBackStack(null);
            childFt.commit();
        }
    }


    public void showItemList(){
        mAdapter = new GroupAdapter(myDataset, new GroupAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position, String token) {
                //클릭 이벤트
                Fragment fg;
                fg = GroupSubFragment.newInstance();
                Bundle bundle = new Bundle(1);
                bundle.putString("groupToken", token);
                fg.setArguments(bundle);
                setChildFragment(fg);
            }
        });
        mRecyclerView.setAdapter(mAdapter);

        sharedPreference = getContext().getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);
        Call<List<Group>> call = service.getusergroups(email);
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if(response.code() == 200){//성공
                    List<Group> getGroupList = response.body();
                    for(Group singleGroup : getGroupList){
                        //if(singleGroup.getImage().getUrl().isEmpty()){
                        myDataset.add(new Group(singleGroup.getToken(),singleGroup.getName(),singleGroup.getColor(),singleGroup.getMembers()));
                        Log.e("그룹 가져옴", singleGroup.getName());
                        mAdapter.notifyItemInserted(0);
                    }
                }else if(response.code() == 400){//실패
                    Toast.makeText(getContext(),"nvalid input, object invalid",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.e("getusergroupsError", t.toString());
            }
        });
    }//showItemList

}
