package s2017s40.kr.hs.mirim.mirimitshow;

import android.Manifest;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;
import java.util.Random;

public class AddGroupActivity extends AppCompatActivity {
    ImageView iconImage, mainImage;
    EditText editGroupName;
    TextView textGroupCode;
    LinearLayout addImage;
    Button setTimeTableBtn, setGroupBtn, copyCodeBtn;
    String groupCodeStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_);

        groupCodeStr = getRandomString(7); //랜덤으로 Code 받기
        tedPermission();

        iconImage = findViewById(R.id.addGroup_plus_image);
        mainImage = findViewById(R.id.addGroup_mainImage_image);

        textGroupCode = findViewById(R.id.addGroup_groupCode_textView);
        editGroupName = findViewById(R.id.addGroup_groupName_editText);

        addImage = findViewById(R.id.addGroup_addImage_Linear);

        setTimeTableBtn = findViewById(R.id.addGrop_setTimetable_btn);
        setGroupBtn = findViewById(R.id.addGrop_setgroup_btn);
        copyCodeBtn = findViewById(R.id.addGrop_copyCode_btn);

        textGroupCode.setText(groupCodeStr);


        setTimeTableBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //시간표 열기
            }
        });


        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //이미지 추가
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

    //랜덤 문자열 발생
    private static String getRandomString(int length)
    {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        String chars[] =
    "a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,1, 2,3,4,5,6,7,8,9,0".split(",");

        for (int i=0 ; i<length ; i++)
        {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }

    private void tedPermission() { // 권한 요청 메소드

        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                // 권한 요청 성공

            }

            @Override
            public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                // 권한 요청 실패
            }
        };

        TedPermission.with(this)
                .setPermissionListener(permissionListener)
                .setRationaleMessage(getResources().getString(R.string.permission_2))
                .setDeniedMessage(getResources().getString(R.string.permission_1))
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

}
