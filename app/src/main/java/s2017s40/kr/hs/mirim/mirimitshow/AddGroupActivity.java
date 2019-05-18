package s2017s40.kr.hs.mirim.mirimitshow;

import android.Manifest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddGroupActivity extends AppCompatActivity {
    ImageView iconImage, mainImage;
    EditText editGroupName;
    TextView textGroupCode;
    LinearLayout addImage;
    Button setTimeTableBtn, setGroupBtn, copyCodeBtn;
    String groupCodeStr, email;
    private Services service;
    Utils utils = new Utils();
    private static final int PICK_FROM_CAMERA = 0;
    private static final int PICK_FROM_ALBUM = 1;
    private static final int CROP_FROM_iMAGE = 2;
    private Uri imageCaptureUri;
    private String absoultePath;
    private int id_view;
    SharedPreferences sharedPreference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_group_);



        groupCodeStr = getRandomString(7); //랜덤으로 Code 받기

        iconImage = findViewById(R.id.addGroup_plus_image);
        mainImage = findViewById(R.id.addGroup_mainImage_image);

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
                DialogInterface.OnClickListener cameraListener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakePhotoAction();
                    }
                };
                DialogInterface.OnClickListener albumListener  = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        doTakeAlbumAction();
                    }
                };
                DialogInterface.OnClickListener cancelListener  = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                };
                new AlertDialog.Builder(AddGroupActivity.this)
                        .setTitle("업로드할 이미지 선택")
                        .setNeutralButton("취소", cancelListener)
                        .setPositiveButton("사진촬영", cameraListener)
                        .setNegativeButton("앨범선택", albumListener)
                        .show();
            }
        });
        //그룹 생성 버튼
        setGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                service = utils.mRetrofit.create(Services.class);
                sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
                String email = sharedPreference.getString("email","defValue");
                //내 그룹에 추가

                JoinGroup join = new JoinGroup(groupCodeStr, email);
                Call<JoinGroup> callmy = service.joingroup(join);
                callmy.enqueue(new Callback<JoinGroup>() {
                    @Override
                    public void onResponse(Call<JoinGroup> call, Response<JoinGroup> response) {
                        if(response.code() == 200){
                            Toast.makeText(AddGroupActivity.this, "new group added", Toast.LENGTH_SHORT).show();
                        }else if(response.code() == 400){
                            Toast.makeText(AddGroupActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                            finish();
                        }else if(response.code() == 409){
                            Toast.makeText(AddGroupActivity.this, "group already exists", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                    @Override
                    public void onFailure(Call<JoinGroup> call, Throwable t) {
                        Toast.makeText(AddGroupActivity.this, "t", Toast.LENGTH_SHORT).show();
                        Log.e("dddddddd",String.valueOf(t));
                    }
                });

                //그룹에 추가
                Group group = new Group(groupCodeStr,  editGroupName.getText().toString(), email);
                Call<Group> call = service.setgroup(group);
                call.enqueue(new Callback<Group>() {
                    @Override
                    public void onResponse(Call<Group> call, Response<Group> response) {
                        if(response.code() == 200){
                            Toast.makeText(AddGroupActivity.this, "new group added", Toast.LENGTH_SHORT).show();
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
                        Log.e("dddddddd",String.valueOf(t));
                    }
                });
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

    //카메라에서 이미지 가져오기
    public void doTakePhotoAction() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 임시로 사용할 파일의 경로를 생성
        String url = "tmp_" + String.valueOf(System.currentTimeMillis()) + ".jpg";
        imageCaptureUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), url));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCaptureUri);
        startActivityForResult(intent, PICK_FROM_CAMERA);
    }
    // 앨범에서 이미지 가져오기
    public void doTakeAlbumAction() {
        // 앨범 호출
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType(android.provider.MediaStore.Images.Media.CONTENT_TYPE);
        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if(resultCode != RESULT_OK)
            return;
        switch(requestCode) {
            case PICK_FROM_ALBUM: {
                imageCaptureUri = data.getData();
                Log.e("SmartWheel",imageCaptureUri.getPath());
            }
            case PICK_FROM_CAMERA: {
                // 이미지를 가져온 이후의 리사이즈할 이미지 크기를 결정합니다.
                // 이후에 이미지 크롭 어플리케이션을 호출하게 됩니다.
                Intent intent = new Intent("com.android.camera.action.CROP");
                intent.setDataAndType(imageCaptureUri, "image/*");
                // CROP할 이미지를 200*200 크기로 저장
                intent.putExtra("outputX", 185); // CROP한 이미지의 x축 크기
                intent.putExtra("outputY", 370); // CROP한 이미지의 y축 크기
                intent.putExtra("aspectX", 1); // CROP 박스의 X축 비율
                intent.putExtra("aspectY", 2); // CROP 박스의 Y축 비율
                intent.putExtra("scale", true);
                intent.putExtra("return-data", true);
                startActivityForResult(intent, CROP_FROM_iMAGE); // CROP_FROM_CAMERA case문 이동
                break;
            }
            case CROP_FROM_iMAGE: {
                // 크롭이 된 이후의 이미지를 넘겨 받습니다.
                // 이미지뷰에 이미지를 보여준다거나 부가적인 작업 이후에
                // 임시 파일을 삭제합니다.
                if(resultCode != RESULT_OK) {
                    return;
                }
                final Bundle extras = data.getExtras();
                // CROP된 이미지를 저장하기 위한 FILE 경로
                String filePath = Environment.getExternalStorageDirectory().getAbsolutePath()+ "/SmartWheel/"+System.currentTimeMillis()+".jpg";
                Log.e("filePath", filePath);
                if(extras != null) {
                    Bitmap photo = extras.getParcelable("data"); // CROP된 BITMAP
                    mainImage.setImageBitmap(photo); // 레이아웃의 이미지칸에 CROP된 BITMAP을 보여줌
                    storeCropImage(photo, filePath); // CROP된 이미지를 외부저장소, 앨범에 저장한다.
                    absoultePath = filePath;
                    break;
                }
                // 임시 파일 삭제
                File f = new File(imageCaptureUri.getPath());
                if(f.exists()) {
                    f.delete();
                }
            }
        }
    }

    private void storeCropImage(Bitmap bitmap, String filePath) {
        // SmartWheel 폴더를 생성하여 이미지를 저장하는 방식이다.
        String dirPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SmartWheel";
        File directory_SmartWheel = new File(dirPath);
        if(!directory_SmartWheel.exists()) // SmartWheel 디렉터리에 폴더가 없다면 (새로 이미지를 저장할 경우에 속한다.)
            directory_SmartWheel.mkdir();
        File copyFile = new File(filePath);
        BufferedOutputStream out = null;
        try {
            copyFile.createNewFile();
            out = new BufferedOutputStream(new FileOutputStream(copyFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            // sendBroadcast를 통해 Crop된 사진을 앨범에 보이도록 갱신한다.
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(copyFile)));
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

