package com.appgather;

import android.util.Log;

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

        if(SharedPreferenceUtil.CommitDate("name","周庆华"))
        {
            Log.d("xyz","数据保存成功");
        }
        else{
            Log.d("xyz","数据保存失败");
        }

    }
}