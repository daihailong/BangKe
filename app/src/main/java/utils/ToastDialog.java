package utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by dhl on 2016/9/3.
 */
public class ToastDialog {
    public static void toast_long(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
    }

    public static void toast_short(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }
}
