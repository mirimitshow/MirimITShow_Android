package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
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
import s2017s40.kr.hs.mirim.mirimitshow.Board;
import s2017s40.kr.hs.mirim.mirimitshow.GroupSub1Adapter;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;
import s2017s40.kr.hs.mirim.mirimitshow.ViewBoardActivity;

public class Group1Fragement extends Fragment {
    public Group1Fragement() {
        // Required empty public constructor
    }
    View view;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Board> myDataset;
    String groupToken;
    private Services service;
    SharedPreferences sharedPreference;
    public  String email;
    Utils utils = new Utils();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_group_1, container, false);
        groupToken = getArguments().getString("groupToken");

        Log.e("groupToken",groupToken);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.sub_group1_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        myDataset = new ArrayList<>();

        //어탭터
        mAdapter = new GroupSub1Adapter(myDataset, new GroupSub1Adapter.ClickCallback() {
            @Override
            public void onItemClick(int position) {
                Intent i = new Intent(getActivity(), ViewBoardActivity.class);
                i.putExtra("BoardToken",myDataset.get(position).getGroup_token());
                i.putExtra("position", position);
                startActivity(i);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        service = utils.mRetrofit.create(Services.class);
        Call<List<Board>> call = service.getgroupboards(groupToken);
        call.enqueue(new Callback<List<Board>>() {
            @Override
            public void onResponse(Call<List<Board>> call, Response<List<Board>> response) {
                if(response.code() == 200){//성공
                    List<Board> getBoardList = response.body();
                    try{
                        for(Board singleBoard : getBoardList){
                            myDataset.add(new Board(singleBoard.getAuthor(), singleBoard.getTitle()));
                            Log.e("Board",singleBoard.getTitle());
                        }
                    }catch (NullPointerException e){
                        Toast.makeText(getContext(),"게시글이 존재하지 않습니다.",Toast.LENGTH_LONG).show();
                    }
                    Toast.makeText(getContext(),"returns user's Groups",Toast.LENGTH_LONG).show();
                } else if(response.code() == 209){//실패
                    Toast.makeText(getContext(),"No board found",Toast.LENGTH_LONG).show();
                }else if(response.code() == 400){//실패
                    Toast.makeText(getContext(),"invalid input, object invalid",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Board>> call, Throwable t) {
                Log.e("getgroupboardsError", t.toString());
            }
        });

        return view;
    }
}
