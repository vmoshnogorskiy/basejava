package com.basejava;

public class MainDeadlock {

    public static void main(String[] args) {
        final Object LOCK_1 = new Object();
        final Object LOCK_2 = new Object();

        new Thread(() -> {
            MainDeadlock.localRun(LOCK_1, LOCK_2);
        }).start();
        new Thread(() -> {
            MainDeadlock.localRun(LOCK_2, LOCK_1);
        }).start();
    }

    private static void localRun(Object lockA, Object lockB) {
        synchronized (lockA) {
            System.out.println(Thread.currentThread().getName() + " is taken Object " + lockA.toString() + " monitor");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(Thread.currentThread().getName() + " sleep interrupted");
            }
            System.out.println(Thread.currentThread().getName() + " trying to take " + lockB.toString() + " monitor..");
            synchronized (lockB) {
                System.out.println(Thread.currentThread().getName() + " is taken Object " + lockB.toString() + " monitor");
            }
        }
    }
}
