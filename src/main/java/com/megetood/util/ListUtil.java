package com.megetood.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * List Util
 *
 * @author Chengdong Lei
 * @date 2020/8/7
 */
public class ListUtil {

    /**
     * Returns a {@code Map} with grouped elements on input elements
     * of type {@code T}, grouping elements according to a classification
     * function, and returning the results in a {@code Map}.
     *
     * @param list the list to be grouped
     * @param f    the classification function
     * @param <T>  the type of the input elements
     * @param <R>  the type of the keys
     * @return a {@code Map} with grouped elements
     */
    public static <T, R> Map<R, List<T>> group(List<T> list, Function<T, R> f) {
        if (isEmpty(list) || Objects.isNull(f)) {
            return null;
        }

        return list.stream().collect(Collectors.groupingBy(f));
    }

    /**
     * Returns a list consisting of the distinct elements
     *
     * @param list the list to be distincted
     * @param f    to apply a element in the list, and supply a value you make
     * @param <T>  the class of the objects in the list
     * @param <R>  the class of the object supplied by f
     * @return a list consisting of the distinct elements
     */
    public static <T, R> List<T> distinct(List<T> list, Function<T, R> f) {
        if (isEmpty(list) || Objects.isNull(f)) {
            return null;
        }

        Set<R> set = new HashSet<>();
        List<T> res = new ArrayList<>();

        Iterator<T> ite = list.iterator();
        while (ite.hasNext()) {
            T cur = ite.next();
            if (!set.contains(f.apply(cur))) {
                res.add(cur);
                set.add(f.apply(cur));
            }
        }

        return res;
    }

    /**
     * Finds the specified list for the matched predicate object list
     *
     * @param list the list to be finded
     * @param p    the predicate to be matched
     * @param <T>  the class of the objects in the list
     * @return the index of the find key, if it is contained in the list;
     */
    public static <T> int find(List<T> list, Predicate<T> p) {
        if (isEmpty(list) || Objects.isNull(p)) {
            return -1;
        }

        for (int i = 0; i < list.size(); i++) {
            if (p.test(list.get(i))) {
                return i;
            }
        }

        return -1;
    }

    /**
     * Finds the specified list for the matched predicate object list
     *
     * @param list the list to be finded
     * @param p    the predicate to be matched
     * @param <T>  the class of the objects in the list
     * @return a list matching the predicate
     */
    public static <T> List<T> findAll(List<T> list, Predicate<T> p) {
        if (isEmpty(list) || Objects.isNull(p)) {
            return null;
        }

        List<T> res = new ArrayList<>();

        Iterator<T> ite = list.iterator();
        while (ite.hasNext()) {
            T cur = ite.next();
            if (p.test(cur)) {
                res.add(cur);
            }
        }

        return res;
    }

    /**
     * Finds the specified maximun element with the comparator
     *
     * @param list the list to be finded
     * @param comparator    the comparator to be used
     * @param <T>  the class of the objects in the list
     * @return a maximum element of the list
     */
    public static <T> T findMax(List<T> list, Comparator<T> comparator) {
        if (isEmpty(list) || Objects.isNull(comparator)) {
            return null;
        }

        T max = list.get(0);
        for (int i = 1; i < list.size(); i++) {
            if (comparator.compare(max, list.get(i)) <= 0) {
                max = list.get(i);
            }
        }

        return max;
    }

    /**
     * Null-safe check if the specified collection is empty
     *
     * @param collection the collection to check, may be null
     * @return true if empty or null
     */
    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }

    /**
     * Null-safe check if the specified collection is not empty
     *
     * @param collection the collection to check, may be null
     * @return true if non-null and non-empty
     */
    public static boolean nonEmpty(Collection<?> collection) {
        return !isEmpty(collection);
    }
}
