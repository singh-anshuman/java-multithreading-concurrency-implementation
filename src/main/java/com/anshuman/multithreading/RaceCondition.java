package com.anshuman.multithreading;

class MyAccount {
    Integer balance;

    public MyAccount(Integer balance) {
        this.balance = balance;
    }

    public boolean withdrawAmount(Integer amount) {
        if(balance >= amount) {
            balance = balance - amount;
            return true;
        }
        return false;
    }
}

public class RaceCondition {

    public static void main(String[] args) throws InterruptedException {
        MyAccount a1 = new MyAccount(10000);

        Thread t1 = new Thread(()-> {
            while(true) {
                if(!a1.withdrawAmount(10)) {
                    break;
                }
                System.out.println("Money Withdrawn "+Thread.currentThread().getName()+" Balance: "+a1.balance);
            }
        });

        Thread t2 = new Thread(()-> {
            while(true) {
                if(!a1.withdrawAmount(10)) {
                    break;
                }
                System.out.println("Money Withdrawn "+Thread.currentThread().getName()+" Balance: "+a1.balance);
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("Final Balance: "+a1.balance);
    }

}
