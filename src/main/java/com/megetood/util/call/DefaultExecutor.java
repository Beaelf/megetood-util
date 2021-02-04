package com.megetood.util.call;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;

/**
 * Default implementation of Executor
 *
 * @author Lei Chengdong
 * @date 2021/1/4
 */
public class DefaultExecutor implements Executor {
    private Call call;

    public DefaultExecutor(Call call) {
        this.call = call;
    }

    @Override
    public <T, R> R execute(Class<T> returnType) {
        Request request = call.getRequest();
        RequestMethod method = request.getMethod();
        String url = request.getUrl();
        Object body = request.getBody();
        MediaType contentType = request.getContentType();

        // convert body data
        if (RequestMethod.POST.equals(method)) {
            Converter converter = call.getConverter(ConverterType.BODY);
            if(converter!=null){
                body = converter.convert(body);
            }
        }

        // call request
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(contentType);
        HttpEntity httpEntity = new HttpEntity(body, httpHeaders);

        T res = null;
        switch (method) {
            case GET:
                res = restTemplate.getForObject(url, returnType);
                break;
            case POST:
                res = restTemplate.postForObject(url, httpEntity, returnType);
                break;
            default:
                break;
        }

        // convert result
        Converter<T, R> converter = call.getConverter(ConverterType.RESULT);
        if (converter != null) {
            return converter.convert(res);
        }

        return (R) res;
    }
}
