package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
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
        char [][] vertical = new char [100][40];
        for (int i = 0; i<100; i++)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j<40; j++)
            {
                vertical[i][j] = '|';
                System.out.print(vertical[i][j]);
            }
            System.out.println();
        }
    }
}

class MyThread2 extends Thread
{
    public void run()
    {
        char [][] vertical = new char [100][40];
        for (int i = 0; i<100; i++)
        {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            for (int j = 0; j<40; j++)
            {
                vertical[i][j] = '-';
                System.out.print(vertical[i][j]);
            }
            System.out.println();
        }
    }
}
