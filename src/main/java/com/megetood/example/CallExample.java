package com.megetood.example;

import com.megetood.example.converter.BodyConverter;
import com.megetood.example.converter.ResultConverter;
import com.megetood.example.model.User;
import com.megetood.util.call.Call;
import com.megetood.util.call.ConverterType;
import com.megetood.util.call.Request;

import java.util.HashMap;
import java.util.Map;

/**
 * Call Example
 *
 * @author Lei Chengdong
 * @date 2021/1/4
 */
public class CallExample {
    public static void main(String[] args) {
        String res = testGet();
        System.out.println(res);

        String res2 = testPost();
        System.out.println(res2);
    }

    private static String testPost() {
        Request postRequst = Request.builder()
                .url("http://localhost:8080/main/test")
                .post(new User("megetood测试", 1))
                .build();
        return Call.newCall(postRequst)
                .addConverter(ConverterType.BODY,new BodyConverter())
                .addConverter(ConverterType.RESULT,new ResultConverter())
                .call(User.class);
    }

    private static String testGet() {
        Map<String, Object> params = new HashMap<>();
        params.put("name", "com/megetood");
        params.put("sex", 0);

        Request request = Request.builder()
                .url("http://localhost:8080/main/test")
                .get()
                .params(params)
                .build();
        return Call.newCall(request)
                .addConverter(ConverterType.RESULT, new ResultConverter())
                .call(User.class);
    }
}
