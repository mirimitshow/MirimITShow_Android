package s2017s40.kr.hs.mirim.mirimitshow;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScheduleActivity extends AppCompatActivity {
    Button okBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        okBtn = findViewById(R.id.schedule_ok_btn);

        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View rootView = getWindow().getDecorView();
                File screenShot = ScreenShot(rootView);
                if(screenShot!=null){
                    //갤러리에 추가
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(screenShot)));
                    Toast.makeText(getApplicationContext(), "시간표 설정이 완료 되었습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    //화면 캡쳐하기
    public File ScreenShot(View view){
        view.setDrawingCacheEnabled(true);  //화면에 뿌릴때 캐시를 사용하게 한다

        Bitmap screenBitmap = view.getDrawingCache();   //캐시를 비트맵으로 변환

        String filename = "screenshot.png";//그룹이름으로 변경해 주세용
        File file = new File(Environment.getExternalStorageDirectory()+"/Pictures", filename);  //Pictures폴더 screenshot.png 파일
        FileOutputStream os = null;
        try{
            os = new FileOutputStream(file);
            screenBitmap.compress(Bitmap.CompressFormat.PNG, 90, os);   //비트맵을 PNG파일로 변환
            os.close();

        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
        view.setDrawingCacheEnabled(false);
        return file;
    }
}
