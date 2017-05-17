package com.appgather;

import android.util.Log;

import com.appgather.application.MyApplication;
import com.appgather.util.ReadAppConfigUtil;
import com.appgather.util.SharedPreferenceUtil;

import org.junit.Test;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {

       Log.d("xyz", ReadAppConfigUtil.readFile("file:///android_asset/kuaidi/config.json"));
    }
}