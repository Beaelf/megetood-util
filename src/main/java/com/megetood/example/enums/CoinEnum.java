package com.megetood.example.enums;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * 硬币枚举
 *
 * @author Lei Chengdong
 * @date 2020/12/6
 */
@Getter
public enum CoinEnum {
    PENNY(1), NICKEL(5), DIME(10), QUARTER(25),NEWONE(50);

    CoinEnum(int value) {
        this.value = value;
    }

    private final int value;

    public int value() {
        return value;
    }

    /**
     * 优化获取枚举对象的时间复杂度：O(n) -> O(1)
     */
    private static final Map<Integer, CoinEnum> cache = new HashMap<>();

    static {
        for (CoinEnum coinEnum : CoinEnum.values()) {
            cache.put(coinEnum.getValue(), coinEnum);
        }
    }

    public static CoinEnum getEnum(int value) {
        return cache.getOrDefault(value, null);
    }
}
