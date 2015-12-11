package com.sargin.utkan.volatilesample;

/**
 * Created by utkan on 10/12/15.
 */
public class ProducerConsumer {

    private String value = "";
    private volatile boolean hasValue = false;

    public void produce(String value) {
        while (hasValue) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Producing " + value + " as the next consumable");
        this.value = value;
        hasValue = true;
    }

    public String consume() {
        while (!hasValue) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        String value = this.value;
        hasValue = false;
        System.out.println("Consumed " + value);
        return value;
    }
}