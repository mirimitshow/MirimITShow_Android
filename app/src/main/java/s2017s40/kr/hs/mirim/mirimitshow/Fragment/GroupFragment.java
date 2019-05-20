package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.app.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.GroupAdapter;
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.group_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();
        myToken= new ArrayList<>();
        //어탭터
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

        SharedPreferences sharedPreference = getContext().getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");

        service = utils.mRetrofit.create(Services.class);
        Call<List<Group>> call = service.getusergroups(email);
        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                Log.e("dsfsfsfsfsfsf",response.body().toString());
                if(response.code() == 200){//성공
                    List<Group> getGroupList = response.body();
                    for(Group singleGroup : getGroupList){
                        myDataset.add(new Group(singleGroup.getToken(),singleGroup.getName(), String.valueOf(R.mipmap.ic_launcher), singleGroup.getMembers()));
                    }
                    Toast.makeText(getContext(),"returns user's Groups",Toast.LENGTH_LONG).show();
                }else if(response.code() == 400){//실패
                    Toast.makeText(getContext(),"nvalid input, object invalid",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Log.e("getusergroupsError", t.toString());
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
