package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class WritePostActivity extends AppCompatActivity {

    private static final int PICK_FROM_ALBUM = 1;
    private File tempFile;

    EditText Title, Content;
    Button postingBtn;
    ImageButton GallaryBtn;
    String title_str, content_str;
    Switch Notice;
    Spinner groups;
    String PostingGroup;
    ArrayList<String> groupList;
    Uri photoUri;
    TextView writingTitle, isnotice_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        Title = findViewById(R.id.PostTitle);
        Content = findViewById(R.id.PostContent);
        postingBtn = findViewById(R.id.postingBtn);
        GallaryBtn = findViewById(R.id.gallaryBtn);
        Notice = findViewById(R.id.isNotice);
        groups = findViewById(R.id.WritePost_groupLists);
        writingTitle  = findViewById(R.id.WritingTitle);
        isnotice_txt = findViewById(R.id.isnotice_txt);

        isnotice_txt.setText("공지여부");

        groupList = new ArrayList<String>();


        groupList.add("3-6"); // 그룹 목록 스피너의 값들
        groupList.add("3-5");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, groupList);
        groups.setAdapter(adapter);


        groups.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PostingGroup = groupList.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                PostingGroup="";
            }
        });



        GallaryBtn.setOnClickListener(new View.OnClickListener() { // 사진 가져오기 버튼 리스너
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });

        postingBtn.setOnClickListener(new View.OnClickListener() { // 작성하기 버튼을 누를 때 이벤트
            @Override
            public void onClick(View v) {
                if(PostingGroup.equals("")){
                    Toast.makeText(getApplicationContext(), "글을 작성할 그룹을 선택해주세요", Toast.LENGTH_SHORT);
                    return;
                }

                String isNotice =  String.valueOf(Notice.isChecked()); // 공지글 여부
                title_str = Title.getText().toString(); // 글 타이틀
                content_str = Content.getText().toString(); // 글 내용

            }
        });


    }

    private void goToAlbum() {

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);

        startActivityForResult(intent, PICK_FROM_ALBUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode != Activity.RESULT_OK) {
            Toast.makeText(this, "취소 되었습니다.", Toast.LENGTH_SHORT).show();

            if(tempFile != null) {
                if (tempFile.exists()) {
                    if (tempFile.delete()) {
                        Log.e("", tempFile.getAbsolutePath() + " 삭제 성공");
                        tempFile = null;
                    }
                }
            }

            return;
        }

        if (requestCode == PICK_FROM_ALBUM) {

            photoUri = data.getData();
            Bitmap image_bitmap;

            try {
                image_bitmap 	= MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                setImage(image_bitmap);

            }catch(Exception e){
                e.getStackTrace();
            }
            finally {
            }

        }
    }

    private void setImage(Bitmap img) {

       ImageView imageView = findViewById(R.id.PostImageContent);

        imageView.setImageBitmap(img);
        imageView.setVisibility(View.VISIBLE);

    }


    
}
