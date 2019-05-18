package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.Intent;
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
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s2017s40.kr.hs.mirim.mirimitshow.GroupAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.LoginActivity;
import s2017s40.kr.hs.mirim.mirimitshow.MainActivity;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.SharedPreferenceUtil;
import s2017s40.kr.hs.mirim.mirimitshow.User;


public class GroupFragment extends Fragment {
    private Retrofit mRetrofit;
    private Services service;

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;

    SharedPreferenceUtil sharedPreference = new SharedPreferenceUtil(getContext());
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

        sharedPreference.getSharedTest();
        init();
        service = mRetrofit.create(Services.class);
        Group group = new Group();
        Call<Group> call = service.getgroup(group);
        call.enqueue(new Callback<Group>() {
                         @Override
                         public void onResponse(Call<Group> call, Response<Group> response) {

                         }

                         @Override
                         public void onFailure(Call<Group> call, Throwable t) {

                         }
                     });

                myDataset.add(new Group("3학년6반", String.valueOf(R.mipmap.ic_launcher), "17"));
        myDataset.add(new Group("3학년5반",String.valueOf(R.mipmap.ic_launcher),"30"));
        myDataset.add(new Group("2학년3반",String.valueOf(R.mipmap.ic_launcher),"12"));
        myDataset.add(new Group("2학년1반",String.valueOf(R.mipmap.ic_launcher),"17"));


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
    public void init(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //쿠키 메니저의 cookie policy를 변경 합니다.
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();

        mRetrofit  = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-124-242.ap-northeast-2.compute.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
}
