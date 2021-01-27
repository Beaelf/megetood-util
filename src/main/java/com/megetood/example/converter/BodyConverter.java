package com.megetood.example.converter;

import com.megetood.example.model.User;
import com.megetood.util.call.Converter;

/**
 * BodyConverter
 *
 * @author Lei Chengdong
 * @date 2021/1/6
 */
public class BodyConverter implements Converter<User,User> {
    @Override
    public User convert(User source) {
        source.setName("change");
        return source;
    }
}
