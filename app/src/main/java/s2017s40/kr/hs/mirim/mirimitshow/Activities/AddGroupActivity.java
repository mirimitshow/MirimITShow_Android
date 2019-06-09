package s2017s40.kr.hs.mirim.mirimitshow.Activities;

import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.Group;
import s2017s40.kr.hs.mirim.mirimitshow.JoinGroup;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class AddGroupActivity extends AppCompatActivity {
    ImageView iconImage, mainImage;
    s2017s40.kr.hs.mirim.mirimitshow.image image;
    EditText editGroupName;
    TextView textGroupCode;
    LinearLayout addImage;
    Button setTimeTableBtn, setGroupBtn, copyCodeBtn;
    String groupCodeStr, email;
    String pathGroupImage;
    private Services service;
    Utils utils = new Utils();
    private static final int PICK_FROM_ALBUM = 1;
    private File useFile;
    private File tempFile;
    private int id_view;
    SharedPreferences sharedPreference;
    LinearLayout addColor;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_);

        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");

        groupCodeStr = getRandomString(7); //랜덤으로 Code 받기

        textGroupCode = findViewById(R.id.addGroup_groupCode_textView);
        editGroupName = findViewById(R.id.addGroup_groupName_editText);

        addImage = findViewById(R.id.addGroup_addImage_Linear);

        setGroupBtn = findViewById(R.id.addGrop_setgroup_btn);
        copyCodeBtn = findViewById(R.id.addGrop_copyCode_btn);

        textGroupCode.setText(groupCodeStr);

        //복사하기 버튼
        copyCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager manager = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                ClipData clipData = ClipData.newPlainText("copyCode", textGroupCode.getText().toString());
                manager.setPrimaryClip(clipData);

                Toast.makeText(getApplicationContext(), "텍스트가 복사되었습니다", Toast.LENGTH_SHORT).show();
            }
        });

        //대표사진 추가하기
        addImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rnd = new Random();
                color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                addImage.setBackgroundColor(color);
                Log.e("color", String.valueOf(color));
            }
        });
        //그룹 생성 버튼
        setGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service = utils.mRetrofit.create(Services.class);
                addGroup();
            }
        });
    }

    //랜덤 문자열 발생
    @NonNull
    private static String getRandomString(int length) {
        StringBuffer buffer = new StringBuffer();
        Random random = new Random();
        String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0".split(",");
        for (int i=0 ; i<length ; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }

    private void goToAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();
            if(useFile != null) {
                if (useFile.exists()) {
                    if (useFile.delete()) {
                        Log.e("", useFile.getAbsolutePath() + " 삭제 성공");
                        useFile = null;
                    }
                }
            }
            return;
        }
        if (requestCode == PICK_FROM_ALBUM) {
            Uri photoUri = data.getData();
            Cursor cursor = null;
            try {
                String[] proj = {MediaStore.Images.Media.DATA};
                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);
                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                useFile = new File(cursor.getString(column_index));
                tempFile= new File("");
            } catch (Exception e) {
                e.getStackTrace();
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
            setImage();
        }
    }
    private void setImage() {
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        mainImage.setImageBitmap(originalBm);
        iconImage.setVisibility(View.INVISIBLE);
    }
    public void joinGroupMethod(){
        //내 그룹에 추가
        JoinGroup join = new JoinGroup(groupCodeStr, email);
        Call<JoinGroup> callmy = service.joingroup(join);
        callmy.enqueue(new Callback<JoinGroup>() {
            @Override
            public void onResponse(Call<JoinGroup> call, Response<JoinGroup> response) {
                if(response.code() == 200){
                    Toast.makeText(AddGroupActivity.this, "success joined group", Toast.LENGTH_SHORT).show();
                }else if(response.code() == 400) {
                    Toast.makeText(AddGroupActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<JoinGroup> call, Throwable t) {
                Toast.makeText(AddGroupActivity.this, "t", Toast.LENGTH_SHORT).show();
                Log.e("joingroupError",String.valueOf(t));
            }
        });
    }
    public void addGroup(){
        //그룹에 추가\
        Group group = new Group(groupCodeStr,  editGroupName.getText().toString(), email , color);
        Call<Group> call = service.setgroup(group);

        call.enqueue(new Callback<Group>() {
            @Override
            public void onResponse(Call<Group> call, Response<Group> response) {
                if(response.code() == 200){
                    Toast.makeText(AddGroupActivity.this, "new group added", Toast.LENGTH_SHORT).show();
                    //joinGroupMethod();
                    Intent intent = new Intent(AddGroupActivity.this, ScheduleActivity.class);
                    intent.putExtra("token",groupCodeStr);
                    startActivity(intent);
                }else if(response.code() == 400){
                    Toast.makeText(AddGroupActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                    finish();
                }else if(response.code() == 409){
                    Toast.makeText(AddGroupActivity.this, "group already exists", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            @Override
            public void onFailure(Call<Group> call, Throwable t) {
                Toast.makeText(AddGroupActivity.this, "t", Toast.LENGTH_SHORT).show();
                Log.e("setgroupError",String.valueOf(t));
            }
        });

    }
    public static RequestBody toRequestBody (String value) {
        RequestBody body = RequestBody.create(MediaType.parse("text/plain"), value);
        return body ;
    }
}

