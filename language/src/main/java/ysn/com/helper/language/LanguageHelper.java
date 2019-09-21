package ysn.com.helper.language;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.LocaleList;
import android.util.DisplayMetrics;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.util.Locale;

/**
 * @Author yangsanning
 * @ClassName LanguageHelper
 * @Description 语言切换的帮助类
 * @Date 2019/1/1
 * @History 2019/1/1 author: description:
 */
public class LanguageHelper {

    public static final String LANGUAGE_UPDATE = "ysn.com.helper.language.update";

    private static LanguageHelper instance;
    private Context context;
    public static final String KEY_LANGUAGE = "KEY_LANGUAGE";

    public static void inject(Context mContext) {
        if (instance == null) {
            synchronized (LanguageHelper.class) {
                if (instance == null) {
                    instance = new LanguageHelper(mContext);
                }
            }
        }
    }

    public static LanguageHelper get() {
        if (instance == null) {
            throw new IllegalStateException("You must be init LanguageHelper first");
        }
        return instance;
    }

    private LanguageHelper(Context context) {
        this.context = context;
    }

    /**
     * 设置语言
     */
    public void setConfiguration() {
        Locale targetLocale = getLanguageLocale();
        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(targetLocale);
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        //语言更换生效的代码
        resources.updateConfiguration(configuration, dm);
    }

    /**
     * 获取语言类型
     */
    private Locale getLanguageLocale() {
        int languageType = LanguagePreferences.get(context).getInt(LanguageHelper.KEY_LANGUAGE, -2020);

        if (languageType == -2020) {
            Locale locale;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                locale = LocaleList.getDefault().get(0);
            } else {
                locale = Locale.getDefault();
            }

            languageType = locale.getLanguage().startsWith("zh") ? LanguageType.LANGUAGE_CHINESE_SIMPLIFIED : LanguageType.LANGUAGE_EN;
        }

        if (languageType == LanguageType.LANGUAGE_EN) {
            return Locale.ENGLISH;
        }
        return Locale.SIMPLIFIED_CHINESE;
    }

    /**
     * 更新语言
     */
    public void updateLanguage(int languageType) {
        LanguagePreferences.get(context).putInt(LanguageHelper.KEY_LANGUAGE, languageType);
        LanguageHelper.get().setConfiguration();
        LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(LANGUAGE_UPDATE));
    }

    /**
     * 获取到用户保存的语言类型
     */
    public int getLanguageType() {
        return LanguagePreferences.get(context).getInt(LanguageHelper.KEY_LANGUAGE,
                LanguageType.LANGUAGE_CHINESE_SIMPLIFIED);
    }

    public static Context attachBaseContext(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return createConfigurationResources(context);
        } else {
            LanguageHelper.get().setConfiguration();
            return context;
        }
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context createConfigurationResources(Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        Locale locale = get().getLanguageLocale();
        configuration.setLocale(locale);
        return context.createConfigurationContext(configuration);
    }
}
