package com.company;

public class Counter {
    private static int count = 0;

    public static void increment() {
        for (int i = 0; i < 100000; i++)
        {
            count++;
            System.out.println(count);
        }
    }
    public static void decrement() {
        for (int i = 0; i < 100000; i++)
        {
            count--;
            System.out.println(count);
        }
    }
}
