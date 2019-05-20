package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EnterGroupActivity extends AppCompatActivity {

    Button joinBtn;
    EditText groupCode;
    String GroupCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_group);

        joinBtn = findViewById(R.id.join_group);
        groupCode = findViewById(R.id.groupCode_input_edit);


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCodeStr = groupCode.getText().toString(); // 입력받은 그룹코드

                // 처리하구 성공하면 finish()
                //finish();
                //Toast.makeText(getApplicationContext(), "그룹에 참여하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
