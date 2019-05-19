package s2017s40.kr.hs.mirim.mirimitshow;

import okhttp3.JavaNetCookieJar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import java.io.File;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import retrofit2.converter.gson.GsonConverterFactory;
import s2017s40.kr.hs.mirim.mirimitshow.Utils;

public class Utils {
    public  static Retrofit mRetrofit;

    public static final int CONNECT_TIMEOUT = 100;
    public static final int WRITE_TIMEOUT = 100;
    public static final int READ_TIMEOUT = 100;
    String MULTIPART_FORM_DATA = "multipart/form-data";
    public Utils(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        //쿠키 메니저의 cookie policy를 변경 합니다.
        CookieManager cookieManager = new CookieManager();
        cookieManager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);

        OkHttpClient client = new OkHttpClient().newBuilder()
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS) //연결 타임아웃 시간 설정
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS) //쓰기 타임아웃 시간 설정
                .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS) //읽기 타임아웃 시간 설정
                .cookieJar(new JavaNetCookieJar(cookieManager)) //쿠키메니져 설정
                .addInterceptor(httpLoggingInterceptor) //http 로그 확인
                .retryOnConnectionFailure(true)
                .build();

        mRetrofit  = new Retrofit.Builder()
                .baseUrl("http://ec2-54-180-124-242.ap-northeast-2.compute.amazonaws.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public MultipartBody.Part createRequestBody(File file, String name){
         RequestBody mFile = RequestBody.create(MediaType.parse("images/*"), file);
        return MultipartBody.Part.createFormData(name, file.getName(), mFile);
    }

}
