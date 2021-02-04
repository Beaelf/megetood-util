package com.megetood.example;

import com.megetood.example.converter.BodyConverter;
import com.megetood.example.converter.ResultConverter;
import com.megetood.example.model.User;
import com.megetood.util.call.Call;
import com.megetood.util.call.Converter;
import com.megetood.util.call.ConverterType;
import com.megetood.util.call.Request;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

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
        originalApiCall();

        String res = testGet();
        System.out.println(res);

        String res2 = testPost();
        System.out.println(res2);
    }

    // original Api Call
    private static void originalApiCall() {
        String url = "http://localhost:8080/main/test";
        Object body = new User();
        MediaType contentType = MediaType.parseMediaType("text/xml; charset=utf-8");

        /*
            convert body data to xml
            参数转化、校验、数据准备等等
         */


        // call request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/json; charset=utf-8"));
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);

        String xmlRes = restTemplate.postForObject(url, httpEntity, String.class);

        // convert result data to User.class
        // 后续处理
    }

    private static String testPost() {
        Request postRequst = Request.builder()
                .url("http://localhost:8080/main/test")
                .post(new User("megetood测试", 1))
                .build();
        return Call.newCall(postRequst)
                .addConverter(ConverterType.BODY, new BodyConverter())
                .addConverter(ConverterType.RESULT, new ResultConverter())
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
