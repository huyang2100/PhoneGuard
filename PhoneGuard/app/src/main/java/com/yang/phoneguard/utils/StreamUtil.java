package com.yang.phoneguard.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 与流相关的工具类
 *
 * 作者：Yang on 2016/12/1 22:48
 * 邮箱：huyang2100@163.com
 */

public class StreamUtil {
    /**
     * 将输入流转换为字符串
     *
     * @param is 输入流
     * @return 字符串
     */
    public static String readStream(InputStream is) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = -1;
        try {
            while ((len = is.read(buffer)) != -1) {
                baos.write(buffer, 0, len);
            }
            is.close();
            return new String(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
