package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity implements android.text.TextWatcher {
    EditText name, email, pwd, pwdConfirm, phoneNum1, phoneNum2;
    Spinner phoneSpinner, domainSpinner;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name_editText);
        name.addTextChangedListener(this);
        email = findViewById(R.id.signup_email_editText);
        email.addTextChangedListener(this);
        pwd = findViewById(R.id.signup_pass_editText);
        pwd.addTextChangedListener(this);
        pwdConfirm = findViewById(R.id.signup_passConfirm_editText);
        pwdConfirm.addTextChangedListener(this);
        phoneNum1 = findViewById(R.id.signup_middlePhone_editText);
        phoneNum1.addTextChangedListener(this);
        phoneNum2 = findViewById(R.id.signup_lastPhone_editText);
        phoneNum2.addTextChangedListener(this);

        phoneSpinner = findViewById(R.id.signup_phoneNum_Spinner);
        domainSpinner = findViewById(R.id.signup_domain_spinner);

        signupBtn = findViewById(R.id.signup_signUp_btn);

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nameStr = name.getText().toString();

                String emailStr = email.getText().toString(); //이메일의 아이디 부분
                String domainStr = domainSpinner.getSelectedItem().toString(); //이메일의 도메인 부분

                String pwdStr = pwd.getText().toString();
                String pwdConfrimStr = pwdConfirm.getText().toString();

                String firstPhone = phoneSpinner.getSelectedItem().toString(); //첫 3자리
                String middlePhone = phoneNum1.getText().toString(); // 중간 4자리
                String lastPhone = phoneNum2.getText().toString(); // 마지막 4ㅈ리

                //빈 칸이 있는지 검사
                if (nameStr.getBytes().length <= 0 || emailStr.getBytes().length <= 0 || domainStr.getBytes().length <= 0 ||
                        pwdStr.getBytes().length <= 0 || pwdConfrimStr.getBytes().length <= 0 || firstPhone.getBytes().length <= 0 ||
                        middlePhone.getBytes().length <= 0 || lastPhone.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //비밀번호가 확인 비밀번호와 일치하는지 검사
                if(pwdStr != pwdConfrimStr){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
