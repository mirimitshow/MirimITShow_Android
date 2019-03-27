package s2017s40.kr.hs.mirim.mirimitshow;

import android.graphics.Bitmap;
import android.os.Environment;
import android.view.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CaptureUtil {
    // 캡쳐가 저장될 외부 저장소
    private static final String CAPTURE_PATH = "/CAPTURE_TEST";
    /**
     * 특정 뷰만 캡쳐
     * @param View
     */
    public static void captureView(View View) {
        View.buildDrawingCache();
        Bitmap captureView = View.getDrawingCache();
        FileOutputStream fos;

        String strFolderPath = Environment.getExternalStorageDirectory().getAbsolutePath() + CAPTURE_PATH;
        File folder = new File(strFolderPath);
        if(!folder.exists()) {
            folder.mkdirs();
        }

        String strFilePath = strFolderPath + "/" + System.currentTimeMillis() + ".png";
        File fileCacheItem = new File(strFilePath);

        try {
            fos = new FileOutputStream(fileCacheItem);
            captureView.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
