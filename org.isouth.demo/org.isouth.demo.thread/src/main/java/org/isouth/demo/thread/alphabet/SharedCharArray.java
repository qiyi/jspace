package org.isouth.demo.thread.alphabet;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 共享字符数组对象
 * 
 * @author qiyi
 * 
 */
public class SharedCharArray {
    /** 共享数组 **/
    private char[] charArray;
    /** 字符序列循环次数 **/
    private int times;
    /** 线程个数/字符序列个数 **/
    private int threads;
    /** 当前写入位置游标 **/
    private int cursor;
    private Lock lock = new ReentrantLock();
    /** 字符和对应的锁变量对象 */
    private Map<Character, Condition> conditionMap = new HashMap<Character, Condition>();

    public SharedCharArray(int threads, int times) {
        this.times = times;
        this.threads = threads;
        this.charArray = new char[this.times * threads];
        for (int i = 0; i < threads; i++) {
            this.conditionMap.put((char) ('A' + i), this.lock.newCondition());
        }
    }

    public void write(char ch) throws InterruptedException {
        this.lock.lock();
        Condition condition = this.conditionMap.get(ch);
        try {
            while ((char) ((this.cursor) % this.threads + 'A') != ch) {
                condition.await();
            }
            if (this.cursor < this.charArray.length) {
                this.charArray[cursor] = ch;
            }
            this.cursor++;
            char nextChar = (char) ((this.cursor) % this.threads + 'A');
            Condition nextTurn = this.conditionMap.get(nextChar);
            nextTurn.signal();
        } finally {
            this.lock.unlock();
        }
    }

    public char[] getCharArray() {
        return this.charArray;
    }

    /** 是否已经写满 **/
    public boolean isFull() {
        return this.cursor >= this.charArray.length;
    }
}
