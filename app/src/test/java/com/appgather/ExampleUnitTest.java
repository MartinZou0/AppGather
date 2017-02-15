package com.appgather;

import com.alibaba.fastjson.JSON;
import com.appgather.entity.ResultData;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        ResultData resultData;
        resultData=JSON.parseObject("{\"status\":\"0\",\"info\":\"\\u7528\\u6237\\u540d\\u5fc5\\u987b\\u4e3a\\u624b\\u673a\\u53f7\"}",ResultData.class);
        System.out.println(resultData.getInfo());
    }
}