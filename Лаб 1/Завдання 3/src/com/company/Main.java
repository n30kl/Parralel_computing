package com.company;

public class Main {

    public static void main(String[] args) {
        MyThread1 myThread1 = new MyThread1();
        myThread1.start();

        MyThread2 myThread2 = new MyThread2();
        myThread2.start();
    }
}
class MyThread1 extends Thread
{
    public void run()
    {
        Counter.increment();
    }
}

class MyThread2 extends Thread
{
    public void run()
    {
        Counter.decrement();
    }
}


