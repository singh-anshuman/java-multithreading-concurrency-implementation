package com.anshuman.multithreading;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class Machine implements Runnable {
    List<Design> designs;

    public Machine(List<Design> designs) {
        this.designs = designs;
    }

    public List<Design> getDesigns() {
        return designs;
    }

    public void setDesigns(List<Design> designs) {
        this.designs = designs;
    }

    public void processDesign() {
        synchronized(designs) {
            while(true) {
                if(designs.size()==0) {
                    try {
                        System.out.println("Waiting for new designs to come in");
                        designs.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Iterator<Design> it = designs.iterator();
                while (it.hasNext()) {
                    Design design = it.next();
                    System.out.println("Machine Processing "+design.getDesignDescription());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    it.remove();
                }
                System.out.println("Machine Notifying that it is done processing all the elements");
                designs.notify();
            }
        }
    }

    @Override
    public void run() {
        processDesign();
    }
}

class Operator implements Runnable {
    List<Design> designs;

    public Operator(List<Design> designs) {
        this.designs = designs;
    }

    public List<Design> getDesigns() {
        return designs;
    }

    public void setDesigns(List<Design> designs) {
        this.designs = designs;
    }

    public void addDesigns() {
        synchronized (designs) {
            if(designs.size()>=2) {
                try {
                    System.out.println("Operator waiting for the designs to be consumed before adding new ones");
                    designs.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Operator Adding Design Test 1");
            designs.add(new Design("Test 1"));
            System.out.println("Operator Adding Design Test 2");
            designs.add(new Design("Test 2"));
            System.out.println("Operator Adding Design Test 3");
            designs.add(new Design("Test 3"));
            System.out.println("Notifying the machine that new designs are available");
            designs.notify();
        }
    }

    @Override
    public void run() {
        addDesigns();
        System.out.println("Operator Logging Out");
        System.out.println("Operator Logging In Again...Adding More designs");
        addDesigns();
    }
}

class Design {
    private String designDescription;

    public Design(String designDescription) {
        this.designDescription = designDescription;
    }

    public String getDesignDescription() {
        return designDescription;
    }

    public void setDesignDescription(String designDescription) {
        this.designDescription = designDescription;
    }
}

public class MachineOperator {

    public static void main(String[] args) {
        List<Design> designs = new ArrayList<>();
        designs.add(new Design("Design 1"));
        designs.add(new Design("Design 2"));
        designs.add(new Design("Design 3"));
        designs.add(new Design("Design 4"));

        Operator operator = new Operator(designs);
        Machine machine = new Machine(designs);

        Thread t1 = new Thread(machine);
        t1.start();

        Thread t2 = new Thread(operator);
        t2.start();

    }
}
