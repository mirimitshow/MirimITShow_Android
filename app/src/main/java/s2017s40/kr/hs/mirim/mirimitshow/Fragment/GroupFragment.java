package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.app.Activity;
import android.content.Intent;
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

import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import android.content.SharedPreferences;
import retrofit2.converter.gson.GsonConverterFactory;
import s2017s40.kr.hs.mirim.mirimitshow.GroupAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.LoginActivity;
import s2017s40.kr.hs.mirim.mirimitshow.MainActivity;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.User;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;


public class GroupFragment extends Fragment {
    private Services service;
    public  String email;
    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;
    Utils utils = new Utils();
    public static GroupFragment newInstance() {
            return new GroupFragment();
    }
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Group> myDataset;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group, container, false);

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
        SharedPreferences sharedPreference = getContext().getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");

        service = utils.mRetrofit.create(Services.class);
        Call<List<Group>> call = service.getusergroups(email);
        call.enqueue(new Callback<List<Group>>() {
                         @Override
                         public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                            if(response.code() == 200){//성공
                                List<Group> getGroupList = response.body();
                                for(Group singleGroup : getGroupList){
                                    myDataset.add(new Group(singleGroup.getName(), String.valueOf(R.mipmap.ic_launcher), String.valueOf(singleGroup.getMembers())));
                                }
                            }else if(response.code() == 400){//실패
                                Toast.makeText(getContext(),"nvalid input, object invalid",Toast.LENGTH_LONG);
                            }
                         }

                         @Override
                         public void onFailure(Call<List<Group>> call, Throwable t) {

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
