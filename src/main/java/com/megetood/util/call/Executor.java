package com.megetood.util.call;

/**
 * call executor
 *
 * @author Lei Chengdong
 * @date 2021/1/4
 */
public interface Executor {
    <T,R> R execute(Class<T> returnType);
}
