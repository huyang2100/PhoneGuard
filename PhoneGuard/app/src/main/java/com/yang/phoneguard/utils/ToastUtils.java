package com.yang.phoneguard.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast 相关工具类
 *
 * 作者：Yang on 2016/12/2 16:16
 * 邮箱：huyang2100@163.com
 */
public class ToastUtils {
    private static String oldMsg;
    protected static Toast toast = null;
    private static long oneTime = 0;
    private static long twoTime = 0;

    /**
     * 显示一次toast
     *
     * @author Yang
     * @time 2016/12/2 16:00
     */
    public static void showToast(Context context, String s) {
        if (toast == null) {
            toast = Toast.makeText(context, s, Toast.LENGTH_SHORT);
            toast.show();
            oneTime = System.currentTimeMillis();
        } else {
            twoTime = System.currentTimeMillis();
            if (s.equals(oldMsg)) {
                if (twoTime - oneTime > Toast.LENGTH_SHORT) {
                    toast.show();
                }
            } else {
                oldMsg = s;
                toast.setText(s);
                toast.show();
            }
        }
        oneTime = twoTime;
    }

    public static void showToast(Context context, int resId) {
        showToast(context, context.getString(resId));
    }
}
