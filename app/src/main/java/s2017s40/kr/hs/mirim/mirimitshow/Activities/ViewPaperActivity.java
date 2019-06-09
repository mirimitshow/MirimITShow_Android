package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class ViewPaperActivity extends AppCompatActivity {
    TextView title_txt; // 학습지 이름
    ImageView paperImg; // 학습지 이미지
    String name, email;
    private Services service;
    Utils utils = new Utils();
    SharedPreferences sharedPreference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);


        Intent i = getIntent();
        String title = i.getStringExtra("paperName");

        title_txt = findViewById(R.id.viewPaper_title_text);
        paperImg = findViewById(R.id.viewPaper_paperImg);

        title_txt.setText(title);

       // Picasso.get().load("http://13.125.15.20/" + .into(paperImg);
    }
}
