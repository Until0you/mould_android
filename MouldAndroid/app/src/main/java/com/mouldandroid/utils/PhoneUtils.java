package com.mouldandroid.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2018/1/23.
 */

public class PhoneUtils {

    /**
     * 手机号正则
     * */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("^((1[0-9]))\\d{9}$");
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
