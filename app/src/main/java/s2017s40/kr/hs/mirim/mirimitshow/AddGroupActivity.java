package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.QuickContactBadge;
import android.widget.TextView;
import android.widget.Toast;

public class AddGroupActivity extends AppCompatActivity {
    EditText editGroupName;
    TextView textGroupCode;
    LinearLayout addImage;
    Button setTimeTableBtn, setGroupBtn, copyCodeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_);

        textGroupCode = findViewById(R.id.addGroup_groupCode_textView);
        editGroupName = findViewById(R.id.addGroup_groupName_editText);

        addImage = findViewById(R.id.addGroup_addImage_Linear);

        setTimeTableBtn = findViewById(R.id.addGrop_setTimetable_btn);
        setGroupBtn = findViewById(R.id.addGrop_setgroup_btn);
        copyCodeBtn = findViewById(R.id.addGrop_copyCode_btn);



        setTimeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간표 열기
            }
        });


        copyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copyCode", textGroupCode.getText().toString());
                manager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(), "텍스트가 복사되었습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
