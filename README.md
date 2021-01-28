# Megetood Util

Provides common utility classes, including collection manipulation, object manipulation, enumeration caching, LRU caching, timers, Word document character template replacement, amount tools, API calling tools, and more.

## Overview

* **ListUtil**: conditional grouping, conditional de-duplication, conditional search (single, multiple), conditional search maximum, set illegal check
* **ObjectUtil**: remove all empty string of member variables, find member variables, map member variables to key-value, illegal validation
* **EnumUtil**: provide two ways to obtain enumeration values with cache and without cache, improve the efficiency of enumeration value search O(n)->O(1)
* **LRUCache**: bidirectional linked list and HashMap implementation, queued by recent use, cache reaches a threshold, the least used elements will be removed
* **DocxUtil**: replace the document, table template characters, the table can increase the line.
* **MoneyUtil**: Translate the figure amount into Chinese capitals.
* **API Call**: encapsulate API interface call process and optimize code structure.
* **TimeCounter**: Good timing tool

## Quick Start

### EnumUtil

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

* Add dependency

```java
        <dependency>
            <groupId>pl.jsolve</groupId>
            <artifactId>templ4docx</artifactId>
            <version>2.0.2</version>
        </dependency>
```

* Add your code

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

### MoneyUtil

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

### TimeCounter

Using lambda expressions, functional programming ideas

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

