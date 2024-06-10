package ru.gb.jdk.homework_5;

import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread{
    private String philosopher;
    private int leftFork;
    private int rightFork;
    private int countMeal;
    private CountDownLatch latch;
    private Table table;

    public Philosopher(String philosopher, int leftFork, int rightFork, CountDownLatch latch, Table table) {
        this.philosopher = philosopher;
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.latch = latch;
        this.table = table;
        countMeal = 0;
    }

    public String getPhilosopher() {
        return philosopher;
    }

    public int getLeftFork() {
        return leftFork;
    }

    public int getRightFork() {
        return rightFork;
    }

    @Override
    public void run() {
        while (countMeal<3) {
            try {
                eat();
                think();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        latch.countDown();
        System.out.println(getPhilosopher() + " ушёл из-за стола");
    }

    public void eat() throws InterruptedException {
        if (table.tryToTakeForks(getLeftFork(), getRightFork())) {
            System.out.println(getPhilosopher() + " начал есть");
            sleep(3000);
            table.returnForks(getLeftFork(), getRightFork());
            System.out.println(getPhilosopher() + " закончил есть");
            countMeal++;
        }
    }

    public void think() throws InterruptedException {
        sleep(6000);
    }
}
