package s2017s40.kr.hs.mirim.mirimitshow.ServerConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.JsonReader;

import org.json.JSONObject;

import okhttp3.OkHttpClient;

public class ConnectClass extends AsyncTask<JSONObject, Void, Integer> {

    String url =
            "http://ec2-52-78-186-63.ap-northeast-2.compute.amazonaws.com/?fbclid=IwAR1tYWUHbhufvTaIpZqldyR4MTYk9XNw5mkxexFPTcTDgXxCCeqaKRvIq_k";
    String type;
    Activity activity;

    public ConnectClass(Activity activity, String type){
        this.activity = activity; // task 를 실행하는 액티비티
        this.type = type; // 요청할 작업(ex : /signin)
    }

    @Override
    protected Integer doInBackground(JSONObject... voids) {
        OkHttpClient client = new OkHttpClient();
        return null;
    }
    @Override
    protected void onProgressUpdate (Void... voids){}

    @Override
    protected void onPostExecute (Integer sign){}

}
