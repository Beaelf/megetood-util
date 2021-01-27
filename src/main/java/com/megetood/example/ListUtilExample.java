package com.megetood.example;

import com.google.common.collect.Lists;
import com.megetood.example.model.User;
import com.megetood.util.ListUtil;

import java.util.ArrayList;
import java.util.Date;

/**
 * ListUtil Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class ListUtilExample {
    public static void main(String[] args) {
        long l = System.currentTimeMillis();
        ArrayList<User> list = Lists.newArrayList(
                new User("a", new Date(l)),
                new User("b", new Date(l + 120000)),
                new User("c", new Date(l + 60000))
        );
        list.forEach(c -> System.out.println(c.getBirth()));

        User max = ListUtil.findMax(list, (a, b) -> {
            Date aBirth = a.getBirth();
            Date bBirth = b.getBirth();
            if (aBirth.before(bBirth)) {
                return -1;
            } else if (aBirth.after(bBirth)) {
                return 1;
            } else {
                return 0;
            }
        });

        System.out.println(max.getBirth());
    }
}
