package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    EditText idEdit, pwEdit;
    Button loginBtn;
    TextView joinTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        idEdit = findViewById(R.id.login_idInput_editText);
        pwEdit = findViewById(R.id.login_pwInput_editText);
        loginBtn = findViewById(R.id.login_login_btn);
        joinTxt = findViewById(R.id.login_join_txt);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        joinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
            }
        });

    }
}
