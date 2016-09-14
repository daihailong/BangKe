package utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by dhl on 2016/9/8.
 */
public class NetWorkUtils {
    /**
     * 检查当前网络是否可用
     * @param context 上下文
     * @return boolean
     */
    public static boolean isNetworkAvailable(Context context) {
        boolean isAvailable = false;
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null) {
            isAvailable = false;
        } else {
            // 获取NetworkInfo对象
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            int subType;
            String subTypeName;
            if (networkInfo != null) {
                // 判断当前网络状态是否为连接状态
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    //如果网络类型为WiFi的话就执行耗流量操作
                    if (isWifi(networkInfo)) {
                        ToastDialog.toast_short(context.getApplicationContext(), "现在用的是wifi");
                    }
                    if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                        subType = networkInfo.getSubtype();
                        subTypeName = networkInfo.getSubtypeName();
                        Log.e("subType", subType + "");
                        Log.e("subTypeName", subTypeName);
                        ToastDialog.toast_short(context.getApplicationContext(), "现在用的是移动数据");
                    }
                    isAvailable = true;
                }
            } else {
                ToastDialog.toast_short(context.getApplicationContext(), "网络连接错误，请检查你的网络设置！");
                isAvailable = false;
            }
        }
        return isAvailable;
    }

    /**
     * 是否是wifi
     * @param networkInfo
     * @return boolean
     */
    public static boolean isWifi(NetworkInfo networkInfo) {
        if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }



}
