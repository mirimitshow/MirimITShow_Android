package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.regex.Pattern;

public class SignupActivity extends AppCompatActivity {
    EditText name, email, pwd, pwdConfirm, phoneNum1, phoneNum2;
    Spinner phoneSpinner, domainSpinner;
    Button signupBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        name = findViewById(R.id.signup_name_editText);
        email = findViewById(R.id.signup_email_editText);
        pwd = findViewById(R.id.signup_pass_editText);
        pwdConfirm = findViewById(R.id.signup_passConfirm_editText);
        phoneNum1 = findViewById(R.id.signup_middlePhone_editText);
        phoneNum2 = findViewById(R.id.signup_lastPhone_editText);

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

             

            }
        });

    }
}
