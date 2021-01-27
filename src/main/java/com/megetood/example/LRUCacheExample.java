package com.megetood.example;

import com.megetood.util.cache.Cache;
import com.megetood.util.cache.LRUCache;

import java.util.Arrays;

/**
 * LRUCache Example
 *
 * @author Lei Chengdong
 * @date 2020/11/21
 */
public class LRUCacheExample {


    /**
     * 若opt=1，接下来两个整数x, y，表示set(x, y)
     * 若opt=2，接下来一个整数x，表示get(x)，若x未出现过或已被移除，则返回-1
     * 对于每个操作2，输出一个答案
     *
     * 输入；
     * [[1,1,1],[1,2,2],[1,3,2],[2,1],[1,4,4],[2,2]],3
     * 输出：
     * [1,-1]
     *
     * @param operators int整型二维数组 the ops
     * @param k         int整型 the k
     * @return int整型一维数组
     */
    public int[] LRU(int[][] operators, int k) {
        if (operators == null || operators.length == 0 || k < 1) {
            return new int[]{};
        }

        Cache<Integer, Integer> lru = new LRUCache(k);

        int len = 0;
        for (int i = 0; i < operators.length; i++) {
            if (operators[i][0] == 2) {
                len++;
            }
        }
        int[] res = new int[len];

        int r = 0;
        for (int i = 0; i < operators.length; i++) {
            int[] op = operators[i];

            int flag = op[0];
            if (flag == 2) {
                res[r++] = lru.get(op[1]);
            } else {
                lru.set(op[1], op[2]);
            }
        }

        return res;
    }

    public static void main(String[] args) {
        int[][] operators = {{1, 1, 1}, {1, 2, 2}, {1, 3, 2}, {2, 1}, {1, 4, 4}, {2, 2}};
        System.out.println("input: " + Arrays.deepToString(operators) + ", length: " + operators.length);
        int k = 3;
        int[] res = new LRUCacheExample().LRU(operators, k);
        System.out.println(Arrays.toString(res));
    }


}
