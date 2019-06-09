package com.anshuman.multithreading;

public class GenericsTest<T> {

    T temp;

    public GenericsTest(T temp) {
        this.temp = temp;
    }

    public void doSomething() {
        System.out.println(temp.toString());
    }
}

interface Auditable {
    public void doAudit();
}

class GenTest {
    public static void main(String[] args) {

        Auditable audit = new Auditable() {
            @Override
            public void doAudit() {
                System.out.println("Audit Done");
            }
        };

        GenericsTest<Auditable> genericsTest = new GenericsTest<>(audit);

        genericsTest.doSomething();


    }
}
