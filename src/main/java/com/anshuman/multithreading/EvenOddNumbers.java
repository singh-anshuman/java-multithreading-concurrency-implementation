package com.anshuman.multithreading;

class Odd implements Runnable {
    TempClass tempClass;

    public Odd(TempClass tempClass) {
        this.tempClass = tempClass;
    }

    public void run() {
        while(true) {
            synchronized (this.tempClass) {
                if(this.tempClass.number%2==1) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() +" - "+ this.tempClass.number++);
                    this.tempClass.notify();
                } else {
                    try {
                        this.tempClass.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

class TempClass {
    Integer number;

    public TempClass(Integer number) {
        this.number = number;
    }
}

class Even implements Runnable {
    TempClass tempClass;

    public Even(TempClass tempClass) {
        this.tempClass = tempClass;
    }

    public void run() {
        while(true) {
            synchronized (this.tempClass) {
                if(this.tempClass.number%2==0) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(Thread.currentThread().getName() +" - "+ this.tempClass.number++);
                    this.tempClass.notify();
                } else {
                    try {
                        this.tempClass.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

public class EvenOddNumbers {

    public static void main(String[] args) {

        TempClass tempClass = new TempClass(1);
        Thread t1 = new Thread(new Odd(tempClass));
        t1.start();

        Thread t2 = new Thread(new Even(tempClass));
        t2.start();

    }
}
