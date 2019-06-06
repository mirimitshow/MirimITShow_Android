package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddBoardActivity extends AppCompatActivity {
    private Services service;
    Utils utils = new Utils();
    SharedPreferences sharedPreference;
    String email, token;
    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;
    private File useFile;
    EditText Title, Content;
    Button postingBtn;
    ImageButton GallaryBtn;
    String title_str, content_str;
    Switch Notice;
    Spinner GroupSpinner;
    ArrayAdapter<String> adapter;

    image image;

    ArrayList<String> groupName;
    ArrayList<String> groupToken;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_board);
        Title = findViewById(R.id.PostTitle);
        Content = findViewById(R.id.PostContent);
        postingBtn = findViewById(R.id.postingBtn);
        GallaryBtn = findViewById(R.id.gallaryBtn);
        Notice = findViewById(R.id.isNotice);
        GroupSpinner =  findViewById(R.id.addBoard_spinner);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);
        //spinner 추가
        setList();



        GallaryBtn.setOnClickListener(new View.OnClickListener() { // 사진 가져오기 버튼 리스너
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });

        postingBtn.setOnClickListener(new View.OnClickListener() { // 작성하기 버튼을 누를 때 이벤트
            @Override
            public void onClick(View v) {
                token = GroupSpinner.getSelectedItem().toString();
                service = utils.mRetrofit.create(Services.class);
                title_str = Title.getText().toString(); // 글 타이틀
                content_str = Content.getText().toString(); // 글 내용
                addBorad();
            }
        });

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
                tempFile = new File("");
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
       ImageView imageView = findViewById(R.id.PostImageContent);
        BitmapFactory.Options options = new BitmapFactory.Options();
        Bitmap originalBm = BitmapFactory.decodeFile(useFile.getAbsolutePath(), options);
        imageView.setImageBitmap(originalBm);
        imageView.setVisibility(View.VISIBLE);
    }

    public void setList(){
        groupName = new ArrayList<String>();
        groupToken = new ArrayList<String>();

        Call<List<Group>> call = service.getusergroups(email);

        call.enqueue(new Callback<List<Group>>() {
            @Override
            public void onResponse(Call<List<Group>> call, Response<List<Group>> response) {
                if (response.code() == 200) {
                    List<Group> getGroupList = response.body();
                    for (Group singleGroup : getGroupList) {
                        groupName.add(singleGroup.getName());
                        groupToken.add(singleGroup.getToken());
                    }
                    Log.e("groupToeken", String.valueOf(groupToken));
                    Toast.makeText(AddBoardActivity.this, "returns user's Groups", Toast.LENGTH_SHORT).show();
                    spinnerSet();
                } else if (response.code() == 400) {
                    Toast.makeText(AddBoardActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                } else {
                }
            }
            @Override
            public void onFailure(Call<List<Group>> call, Throwable t) {
                Toast.makeText(AddBoardActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
                Log.e("getusergroupsError", t.toString());
            }
        });

        Log.e("method", "setList()");

    }
    public void spinnerSet(){

        Log.e("spinner","spinnerSet()");

        GroupSpinner.setSelection(0);
        adapter = new ArrayAdapter<>(AddBoardActivity.this, R.layout.support_simple_spinner_dropdown_item, groupName);
        GroupSpinner.setAdapter(adapter);

        GroupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("seleted", GroupSpinner.getSelectedItem().toString());
                Toast.makeText(getApplicationContext(), GroupSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void addBorad(){
        RequestBody group_tokenBody = RequestBody.create(MediaType.parse("group_token"), "YS0F2UR");
        RequestBody isNoticeBody = RequestBody.create(MediaType.parse("isNotice"), String.valueOf(Notice.isChecked()));
        RequestBody authorBody = RequestBody.create(MediaType.parse("author"),email );
        RequestBody titleBody = RequestBody.create(MediaType.parse("title"), title_str);
        RequestBody contentBody = RequestBody.create(MediaType.parse("content"), content_str);

        MultipartBody.Part body;
        if(!useFile.exists()){
            body = utils.CreateRequestBody( tempFile,"img");
        }else{
            body = utils.CreateRequestBody( useFile,"img");
        }
        //String group_token, Boolean isNotice, String author, String title,String content){
        Call<ResponseBody> call = service.setboard(group_tokenBody,isNoticeBody,authorBody,titleBody,contentBody, body);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.code() == 200) {
                    Toast.makeText(AddBoardActivity.this, "new board successfully added", Toast.LENGTH_SHORT).show();
                    addBoardInGroup();
                    finish();
                } else if (response.code() == 400) {
                    Toast.makeText(AddBoardActivity.this, "invalid input, object invalid", Toast.LENGTH_SHORT).show();
                } else if (response.code() == 409) {
                    Toast.makeText(AddBoardActivity.this, "duplicated board", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddBoardActivity.this, "onfailure", Toast.LENGTH_SHORT).show();
            }
        });//그룹에 추가
    }
    public void addBoardInGroup(){ }

}
