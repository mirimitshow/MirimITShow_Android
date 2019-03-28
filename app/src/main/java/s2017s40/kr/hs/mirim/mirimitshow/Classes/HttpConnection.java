package s2017s40.kr.hs.mirim.mirimitshow.Classes;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class HttpConnection {

    String urlStr = "http://10.96.123.207:9000"; // 김수현 ip
    URL url;
    public HttpURLConnection urlconn = null;
    OutputStreamWriter out;
    InputStreamReader reader;

    public HttpConnection(String url) {
        try {
            urlStr += url;
            this.url = new URL(url);
            urlconn = (HttpURLConnection) this.url.openConnection();
            urlconn.setRequestMethod("POST"); // URL 요청에 대한 메소드 설정 : POST.
            urlconn.setRequestProperty("Accept-Charset", "UTF-8"); // Accept-Charset 설정.
            urlconn.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");
            urlconn.setDoInput(true);
            urlconn.connect();
            out = new OutputStreamWriter(urlconn.getOutputStream(), "UTF-8");
            reader=  new InputStreamReader(urlconn.getInputStream(), "UTF-8");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
