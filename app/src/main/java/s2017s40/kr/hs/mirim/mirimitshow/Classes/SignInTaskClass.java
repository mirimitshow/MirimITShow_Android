package s2017s40.kr.hs.mirim.mirimitshow.Classes;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;

class SignInTaskClass extends AsyncTask <String,Void, String>{

    @Override
    protected String doInBackground(String... strings) {
        //email, pw 순으로 받음
        String resultstr = null;
        HttpConnection conn = new HttpConnection("/signin");
        JSONObject obj = new JSONObject();
        try {

            String str,
                    serverMsg; // 서버에서 보낸 메시지 저장
                    
            obj.put("email", strings[0]);
            obj.put("pw", strings[1]);
            conn.out.write(obj.toString());

            if(conn.urlconn.getResponseCode() == conn.urlconn.HTTP_OK){
//                BufferedReader reader = new BufferedReader(conn.reader);
//                StringBuffer buffer = new StringBuffer();
//
//                if((str = reader.readLine()) != null){
//                    buffer.append(str);
//                }
//                serverMsg = buffer.toString();
                resultstr = "로그인";


            }else{
                Log.e("에러  ", conn.urlconn.getResponseCode() + "");
                resultstr = "로그인 실패";
            }

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resultstr;

    }
}
