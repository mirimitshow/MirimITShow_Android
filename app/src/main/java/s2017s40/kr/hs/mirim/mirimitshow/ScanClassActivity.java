package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScanClassActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 99;
    private Button scanButton;
    private Button cameraButton;
    private Button mediaButton;
    private ImageView scannedImageView;
    private Services service;
    Utils utils = new Utils();
    SharedPreferences sharedPreference;
    String email, token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_class);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);

        init();
    }
    private void init() {

        scanButton = (Button) findViewById(R.id.scanButton);
        scanButton.setOnClickListener(new ScanButtonClickListener());
        cameraButton = (Button) findViewById(R.id.cameraButton);
        cameraButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_CAMERA));
        mediaButton = (Button) findViewById(R.id.mediaButton);
        mediaButton.setOnClickListener(new ScanButtonClickListener(ScanConstants.OPEN_MEDIA));
        scannedImageView = (ImageView) findViewById(R.id.scannedImage);
    }

    private class ScanButtonClickListener implements View.OnClickListener {

        private int preference;

        public ScanButtonClickListener(int preference) {
            this.preference = preference;
        }

        public ScanButtonClickListener() {
        }

        @Override
        public void onClick(View v) {
            startScan(preference);
        }
    }

    protected void startScan(int preference) {
        Intent intent = new Intent(ScanClassActivity.this, ScanActivity.class);
        intent.putExtra(ScanConstants.OPEN_INTENT_PREFERENCE, preference);


        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
            Log.e("uri", uri.getPath());
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);

                //파일DB연결 시 parent를 변경
                service = utils.mRetrofit.create(Services.class);
                //서버에 전송
                File file = new File(uri.getPath());
                RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
                RequestBody emailBody = RequestBody.create(MediaType.parse("email"), email);
                RequestBody cartegoryBody = RequestBody.create(MediaType.parse("cartegory"), "국어");
                RequestBody nameBody = RequestBody.create(MediaType.parse("name"), "국어");
                MultipartBody.Part body = MultipartBody.Part.createFormData("url", email  + "jpeg", requestFile);

               /* Call<Scan> call = service.setscan(emailBody, cartegoryBody, nameBody, body);
                call.enqueue(new Callback<Scan>() {
                    @Override
                    public void onResponse(Call<Scan> call, Response<Scan> response) {
                        if (response.code() == 200) {
                            Toast.makeText(ScanClassActivity.this, "new scan Ssuccessfully added", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ScanClassActivity.this, MainActivity.class);
                        }else if (response.code() == 400) {
                            Toast.makeText(ScanClassActivity.this, "invalid input, object invalid", Toast.LENGTH_LONG).show();
                        }else if (response.code() == 409) {
                            Toast.makeText(ScanClassActivity.this, "duplicated scan", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Scan> call, Throwable t) {
                        Log.e("setScanError",t.toString());
                    }
                });*/
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Bitmap convertByteArrayToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }
}