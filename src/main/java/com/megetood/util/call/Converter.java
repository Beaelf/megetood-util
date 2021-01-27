package com.megetood.util.call;

/**
 * A converter converts a source object of type {@code S} to a target of type {@code T}.
 *
 * @author Lei Chengdong
 * @date 2021/1/4
 */
public interface Converter<S, T> {
    /**
     * Convert a source object of type {@code S} to a target of type {@code T}
     *
     * @param source
     * @return
     */
    T convert(S source);
}
