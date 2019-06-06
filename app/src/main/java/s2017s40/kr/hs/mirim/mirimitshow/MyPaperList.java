package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyPaperList extends AppCompatActivity {

    PaperListAdapter adapter;
    GridView paperListView;
    String category;
    TextView categoryName_textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paper_list);
        Intent i = getIntent();
        category = i.getStringExtra("Category");
        categoryName_textView = findViewById(R.id.categoryName_paperList);
        categoryName_textView.setText(category);

        paperListView = findViewById(R.id.paperlist_view);
        adapter = new PaperListAdapter();
        adapter.addItem("관동별곡");// 학습지 이름 추가
        adapter.addItem("관동별곡");
        adapter.addItem("관동별곡");
        adapter.addItem("관동별곡");

        paperListView.setAdapter(adapter);

        paperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //여기서 학습지 보는 엑티비티랑 연결

                
            }
        });

    }
}
