/**
 * @param		
 */
package com.mouldandroid.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * @author toast 显示工具类
 *
 */
public class ToastUtil {
	private static Toast toast;
	
	/**
	 * 			显示Toast提示
	 * @param str
	 */
	public static void showToast(Context context, String str){
		if (null != toast) {
			toast.cancel();
		}
		toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * 			显示Toast提示
	 * @param resId
	 */
	public static void showToast(Context context, int resId){
		if (null != toast) {
			toast.cancel();
		}
		toast = Toast.makeText(context, resId, Toast.LENGTH_SHORT);
		toast.show();
	}
}
