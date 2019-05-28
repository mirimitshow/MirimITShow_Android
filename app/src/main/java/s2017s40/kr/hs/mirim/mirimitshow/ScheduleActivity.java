package s2017s40.kr.hs.mirim.mirimitshow;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScheduleActivity extends AppCompatActivity {
    Button okBtn, skip_btn;
    String token;
    SharedPreferences sharedPreference;

    private Services service;
    Utils utils = new Utils();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);
        service = utils.mRetrofit.create(Services.class);
        Intent intent = getIntent();
        token = intent.getStringExtra("token");
        skip_btn = findViewById(R.id.schedule_skip_btn);
        okBtn = findViewById(R.id.schedule_ok_btn);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            checkVerify();
        }

        skip_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final TableLayout capture = (TableLayout) findViewById(R.id.table_schedule);//캡쳐할영역(테이블 레이아웃)
                //임시로 파일이름 날짜 설정 -> 그룹 코드로 변경해서 불러오기!

                capture.buildDrawingCache();
                Bitmap captureview = capture.getDrawingCache();

                //파일DB연결 시 parent를 변경

                File file = new File(Environment.getExternalStorageDirectory()+"/Pictures",token+".jpeg");

                FileOutputStream fos = null;
                try{
                    fos = new FileOutputStream(file);
                    captureview.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    //여기 sendBroadcast DB  변경
                    //sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));

                    if (file.exists()) {
                        //서버에 전송
                        RequestBody description = RequestBody.create(MediaType.parse("token"), token);
                        MultipartBody.Part body = utils.CreateRequestBody(file,"img");

                        Call<ResponseBody> call = service.settimetable(description, body);

                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if(response.code() == 200){
                                    Toast.makeText(ScheduleActivity.this,"returns existing Group", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(ScheduleActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else if(response.code() == 400){
                                    Toast.makeText(ScheduleActivity.this,"invalid input, object invalid", Toast.LENGTH_LONG).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                    fos.flush();
                    fos.close();
                    capture.destroyDrawingCache();
                    finish();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void checkVerify()
    {
        if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                        checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // ...
            }
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
        else {
           return;
        }
    }
}
