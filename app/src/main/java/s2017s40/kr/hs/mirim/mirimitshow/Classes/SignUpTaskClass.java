package s2017s40.kr.hs.mirim.mirimitshow.Classes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignUpTaskClass extends AsyncTask<JSONObject, Void, String> {

    @Override
    protected String doInBackground(JSONObject... jsonObjects) {

        String resultstr = null;
        HttpURLConnection conn = null;
        OutputStreamWriter out;
        String urlStr = "http://10.96.123.207:9000/signup";
        JSONObject obj = new JSONObject();

        try {
            Log.e("테스트", "connect 전");
            URL url = new URL(urlStr);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            conn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            conn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");
            conn.setDoInput(true);
            conn.connect();
            Log.e("테스트", "connect 완료");

            out = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");

            out.write(jsonObjects[0].toString()); // 회원정보가 들어있는 json
            Log.e("테스트", "json 서버에 보냄");

            if(conn.getResponseCode() == 200){
                resultstr = "성공";
            }else if(conn.getResponseCode() == 409){
                resultstr = "중복";
            }else{
                resultstr = "실패";
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultstr;
    }
}
