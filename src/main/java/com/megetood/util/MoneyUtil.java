package com.megetood.util;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Money Util
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class MoneyUtil {

    private static final String UNIT[] = {"万", "千", "佰", "拾", "亿", "千", "佰", "拾", "万", "千", "佰", "拾", "元", "角", "分"};

    private static final String NUM[] = {"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};

    private static final BigDecimal MAX_VALUE = BigDecimal.valueOf(9999999999999.99D);

    public static final String ZERO_YUAN = "零元整";

    private MoneyUtil() {
    }

    /**
     * Converts number to Chinese words
     *
     * @param money
     * @return result
     */
    public static String toCNWords(BigDecimal money) {
        if (Objects.isNull(money)) {
            return null;
        }

        if (money.compareTo(BigDecimal.ZERO) < 0 || money.compareTo(MAX_VALUE) > 0) {
            throw new IllegalArgumentException(money.toString() + " is <0 or >" + MAX_VALUE.toString());
        }

        money = money.movePointRight(2).setScale(0, RoundingMode.HALF_UP);// 四舍五入到分

        if (money.equals(0)) {
            return ZERO_YUAN;
        }

        String strMoney = money.toString();

        // numIndex用于选择金额数值
        int numIndex = 0;
        // unitIndex用于选择金额单位
        int unitIndex = UNIT.length - strMoney.length();
        // 用于判断当前为是否为零
        boolean isZero = false;
        StringBuilder resultBuilder = new StringBuilder();
        for (; numIndex < strMoney.length(); numIndex++, unitIndex++) {
            char num = strMoney.charAt(numIndex);
            if (num == '0') {
                isZero = true;
                // 如果当前位是亿、万、元，且数值为零, 补单位亿、万、元
                if (UNIT[unitIndex] == "亿" || UNIT[unitIndex] == "万" || UNIT[unitIndex] == "元") {
                    resultBuilder.append(UNIT[unitIndex]);
                    isZero = false;
                }
            } else {
                if (isZero) {
                    resultBuilder.append("零");
                    isZero = false;
                }
                resultBuilder.append(NUM[Integer.parseInt(String.valueOf(num))] + UNIT[unitIndex]);
            }
        }

        String resultStr = resultBuilder.toString();

        // 不是角分结尾就加"整"字
        if (!resultStr.endsWith("角") && !resultStr.endsWith("分")) {
            resultStr = resultStr + "整";
        }
        // 例如没有这行代码，数值"400000001101.2"，输出就是"肆千亿万壹千壹佰零壹元贰角"
        resultStr = resultStr.replaceAll("亿万", "亿");

        return resultStr;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
//        List<BigDecimal> list = Arrays.asList(
//                BigDecimal.valueOf(140330701101.23),
//                BigDecimal.valueOf(0.00),
//                BigDecimal.valueOf(-1),
//                BigDecimal.valueOf(0.23),
//                BigDecimal.valueOf(1001.1)
//        );
//
//        for (BigDecimal value : list) {
//            System.out.println("您输入的金额（小写）为：" + value);
//            System.out.println("您输入的金额（大写）为：" + convertMoney(value));
//        }
    }
}
