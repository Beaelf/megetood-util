package com.megetood.example;

import com.megetood.example.enums.CoinEnum;
import com.megetood.util.EnumUtil;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * EnumUtil Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class EnumUtilExample {
    public static void main(String[] args) {
        int key = 5;

        CoinEnum targetEnum = CoinEnum.NICKEL;

        CoinEnum anEnum = CoinEnum.getEnum(key);
        Assert.isTrue(targetEnum.equals(anEnum),"不相等");

        // 使用缓存
        Optional<CoinEnum> enumWithCache = EnumUtil.getEnumWithCache(CoinEnum.class, CoinEnum::getValue, key);
        Assert.isTrue(enumWithCache.isPresent(),"不存在");
        Assert.isTrue(targetEnum.equals( enumWithCache.get()),"不相等");

        // 不使用缓存（遍历）
        Optional<CoinEnum> enumResult = EnumUtil.getEnum(CoinEnum.class, CoinEnum::getValue, key);
        Assert.isTrue(enumResult.isPresent(),"不存在");
        Assert.isTrue(targetEnum.equals(enumResult.get()),"不相等");
    }
}
