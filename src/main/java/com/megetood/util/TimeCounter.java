package com.megetood.util;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Time counter
 *
 * @author Chengdong Lie
 * @date 2020/08/22 20:43
 */
public class TimeCounter {
    private static final double NANO = 1000000000.0;

    private long startTime;
    private long endTime;
    private double time;

    public static TimeCounter count(Consumer consumer) {
        TimeCounter timeCounter = new TimeCounter();
        timeCounter.start();
        consumer.accept(timeCounter.startTime);
        timeCounter.end();
        return timeCounter;
    }

    // record start time
    public void start() {
        startTime = System.nanoTime();
    }

    // record end time
    public void end() {
        endTime = System.nanoTime();
    }

    // Nanosecond time difference between start time and end time
    public TimeCounter between() {
        this.time = (endTime - startTime) / NANO;
        return this;
    }

    // print the time difference
    public void print() {
        System.out.println(getTime() + "s");
    }

    // print the difference with custom prefix
    public void print(String customPrefix) {
        System.out.println(customPrefix + ": " + getTime() + "s");
    }

    // get time difference
    public double getTime() {
        return time;
    }
}
