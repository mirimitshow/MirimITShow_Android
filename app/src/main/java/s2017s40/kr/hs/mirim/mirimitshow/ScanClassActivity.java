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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;

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
    String email, token, category;
    Spinner categorySpinner;
    ArrayList<String> CategoryArrayList;
    ArrayAdapter<String> CategorArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_class);
        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);

        CategoryArrayList = new ArrayList<>();
        getCategory();
        CategorArrayAdapter = new ArrayAdapter<>(getApplicationContext(),
                android.R.layout.simple_spinner_dropdown_item, CategoryArrayList);

        categorySpinner = (Spinner)findViewById(R.id.scan_category_spinner);
        categorySpinner.setAdapter(CategorArrayAdapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                category = CategoryArrayList.get(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        init();
    }
    public void getCategory(){
        service = utils.mRetrofit.create(Services.class);
        Call<Register> call = service.getuser(email);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.code() == 200) {
                    Register user = response.body();
                    for(int i = 0; i < user.getCategory().size(); i++){
                        CategoryArrayList.add(user.getCategory().get(i).getName());
                        Toast.makeText(ScanClassActivity.this, "returns user", Toast.LENGTH_LONG).show();
                    }
                }else if(response.code() == 400){
                    Toast.makeText(ScanClassActivity.this, "nvalid input, object invalid", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                Toast.makeText(ScanClassActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
                Log.e("getuserError", t.toString());
            }
        });

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

                RequestBody emailBody = RequestBody.create(MediaType.parse("email"), email);
                RequestBody cartegoryBody = RequestBody.create(MediaType.parse("cartegory"), category);
                RequestBody nameBody = RequestBody.create(MediaType.parse("name"), "국어");
                MultipartBody.Part body = utils.CreateRequestBody(file,"url");
                
                Call<Scan> call = service.setscan(emailBody, cartegoryBody, nameBody,body);
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
                });
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
