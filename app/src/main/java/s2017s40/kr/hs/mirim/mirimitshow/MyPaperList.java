package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;

public class MyPaperList extends AppCompatActivity {

    PaperListAdapter adapter;
    GridView paperListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_paper_list);

        paperListView = findViewById(R.id.paperlist_view);
        adapter = new PaperListAdapter();
        adapter.addItem("관동별곡");
        adapter.addItem("관동별곡");
        adapter.addItem("관동별곡");
        adapter.addItem("관동별곡");

        paperListView.setAdapter(adapter);

        paperListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }
}
