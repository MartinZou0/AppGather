package com.appgather;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.appgather.util.SharedPreferenceUtil;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        
        if(SharedPreferenceUtil.CommitDate("name","周庆华"))
        {
            Log.d("xyz","数据保存成功");
        }
        else{
            Log.d("xyz","数据保存失败");
        }

    }
}
