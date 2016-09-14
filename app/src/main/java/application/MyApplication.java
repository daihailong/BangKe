package application;

import android.app.Application;

import com.avos.avoscloud.AVOSCloud;
import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * Created by dhl on 2016/9/1.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, "QugUTj79NWJNrhjUeQw8RUL3-gzGzoHsz", "xGova4Q6G4m6UPQl3JvYFOFY");
        Fresco.initialize(this);
    }
}
