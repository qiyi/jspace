package org.isouth.demo.thread.alphabet;

public class WriteThread extends Thread {

    private char alphabet;
    private SharedCharArray sharedCharArray;

    public WriteThread(SharedCharArray sharedCharArray, char alphabet) {
        super(alphabet + "-thread");
        this.sharedCharArray = sharedCharArray;
        this.alphabet = alphabet;
    }

    @Override
    public void run() {
        try {
            while (!this.sharedCharArray.isFull()) {
                this.sharedCharArray.write(this.alphabet);
            }
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
