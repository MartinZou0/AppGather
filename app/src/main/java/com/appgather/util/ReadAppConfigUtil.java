package com.appgather.util;

import android.content.Context;
import android.util.Log;

import org.apache.http.util.EncodingUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 *
 * 读取配置文件的工具类
 */

public class ReadAppConfigUtil {

    private final static String filename="config.json";
    public static String readFile(String url) throws IOException {
        File file = new File(url+filename);
        String res;
        FileInputStream fis = new FileInputStream(file);
        int length = fis.available();
        byte [] buffer = new byte[length];
        fis.read(buffer);
        res = EncodingUtils.getString(buffer, "UTF-8");
        fis.close();
        return res;
    }

}
