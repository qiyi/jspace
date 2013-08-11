package org.isouth.demo.thread.alphabet;

public class ThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        int threads = 8;
        int times = 8;
        SharedCharArray sharedCharArray = new SharedCharArray(threads, times);
        Thread[] writeTheads = new Thread[threads];
        for (int i = 0; i < threads; i++) {
            writeTheads[i] = new WriteThread(sharedCharArray, (char) ('A' + i));
        }
        for (int j = 0; j < threads; j++) {
            writeTheads[j].start();
        }
        for (int k = 0; k < threads; k++) {
            writeTheads[k].join();
        }
        System.out.println(new String(sharedCharArray.getCharArray()));
    }
}
