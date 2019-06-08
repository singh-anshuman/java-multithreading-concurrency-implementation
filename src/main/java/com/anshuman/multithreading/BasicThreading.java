package com.anshuman.multithreading;

class MyThread extends Thread {
    Runnable runnable;

    public MyThread(Runnable runnable) {
        this.runnable = runnable;
    }

    public void run() {
        System.out.println("MyThread's Run Method Executed with "+Thread.currentThread().getName());
    }
}

class MyRunnable implements Runnable {

    @Override
    public void run() {
        System.out.println("MyRunnable's Run Method Executed with "+Thread.currentThread().getName());
    }
}

public class BasicThreading {
    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println("Thread: "+Thread.currentThread().getName());
        });
        t1.start();
        System.out.println("Thread: "+Thread.currentThread().getName());

        MyRunnable r1 = new MyRunnable();
        MyThread t2 = new MyThread(r1);

        t2.start();

        Thread t3 = new Thread(r1);
        t3.start();
    }

}
