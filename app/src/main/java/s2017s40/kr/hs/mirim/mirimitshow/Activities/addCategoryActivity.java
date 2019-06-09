package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import s2017s40.kr.hs.mirim.mirimitshow.R;

public class addCategoryActivity extends AppCompatActivity {

    EditText categoryName;
    Button addBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        categoryName = findViewById(R.id.category_edit_add);
        addBtn = findViewById(R.id.category_add_btn);


    }
}
