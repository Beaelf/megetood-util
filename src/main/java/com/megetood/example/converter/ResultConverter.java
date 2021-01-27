package com.megetood.example.converter;

import com.megetood.example.model.User;
import com.megetood.util.call.Converter;

/**
 * todo
 *
 * @author Lei Chengdong
 * @date 2021/1/6
 */
public class ResultConverter implements Converter<User,String> {
    @Override
    public String convert(User source) {
        return source.toString()+":嘻嘻嘻";
    }
}
