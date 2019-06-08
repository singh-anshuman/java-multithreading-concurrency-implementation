package com.anshuman.multithreading;

import java.util.ArrayList;
import java.util.List;

class Producer implements Runnable{

    List<Item> items;
    int counter=1;

    public Producer(List<Item> items) {
        this.items = items;
    }

    void produce() {
        while(true) {
            synchronized (items) {
                if(items.size()<=2) {
                    System.out.println("Producing Item-"+counter);
                    try {
                        Thread.sleep(1000);
                        items.add(new Item("Item-"+counter++));
                        System.out.println("Notifying Consumer");
                        items.notify();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println("Producer waiting for items to be consumed");
                        items.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public void run() {
        produce();
    }
}

class Consumer implements Runnable{

    List<Item> items;

    public Consumer(List<Item> items) {
        this.items = items;
    }

    void consume() {
        while(true) {
            synchronized (items) {
                if(items.isEmpty()) {
                    try {
                        items.wait();
                        System.out.println("Consumer waiting for items to be produced");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Consuming "+items.get(0).getName());
                    items.remove(0);
                    System.out.println("Notifying Producer");
                    items.notify();
                }
            }
        }
    }

    @Override
    public void run() {
        consume();
    }
}

class Item {
    private String name;

    public Item(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

public class ProducerConsumer {

    public static void main(String[] args) {
        List<Item> items = new ArrayList<>();

        Thread t1 = new Thread(new Producer(items));
        t1.start();

        Thread t2 = new Thread(new Consumer(items));
        t2.start();

    }

}
