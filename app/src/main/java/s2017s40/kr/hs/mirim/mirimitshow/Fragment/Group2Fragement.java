package s2017s40.kr.hs.mirim.mirimitshow.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class Group2Fragement extends Fragment {
    public Group2Fragement() {
        // Required empty public constructor
    }
    private Services service;
    SharedPreferences sharedPreference;
    String email;
    String groupToken;
    Utils utils = new Utils();
    ImageView timetable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_group_2, container, false);
        groupToken = getArguments().getString("groupToken");
        timetable = view.findViewById(R.id.sub_group1_timetable_img);
        service = utils.mRetrofit.create(Services.class);

        Call<Group> call = service.getgroup(groupToken);
        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(response.code() == 200){//성공
                    Group group = response.body();
                    Log.e("group",group.getTimetable().getUrl());
                    //images/timetable/1558541116652.jpeg
                    Picasso.get().load("http://13.125.15.20/" + group.getTimetable().getUrl()).into(timetable);
                    Toast.makeText(getContext(),"returns existing Group",Toast.LENGTH_LONG).show();
                }else if(response.code() == 400){//실패
                    Toast.makeText(getContext(),"invalid input, object invalid",Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Log.e("getgroupError", t.toString());
            }
        });


        return view;
    }
}
