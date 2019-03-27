package s2017s40.kr.hs.mirim.mirimitshow;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class AddGroupActivity extends AppCompatActivity {
    ImageView iconImage, mainImage;
    EditText editGroupName;
    TextView textGroupCode;
    LinearLayout addImage;
    Button setTimeTableBtn, setGroupBtn, copyCodeBtn;
    String groupCodeStr;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;

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
    "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,1,2,3,4,5,6,7,8,9,0".split(",");

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

    private void goToAlbum() { //앨범으로 이동

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) { //엑티비티 리절

        if (resultCode != Activity.RESULT_OK) {

            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("TAG", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            Uri photoUri = data.getData();

            Cursor cursor = null;

            try {

                /*
                 *  Uri 스키마를
                 *  content:/// 에서 file:/// 로  변경한다.
                 */
                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }

            setImage();

        }
    }

    private void setImage() { //view에 이미지 표시

        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);

        mainImage.setImageBitmap(originalBm);
        iconImage.setVisibility(View.GONE);

    }



}
