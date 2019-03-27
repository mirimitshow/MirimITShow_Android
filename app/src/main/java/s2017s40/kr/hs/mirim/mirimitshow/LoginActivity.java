package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import s2017s40.kr.hs.mirim.mirimitshow.Classes.HttpConnection;
import s2017s40.kr.hs.mirim.mirimitshow.Classes.SignInTaskClass;

public class LoginActivity extends AppCompatActivity {
    EditText idEdit, pwEdit;
    Button loginBtn;
    TextView joinTxt;
    String url = null;
    String pwstr, idstr;
    String resultstr;

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

                idstr= idEdit.getText().toString();
                pwstr = pwEdit.getText().toString();

                if(idstr.equals("tmddus") && pwstr.equals("1234")) {

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT).show();
                }

      //          SignInTaskClass loginTask = new SignInTaskClass();

//                try {
//                    resultstr = loginTask.execute(idstr, pwstr).get();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//
//                if(resultstr.equals("성공")){
//                    Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT);
//                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }else{
//                    Toast.makeText(getApplicationContext(), "로그인 실패", Toast.LENGTH_SHORT);
//                    return;
//                }

            }

        });


        joinTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }

}
