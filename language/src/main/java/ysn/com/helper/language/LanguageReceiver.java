package ysn.com.helper.language;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * @Author yangsanning
 * @ClassName LanguageReceiver
 * @Description 语言广播
 * @Date 2019/8/26
 * @History 2019/8/26 author: description:
 */
public class LanguageReceiver extends BroadcastReceiver {

    private OnLanguageUpdateListener onLanguageUpdateListener;

    public LanguageReceiver(OnLanguageUpdateListener onLanguageUpdateListener) {
        this.onLanguageUpdateListener = onLanguageUpdateListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if (onLanguageUpdateListener != null) {
            onLanguageUpdateListener.onLanguageUpdate();
        }
    }

    public interface OnLanguageUpdateListener {

        void onLanguageUpdate();
    }
}
