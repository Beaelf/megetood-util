package com.megetood.util.call;

import java.util.HashMap;
import java.util.Map;

/**
 * Call Api Util
 *
 * @author Lei Chengdong
 * @date 2020/12/28
 */
public class Call {
    private Request request;
    private Map<ConverterType, Converter> converterMap = new HashMap<>();
    private Executor executor;

    private Call(Request request) {
        this.request = request;
    }

    public static Call newCall(Request request) {
        return new Call(request);
    }

    public <T, R> R call(Class<T> returnType) {
        if(executor==null){
            executor = new DefaultExecutor(this);
        }
        return executor.execute(returnType);
    }

    public Call addExecutor(Executor executor){
        this.executor = executor;
        return this;
    }

    public Call addConverter(ConverterType type, Converter converter) {
        if (converterMap.containsKey(type)) {
            return this;
        }

        converterMap.put(type, converter);

        return this;
    }

    public Converter getConverter(ConverterType type) {
        return converterMap.get(type);
    }

    public Request getRequest(){
        return this.request;
    }

}
