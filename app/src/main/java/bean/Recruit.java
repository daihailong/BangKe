package bean;

import android.os.Parcel;

import com.avos.avoscloud.AVClassName;
import com.avos.avoscloud.AVObject;

/**
 * Created by dhl on 2016/9/6.
 */
@AVClassName("Recruit")
public class Recruit extends AVObject {





    //此处为我们的默认实现，当然你也可以自行实现
    public static final Creator CREATOR = AVObjectCreator.instance;
}
