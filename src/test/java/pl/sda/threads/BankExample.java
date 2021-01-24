package pl.sda.threads;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class BankExample {
    @Test
    @Disabled
    void bankSequentially(){
        for (int i = 0; i < 100; i++) {
            new ClientAction().run();
        }
        System.out.println(Bank.getBalance());
    }

    @Test
    @Disabled
    void bankWithThreads(){
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            threads.add(new Thread(new ClientAction()));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("end balance: " + Bank.getBalance());
        System.out.println("counter: " + Bank.getCounter());
    }
}
