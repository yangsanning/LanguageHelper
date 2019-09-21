package ysn.com.helper.language;


import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * @Author yangsanning
 * @ClassName LanguageHelper
 * @Description 语言保存类
 * @Date 2019/1/1
 * @History 2019/1/1 author: description:
 */
public class LanguagePreferences {

    private static final String LANGUAGE = "LANGUAGE";
    private static LanguagePreferences helper;
    private SharedPreferences sharedPreferences;

    private LanguagePreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(LANGUAGE, Context.MODE_PRIVATE);
    }

    public static LanguagePreferences get(Context context) {
        if (helper == null) {
            helper = new LanguagePreferences(context);
        }
        return helper;
    }

    public  void putInt(String key, int value) {
        Editor edit = sharedPreferences.edit();
        edit.putInt(key, value);
        edit.apply();
    }

    public  int getInt(String key, int defValue) {
        return sharedPreferences.getInt(key, defValue);
    }
}
