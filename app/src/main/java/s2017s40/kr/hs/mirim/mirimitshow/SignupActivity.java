package s2017s40.kr.hs.mirim.mirimitshow;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.regex.Pattern;

import s2017s40.kr.hs.mirim.mirimitshow.Classes.SignUpTaskClass;

public class SignupActivity extends AppCompatActivity {
    EditText name, email, pwd, pwdConfirm, phoneNum1, phoneNum2;
    Spinner phoneSpinner;
    Button signupBtn;
    String nameStr,emailStr,pwdStr,pwdConfirmStr,firstPhone,middlePhone,lastPhone;

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

        signupBtn = findViewById(R.id.signup_signUp_btn);



        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameStr = name.getText().toString();

                emailStr = email.getText().toString(); //이메일의 아이디 부분

                pwdStr = pwd.getText().toString();
                pwdConfirmStr = pwdConfirm.getText().toString();

                firstPhone = phoneSpinner.getSelectedItem().toString(); //첫 3자리
                middlePhone = phoneNum1.getText().toString(); // 중간 4자리
                lastPhone = phoneNum2.getText().toString(); // 마지막 4자리
                String Phone_num = firstPhone + " - " + middlePhone + " - " + lastPhone;

                //빈 칸이 있는지 검사
                if (nameStr.getBytes().length <= 0 || emailStr.getBytes().length <= 0 ||
                        pwdStr.getBytes().length <= 0 || pwdConfirmStr.getBytes().length <= 0 || firstPhone.getBytes().length <= 0 ||
                        middlePhone.getBytes().length <= 0 || lastPhone.getBytes().length <= 0) {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                //비밀번호가 확인비밀번호와 일치하는지 검사
                if(!pwdStr.equals(pwdConfirmStr)){
                    Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }

                //휴대폰 번호가 유효한지 검사

                if(!Pattern.matches("^01(?:0|1|[6-9]) - (?:\\d{3}|\\d{4}) - \\d{4}$", Phone_num))
                {
                    Toast.makeText(SignupActivity.this,"올바른 핸드폰 번호가 아닙니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(!(emailStr.contains("@") || emailStr.contains(".com"))){
                    Toast.makeText(SignupActivity.this,"올바른 이메일 형식이 아닙니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject obj = new JSONObject();
                SignUpTaskClass task = new SignUpTaskClass();

                try {
                    obj.put("name", nameStr);
                    obj.put("email", emailStr);
                    obj.put("phone", Phone_num);
                    obj.put("password", pwdStr); // 그룹 제외하고 다 보냄

                    String resultstr = task.execute(obj).get();

                    if(resultstr.equals("성공")){
                        Toast.makeText(SignupActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                    }else if(resultstr.equals("중복")){
                        Toast.makeText(SignupActivity.this, "이미 존재하는 이메일입니다", Toast.LENGTH_SHORT).show();
                        return;
                    }else{
                        Toast.makeText(SignupActivity.this, "회원가입 실패", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

            }
        });


    }
}
