package s2017s40.kr.hs.mirim.mirimitshow;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferenceUtil {
    public static final String APP_SHARED_PREFS = "thisApp.SharedPreference";

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public SharedPreferenceUtil(Context context){
        this.sharedPreferences = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
        this.editor = sharedPreferences.edit();
    }

    public void setSharedTest(String email){
        editor.putString("email",email);
        editor.commit();
    }
    public String getSharedTest(){
        return sharedPreferences.getString("email","defValue");
    }
}
