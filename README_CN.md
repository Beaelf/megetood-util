# Megetood Util

提供常用的工具类，包含集合操作、对象操作、枚举缓存、LRU缓存、计时器、Word文档字符模板替换、金额工具、API调用工具等等。

## Overview

* 集合工具类：按条件分组、按条件去重、按条件查找（单个、多个）、按条件查找最大值、集合非法校验
* 对象工具类：去除所有成员变量空字符串、查找成员变量、成员变量映射成key-value、非法校验
* 枚举缓存：提供带缓存的获取枚举值方式和不带缓存的获取枚举值方式两种方式，提高枚举值查找效率O(n)->O(1)
* LRU缓存：双向链表+HashMap实现，按最近使用进行排队，缓存达到阈值，会剔除最不常使用的元素
* Word工具类：替换文档、表格模板字符，表格可以自增行。
* 金额工具类：数字金额转中文大写。
* API调用工具类：封装Api接口调用流程，优化代码结构。
* 计时器：好用的计时工具

## Quick Start

### 枚举缓存

```java
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
		// 枚举类设计缓存
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

```

### Word工具类

* 添加依赖

```java
        <dependency>
            <groupId>pl.jsolve</groupId>
            <artifactId>templ4docx</artifactId>
            <version>2.0.2</version>
        </dependency>
```

* 编码

```java
package com.megetood.example;

import com.megetood.util.DocxUtil;
import com.megetood.util.FileUtil;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DocxUtil Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class DocxUtilExample {
    public static void main(String[] args) {
        // file path is "resource/docx/template.docx"
        String filePath = FileUtil.getSharedDataDir() + "docx\\template.docx";
        String outputPath = FileUtil.getSharedDataDir() + "docx\\template01.docx";

        HashMap<String, String> textMap = new HashMap<>();
        textMap.put("poNum", "poNum123");
        textMap.put("supplierName", "供应商123");
        textMap.put("amount", "10000");

        Map<String, String> rowMap = new HashMap<>();
        rowMap.put("num", "1");
        rowMap.put("itemCode", "wl123");
        rowMap.put("uomName", "元");
        rowMap.put("quantity", "20");
        Map<String, String> rowMap2 = new HashMap<>();
        rowMap2.put("num", "2");
        rowMap2.put("itemCode", "wl223");
        rowMap2.put("uomName", "元");
        rowMap2.put("quantity", "30");
        List<Map<String, String>> table = Arrays.asList(rowMap, rowMap2);

        DocxUtil.WordVariable wordVariable = new DocxUtil.WordVariable(textMap, Arrays.asList(table));

        DocxUtil.builder(filePath)
                .addPattern(new DocxUtil.Pattern("#{", "}"))
                .replaceTemplate(wordVariable)
                .toFilePath(outputPath);
    }
}

```

### 金额工具类

```java
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

```

### 计时器

使用lambda表达式，函数式编程思想

```java
package com.megetood.example;

import com.megetood.util.TimeCounter;

/**
 * TimeConter Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class TimeCounterExample {
    public static void main(String[] args) {
        exmaple1();
        exmaple2();
    }

    private static void exmaple2() {
        TimeCounter.count(startTime -> doSomething()).between().print();
    }

    private static void exmaple1() {
        TimeCounter timeCounter = new TimeCounter();
        timeCounter.start();

        // do something
        doSomething();

        timeCounter.end();
        timeCounter.between().print();
    }

    private static void doSomething() {
        for (int i = 0; i < 10000000; i++) {
            // do something
        }
    }
}

```

