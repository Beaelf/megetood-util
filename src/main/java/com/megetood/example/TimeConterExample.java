package com.megetood.example;

import com.megetood.util.TimeCounter;

/**
 * TimeConter Example
 *
 * @author Chengdong Lei
 * @date 2021/1/26
 */
public class TimeConterExample {
    public static void main(String[] args) {
        exmaple1();
        exmaple2();
    }

    private static void exmaple2() {
        TimeCounter.count(startTime -> doSomething()).print();
    }

    private static void exmaple1() {
        TimeCounter timeCounter = new TimeCounter();
        timeCounter.start();

        // do something
        doSomething();

        timeCounter.end();

        timeCounter.print();
    }

    private static void doSomething() {
        for (int i = 0; i < 100000; i++) {
            // do something
        }
    }
}
