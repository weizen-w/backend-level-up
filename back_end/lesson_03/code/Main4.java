package de.ait.shop;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 9/18/2023
 * Spring
 *
 * @author Marsel Sidikov (AIT TR)
 */
public class Main4 {
    public static void main(String[] args) {

        try (ExecutorService service = Executors.newFixedThreadPool(2)) {
            service.submit(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " A");
                }
            });

            service.submit(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " B");
                }
            });

            service.submit(() -> {
                for (int i = 0; i < 100; i++) {
                    System.out.println(Thread.currentThread().getName() + " C");
                }
            });
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " A");
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " B");
        }

        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " C");
        }


    }
}
