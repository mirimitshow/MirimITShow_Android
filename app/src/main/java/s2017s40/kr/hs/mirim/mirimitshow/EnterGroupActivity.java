package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterGroupActivity extends AppCompatActivity {
    private Services service;
    Utils utils = new Utils();
    SharedPreferences sharedPreference;
    String email, token;

    Button joinBtn;
    EditText groupCode;
    String GroupCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_group);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);
        joinBtn = findViewById(R.id.join_group);
        groupCode = findViewById(R.id.groupCode_input_edit);


        joinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GroupCodeStr = groupCode.getText().toString(); // 입력받은 그룹코드
                joinGroupMethod();

                // 처리하구 성공하면 finish()
                //finish();
                //Toast.makeText(getApplicationContext(), "그룹에 참여하셨습니다", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void joinGroupMethod(){
        //내 그룹에 추가
        JoinGroup join = new JoinGroup(GroupCodeStr, email);
        Call<JoinGroup> callmy = service.joingroup(join);
        callmy.enqueue(new Callback<JoinGroup>() {
            @Override
            public void onResponse(Call<JoinGroup> call, Response<JoinGroup> response) {
                if(response.code() == 200){
                    Toast.makeText(EnterGroupActivity.this, "success joined group", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(response.code() == 400) {
                    Toast.makeText(EnterGroupActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<JoinGroup> call, Throwable t) {
                Toast.makeText(EnterGroupActivity.this, "t", Toast.LENGTH_SHORT).show();
                Log.e("joingroupError",String.valueOf(t));
            }
        });
    }
}
