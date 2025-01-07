public class Counter {
    private int count = 0;
    private final Object lock = new Object(); // Introduce an explicit lock object

    public void increment() {
        synchronized (lock) { // Synchronize on the explicit lock
            count++;
        }
    }

    public int getCount() {
        synchronized (lock) { // Synchronize on the explicit lock for read operations
            return count;
        }
    }
}

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });
        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                counter.increment();
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        System.out.println("Count: " + counter.getCount());
    }
}