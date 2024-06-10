package ru.gb.jdk.homework_5;

import java.util.concurrent.CountDownLatch;

public class Table extends Thread{
    private final int philosophersNumber = 5;
    private Fork[] forks;
    private Philosopher[] philosophers;
    private CountDownLatch latch;

    public Table() {
        philosophers = new Philosopher[philosophersNumber];
        forks = new Fork[philosophers.length];
        latch = new CountDownLatch(philosophersNumber);
        createForks();
        createPhilosophers();
    }

    @Override
    public void run() {
        try {
            startDinner();
            System.out.println("Ужин начался");
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Ужин окончен, все поели");
    }

    public void createForks() {
        for (int i = 0; i < forks.length; i++) {
            forks[i] = new Fork();
        }
    }

    public void createPhilosophers(){
        for (int i = 0; i < philosophers.length; i++) {
            philosophers[i] = new Philosopher("Философ " + (i+1), i, (i+1) % philosophers.length, latch, this);
        }
    }

    public synchronized boolean tryToTakeForks(int leftFork, int rightFork){
        if(!forks[leftFork].getInUse() && !forks[rightFork].getInUse()){
            forks[leftFork].setInUse(true);
            forks[rightFork].setInUse(true);
            return true;
        }
        return false;
    }

    public void returnForks(int leftFork, int rightFork){
        forks[leftFork].setInUse(false);
        forks[rightFork].setInUse(false);
    }

    public void startDinner(){
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }
    }
}
