package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
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
import s2017s40.kr.hs.mirim.mirimitshow.Adapter.PaperListAdapter;
import s2017s40.kr.hs.mirim.mirimitshow.Board;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Register;
import s2017s40.kr.hs.mirim.mirimitshow.Scan;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class MyPaperListActivity extends AppCompatActivity {

    PaperListAdapter adapter;
    GridView paperListView;
    String category;
    TextView categoryName_textView;
    String name, email;
    private Services service;
    Utils utils = new Utils();
    private ArrayList<Scan> myDataset;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paper_list);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);

        Intent i = getIntent();
        category = i.getStringExtra("Category");

        categoryName_textView = findViewById(R.id.categoryName_paperList);
        categoryName_textView.setText(category);

        myDataset = new ArrayList<>();

        paperListView = findViewById(R.id.paperlist_view);
        adapter = new PaperListAdapter();

        service = utils.mRetrofit.create(Services.class);
        Call<List<Scan>> call = service.getscans(email,category);
        call.enqueue(new Callback<List<Scan>>() {
            @Override
            public void onResponse(Call<List<Scan>> call, Response<List<Scan>> response) {
                if (response.code() == 200) {
                    try{
                        List<Scan> getScan = response.body();
                        for(Scan singleScan : getScan){
                            adapter.addItem(singleScan);
                            Log.e("adapter", singleScan.getName());
                        }
                    }catch (NullPointerException e){
                        Toast.makeText(MyPaperListActivity.this, "scan이미지가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                    }
                }else if(response.code() == 209){
                    Toast.makeText(MyPaperListActivity.this, "No Scanned images found", Toast.LENGTH_LONG).show();
                }else if(response.code() == 400){
                    Toast.makeText(MyPaperListActivity.this, "invalid input, object invalid", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<List<Scan>> call, Throwable t) {
                Toast.makeText(MyPaperListActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
               t.printStackTrace();
            }
        });

        paperListView.setAdapter(adapter);

        paperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MyPaperListActivity.this, ViewPaperActivity.class);
                //intent.putExtra("paperName", adapter.getItem(id).getClass());
                //intent.putExtra("paperUri", adapter.getItem(id).getClass());
                startActivity(intent);
            }
        });
    }
}
