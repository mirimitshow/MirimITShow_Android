package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.concurrent.TimeUnit;

import okhttp3.JavaNetCookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private Retrofit mRetrofit;
    private Services service;

    public static final int CONNECT_TIMEOUT = 15;
    public static final int WRITE_TIMEOUT = 15;
    public static final int READ_TIMEOUT = 15;

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
                if (idEdit.toString().isEmpty() || pwEdit.toString().isEmpty()) {
                    Toast.makeText(LoginActivity.this , "아이디나 비밀번호를 입력하세요", Toast.LENGTH_SHORT).show();
                } else {
                    login();
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
        User users = new User(idEdit.getText().toString(), pwEdit.getText().toString());
        Call<User> call = service.signin(users);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.code() == 200) {
                    Log.e("login",String.valueOf(response.code() ));
                    shared();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(LoginActivity.this, "로그인이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Log.e("l",idEdit.getText().toString() + "  " + pwEdit.getText().toString());
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
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //쿠키 메니저의 cookie policy를 변경 합니다.
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .build();

        mRetrofit  = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-124-242.ap-northeast-2.compute.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }
    public void shared(){
        SharedPreferences email = getSharedPreferences("email", Activity.MODE_PRIVATE);
        SharedPreferences.Editor Login = email.edit();
        Login.putString("email",idEdit.getText().toString()); // 저장
        Login.commit();

        Log.e("ddd",email.getString("email","defValue")); // 가져오기
    }
}
