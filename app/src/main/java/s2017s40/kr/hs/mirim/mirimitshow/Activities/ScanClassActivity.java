package s2017s40.kr.hs.mirim.mirimitshow.Activities;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import android.widget.Toast;

import com.scanlibrary.ScanActivity;
import com.scanlibrary.ScanConstants;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import s2017s40.kr.hs.mirim.mirimitshow.Category;
import s2017s40.kr.hs.mirim.mirimitshow.R;
import s2017s40.kr.hs.mirim.mirimitshow.Register;
import s2017s40.kr.hs.mirim.mirimitshow.Scan;
import s2017s40.kr.hs.mirim.mirimitshow.Services;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class ScanClassActivity extends AppCompatActivity{
    private static final int REQUEST_CODE = 99;
    private Button scanButton;
    private Button cameraButton;
    private Button mediaButton;
    private ImageView scannedImageView;
    private Services service;
    private EditText titleEdit;
    Utils utils = new Utils();
    Uri uri ;
    File file;
    Bitmap bitmap;
    SharedPreferences sharedPreference;
    String email, categoryString = "", titleString;
    Spinner categorySpinner;
    ArrayList<String> CategoryArrayList;
    ArrayAdapter<String> CategoryArrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_class);

        sharedPreference = getSharedPreferences("email", Activity.MODE_PRIVATE);
        email = sharedPreference.getString("email","defValue");
        service = utils.mRetrofit.create(Services.class);

        categorySpinner = findViewById(R.id.scan_category_spinner);
        titleEdit = findViewById(R.id.scan_title_edit);

        CategoryArrayList = new ArrayList<>();
        CategoryArrayList.add(0,"미선택시 제목으로 생성됨");

        init();
        getCategory();
    }
    public void getCategory(){
        service = utils.mRetrofit.create(Services.class);
        Call<Register> call = service.getuser(email);
        call.enqueue(new Callback<Register>() {
            @Override
            public void onResponse(Call<Register> call, Response<Register> response) {
                if (response.code() == 200) {
                    Register user = response.body();
                    try{
                        for(int i = 0; i < user.getCategory().size(); i++){
                            CategoryArrayList.add(user.getCategory().get(i).getName());
                            Log.e("categoryArrayList", user.getCategory().get(i).getName());
                            //Toast.makeText(ScanClassActivity.this, "returns user", Toast.LENGTH_LONG).show();
                            spinnerSet();
                        }
                    }catch (NullPointerException e){
                        Toast.makeText(ScanClassActivity.this, "category를 설정해주세요", Toast.LENGTH_LONG).show();
                    }
                }else if(response.code() == 400){
                   // Toast.makeText(ScanClassActivity.this, "nvalid input, object invalid", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<Register> call, Throwable t) {
                //Toast.makeText(ScanClassActivity.this, "정보받아오기 실패", Toast.LENGTH_LONG).show();
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
        public ScanButtonClickListener() { }
        @Override
        public void onClick(View v) {
            if(preference == 0){
                PutImage_server();
            }else{
                startScan(preference);
            }
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
            try {
                uri = data.getExtras().getParcelable(ScanConstants.SCANNED_RESULT);
                Log.e("uri", uri.getPath());
                bitmap = null;
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                getContentResolver().delete(uri, null, null);
                scannedImageView.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (NullPointerException e) {
                e.printStackTrace();
                Toast.makeText(ScanClassActivity.this, "스캔할 이미지를 선택해 주세요", Toast.LENGTH_LONG).show();
            }  catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private Bitmap convertByteArrayToBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    public void PutImage_server(){
        long mNow;
        Date mDate;
        SimpleDateFormat mFormat = new SimpleDateFormat("yyyyMMddhhmmss");
        mNow = System.currentTimeMillis();
        mDate = new Date(mNow);

        file =  new File(Environment.getExternalStorageDirectory()+"/Pictures",mFormat.format(mDate)+".jpeg");
        FileOutputStream fos = null;
        try{
            fos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            if (file.exists()) {

                titleString = titleEdit.getText().toString();
                if(categoryString.equals(CategoryArrayList.get(0))){
                    categoryString = titleString;
                }
                service = utils.mRetrofit.create(Services.class);
                //서버에 전송
                RequestBody emailBody = RequestBody.create(MediaType.parse("email"), email);
                RequestBody cartegoryBody = RequestBody.create(MediaType.parse("cartegory"), categoryString);
                RequestBody nameBody = RequestBody.create(MediaType.parse("name"), titleString);
                MultipartBody.Part body = utils.CreateRequestBody(file,"img");

                Call<Scan> call = service.setscan(emailBody, cartegoryBody, nameBody, body);
                call.enqueue(new Callback<Scan>() {
                    @Override
                    public void onResponse(Call<Scan> call, Response<Scan> response) {
                        if (response.code() == 200) {
                            Toast.makeText(ScanClassActivity.this, "new scan Ssuccessfully added", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(ScanClassActivity.this, MainActivity.class);
                        }else if (response.code() == 400) {
                          //  Toast.makeText(ScanClassActivity.this, "invalid input, object invalid", Toast.LENGTH_LONG).show();
                        }else if (response.code() == 409) {
                            Toast.makeText(ScanClassActivity.this, "duplicated scan", Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Scan> call, Throwable t) {
                        Log.e("setScanError",t.toString());
                    }
                });
            }
            fos.flush();
            fos.close();
            finish();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        OutputStream out = null;
    }

    public void spinnerSet(){
        Log.e("spinner","spinnerSet()");
        //categorySpinner.setSelection(0);
        CategoryArrayAdapter = new ArrayAdapter<>(ScanClassActivity.this, R.layout.support_simple_spinner_dropdown_item, CategoryArrayList);
        categorySpinner.setAdapter(CategoryArrayAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                categoryString = categorySpinner.getSelectedItem().toString();
               // Toast.makeText(getApplicationContext(), categorySpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                categoryString = titleString;
                Log.e("titleString","이걸로 들어감");
            }
        });
    }
}