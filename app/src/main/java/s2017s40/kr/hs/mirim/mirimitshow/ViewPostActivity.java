package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewPostActivity extends AppCompatActivity {
    TextView postTitle, postContent;
    ImageView postImage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);

        Intent i = getIntent();
        String position = i.getExtras().toString();


    }
}
