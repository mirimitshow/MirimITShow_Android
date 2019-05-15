package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class LoginActivity extends AppCompatActivity {
    private Retrofit mRetrofit;
    private Services service;

    EditText idEdit, pwEdit;
    Button loginBtn;
    TextView joinTxt;
    String url = null;
    String pwstr, idstr;

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
                if (!idEdit.toString().isEmpty() && !pwEdit.toString().isEmpty()) {
                    login();
                } else {
                    Toast.makeText(LoginActivity.this , "아이디나 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                }

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
    public void login(){
        init();
        service = mRetrofit.create(Services.class);
        User users = new User(idEdit.toString(), pwEdit.toString());
        Call<User> call = service.signin(users.getEmail(), users.getPassword());
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.e("login","login 완료");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(LoginActivity.this, "아이디나 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("code : ",  String.valueOf(response.code() ));
                }
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
                Log.e("loginError", t.toString());
            }
        });
    }
    public void init(){
        mRetrofit  = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-124-242.ap-northeast-2.compute.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
