package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.GroupSub1Adapter;
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.PaperListAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.Board;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Register;
import s2017s40.kr.hs.mirim.mirimitshow.Scan;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class MyPaperListActivity extends AppCompatActivity {

    PaperListAdapter adapter;
    RecyclerView paperListView;
    String category;
    TextView categoryName_textView;
    String name, email;
    private Services service;
    Utils utils = new Utils();
    private ArrayList<Scan> myDataset;
    SharedPreferences sharedPreference;
    GridLayoutManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paper_list);

        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email", "defValue");
        service = utils.mRetrofit.create(Services.class);
        Intent i = getIntent();
        category = i.getStringExtra("Category");

        categoryName_textView = findViewById(R.id.categoryName_paperList);

        categoryName_textView.setText(category);

        paperListView = findViewById(R.id.paperlist_view);
        paperListView.setHasFixedSize(true);
        manager = new GridLayoutManager(this, 2);
        paperListView.setLayoutManager(manager);
        myDataset = new ArrayList<>();

        adapter = new PaperListAdapter(myDataset, new PaperListAdapter.ClickCallback() {
            @Override
            public void onItemClick(int position, String scanUri, String scanName) {
                Intent i = new Intent(MyPaperListActivity.this, ViewPaperActivity.class);
                i.putExtra("scanUri", scanUri);
                i.putExtra("scanName", scanName);
                i.putExtra("position", position);
                startActivity(i);
            }
        });

        paperListView.setAdapter(adapter);

        service = utils.mRetrofit.create(Services.class);

        Call<List<Scan>> call = service.getscans(email, category);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if (response.code() == 200) {
                    try {
                        List<Scan> getScan = response.body();
                        for (Scan singleScan : getScan) {
                            myDataset.add(singleScan);
                            Log.e("adapter", singleScan.getName());
                            adapter.notifyDataSetChanged();
                        }
                       // Toast.makeText(MyPaperListActivity.this, "SCAN을 완료했습니다.", Toast.LENGTH_LONG).show();
                    } catch (NullPointerException e) {
                        //Toast.makeText(MyPaperListActivity.this, "scan이미지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                } else if (response.code() == 209) {
                  //  Toast.makeText(MyPaperListActivity.this, "scan이미지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                } else if (response.code() == 400) {
                   // Toast.makeText(MyPaperListActivity.this, "scan에 실패하셨습니다.", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {
               // Toast.makeText(MyPaperListActivity.this, "scan에 실패하셨습니다.", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }
}
