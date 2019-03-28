package s2017s40.kr.hs.mirim.mirimitshow.Classes;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SignInTaskClass extends AsyncTask <String,Void, String>{

    @Override
    protected String doInBackground(String... strings) {
        //email, pw 순으로 받음
        String resultstr = null;
        HttpURLConnection conn = null;
        OutputStreamWriter out;
        String urlStr = "http://10.96.123.207:9000/signin";
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

            obj.put("email", strings[0]);
            obj.put("password ", strings[1]);

            out.write(obj.toString());
            Log.e("테스트", "json 서버에 보냄");

            if(conn.getResponseCode() == 200){
                Log.e("테스트", "response code 받음");
                resultstr = "성공";
            }else{
                Log.e("테스트", "response code 받음");
                resultstr = "실패";
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


//        String resultstr = null;
//        HttpConnection conn = new HttpConnection("/signin");
//        JSONObject obj = new JSONObject();
//        try {
//
//            String str,
//                    serverMsg; // 서버에서 보낸 메시지 저장
//
//            obj.put("email", strings[0]);
//            obj.put("password", strings[1]);
//            conn.out.write(obj.toString());
//
//            if(conn.urlconn.getResponseCode() == conn.urlconn.HTTP_OK){
//
//                //서버 메시지 받아오는 코드
////                BufferedReader reader = new BufferedReader(conn.reader);
////                StringBuffer buffer = new StringBuffer();
////
////                if((str = reader.readLine()) != null){
////                    buffer.append(str);
////                }
////                serverMsg = buffer.toString();
//                resultstr = "로그인";
//
//            }else{
//                Log.e("에러  ", conn.urlconn.getResponseCode() + "");
//                resultstr = null;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return resultstr;
    }
}
