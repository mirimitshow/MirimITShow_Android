package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import s2017s40.kr.hs.mirim.mirimitshow.R;

public class ViewPaperActivity extends AppCompatActivity {
    TextView title_txt; // 학습지 이름
    ImageView paperImg; // 학습지 이미지
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_paper);
        Intent i = getIntent();
        String title = i.getStringExtra("paperName");

        title_txt = findViewById(R.id.viewPaper_title_text);
        paperImg = findViewById(R.id.viewPaper_paperImg);

        title_txt.setText(title);

    }
}
