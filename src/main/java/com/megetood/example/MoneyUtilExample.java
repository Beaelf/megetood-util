package com.megetood.example;

import com.megetood.util.MoneyUtil;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

/**
 * MoneyUtil Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class MoneyUtilExample {
    public static void main(String[] args) throws UnsupportedEncodingException {
        List<BigDecimal> list = Arrays.asList(
                BigDecimal.valueOf(140330701101.23),
                BigDecimal.valueOf(0.00),
                BigDecimal.valueOf(-1),
                BigDecimal.valueOf(0.23),
                BigDecimal.valueOf(1001.1)
        );

        for (BigDecimal value : list) {
            System.out.println("您输入的金额（小写）为：" + value);
            System.out.println("您输入的金额（大写）为：" + MoneyUtil.toCNWords(value));
        }
    }
}
