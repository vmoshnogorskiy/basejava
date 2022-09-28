package com.basejava;

public class MainDeadlock {

    public static void main(String[] args) {
        Object a = new Object();
        Object b = new Object();

        new Thread(() -> {
            synchronized (a) {
                System.out.println(Thread.currentThread().getName() + " is taken Object 'a' monitor");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " sleep interrupted");
                }
                System.out.println(Thread.currentThread().getName() + " trying to take 'b' monitor..");
                synchronized (b) {
                    System.out.println(Thread.currentThread().getName() + " is taken Object 'b' monitor");
                }
            }
        }).start();
        new Thread(() -> {
            synchronized (b) {
                System.out.println(Thread.currentThread().getName() + " is taken Object 'b' monitor");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    System.out.println(Thread.currentThread().getName() + " sleep interrupted");
                }
                System.out.println(Thread.currentThread().getName() + " trying to take 'a' monitor..");
                synchronized (a) {
                    System.out.println(Thread.currentThread().getName() + " is taken Object 'a' monitor");
                }
            }
        }).start();
    }
}
