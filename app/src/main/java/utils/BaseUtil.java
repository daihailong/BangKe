package utils;

import java.util.Date;

/**
 * Created by dhl on 2016/9/8.
 */
public class BaseUtil {
    /**
     * 验证手机号合法性
     *
     * @param mobilePhoneNumber 手机号
     * @return boolean
     */
    public static boolean checkMobilePhoneNumber(String mobilePhoneNumber) {
//        Pattern p = Pattern.compile("^[1][3,4,5,8][0-9]{9}$"); // 验证手机号
//        Matcher m = p.matcher(mobilePhoneNumber);
        int length = mobilePhoneNumber.length();
        if (length == 11)
            return true;
        else
            return false;
    }

}
