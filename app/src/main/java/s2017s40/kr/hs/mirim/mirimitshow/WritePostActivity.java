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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_post);
        Title = findViewById(R.id.PostTitle);
        Content = findViewById(R.id.PostContent);
        postingBtn = findViewById(R.id.postingBtn);
        GallaryBtn = findViewById(R.id.gallaryBtn);
        Notice = findViewById(R.id.isNotice);

        ArrayList<String> groupList = new ArrayList<String>();

        groupList.add("3-6"); // 그룹 목록 스피너의 값들
        groupList.add("3-5");

        GallaryBtn.setOnClickListener(new View.OnClickListener() { // 사진 가져오기 버튼 리스너
            @Override
            public void onClick(View v) {
                goToAlbum();
            }
        });

        postingBtn.setOnClickListener(new View.OnClickListener() { // 작성하기 버튼을 누를 때 이벤트
            @Override
            public void onClick(View v) {
                String isNotice =  String.valueOf(Notice.isChecked()); // 공지글 여부
                title_str = Title.getText().toString(); // 글 타이틀
                content_str = Content.getText().toString(); // 글 내용

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

            Uri photoUri = data.getData();
            Cursor cursor = null;

            try {

                String[] proj = { MediaStore.Images.Media.DATA };

                assert photoUri != null;
                cursor = getContentResolver().query(photoUri, proj, null, null, null);

                assert cursor != null;
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

                cursor.moveToFirst();

                tempFile = new File(cursor.getString(column_index));

            }catch(Exception e){
                e.getStackTrace();
            }
            finally {
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
        Bitmap originalBm = BitmapFactory.decodeFile(tempFile.getAbsolutePath(), options);
        imageView.setImageBitmap(originalBm);
        imageView.setVisibility(View.VISIBLE);

    }

    
}