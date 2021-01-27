package com.megetood.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 对象工具类
 *
 * @author Lei Chengdong
 * @date 2020/11/29
 */
public class ObjectUtil {

    /**
     * 去除空字符串，设为NULL
     *
     * @param obj
     */
    public static void blankToNull(Object obj) {
        Class<?> cls = obj.getClass();
        Field[] fields = cls.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Class<?> type = field.getType();
                Object val = field.get(obj);
                if (String.class.equals(type) && val != null && "".equalsIgnoreCase(val.toString())) {
                    field.set(obj, null);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static Object findFieldValue(Object obj, String fieldName) {
        if (isNull(obj) || fieldName == null) {
            return null;
        }

        for (Field field : obj.getClass().getDeclaredFields()) {
            if (fieldName.equals(field.getName())) {
                try {
                    field.setAccessible(true);
                    return field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }

    public List<Map<String, Object>> fieldsToMapWithNum(List<Object> objList, List<String> fieldNameList) {
        return fieldsToMap(objList, fieldNameList, true);
    }

    public List<Map<String, Object>> fieldsToMapWithoutNum(List<Object> objList, List<String> fieldNameList) {
        return fieldsToMap(objList, fieldNameList, false);
    }

    public static List<Map<String, Object>> fieldsToMap(List<Object> objList, List<String> fieldNameList, boolean neededNum) {
        int i = 1;
        List<Map<String, Object>> fieldMapList = new LinkedList<>();

        if (ListUtil.isEmpty(objList)) {
            return fieldMapList;
        }

        Iterator<?> ite = objList.iterator();
        while (ite.hasNext()) {
            Map<String, Object> fieldMap = fieldsToMap(ite.next(), fieldNameList);

            if (neededNum) {
                fieldMap.put("num", "" + i++);
            }

            fieldMapList.add(fieldMap);
        }

        return fieldMapList;
    }

    /**
     * 将成员变量映射成Map, 如果 fieldNameList 不为 null, 只映射 fieldNameList
     * 里的 fieldName 对应的成员变量，否则映射 obj 所有的成员变量
     *
     * @param obj           实体对象
     * @param fieldNameList 需要替换的实体对象 obj 的成员变量名
     * @return 成员变量组成的键值对Map
     */
    public static Map<String, Object> fieldsToMap(Object obj, List<String> fieldNameList) {
        Map<String, Object> res = new HashMap<>();

        if (isNull(obj)) {
            return res;
        }

        if (ListUtil.nonEmpty(fieldNameList)) {
            Iterator<String> fieldNameIte = fieldNameList.iterator();
            while (fieldNameIte.hasNext()) {
                String fieldName = fieldNameIte.next();
                Object fieldValue = findFieldValue(obj, fieldName);
                res.put(fieldName, fieldValue);
            }
        } else {
            for (Field field : obj.getClass().getDeclaredFields()) {
                String fieldName = field.getName();
                Object fieldValue = null;
                try {
                    field.setAccessible(true);
                    fieldValue = field.get(obj);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                res.put(fieldName, fieldValue);
            }
        }

        return res;
    }

    /**
     * Returns {@code true} if the provided reference is {@code null} otherwise
     * returns {@code false}.
     *
     * @param obj obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is {@code null} otherwise
     */
    public static boolean isNull(Object obj) {
        return obj == null;
    }

    /**
     * Returns {@code true} if the provided reference is non-{@code null}
     * otherwise returns {@code false}.
     *
     * @param obj a reference to be checked against {@code null}
     * @return {@code true} if the provided reference is non-{@code null}
     */
    public static boolean nonNull(Object obj) {
        return obj != null;
    }

}
