package model;

import java.util.concurrent.Semaphore;

public class IdGenerator {
    private final Semaphore semaphore;
    private int value;
    
    public IdGenerator() {
        semaphore = new Semaphore(1);
        value = 0;
    }

    public int getIntegerId() throws InterruptedException {
        semaphore.acquire();
        final int currentValue = value;
        value++;
        semaphore.release();
        return currentValue;
    }
    
    public String getStringId() throws InterruptedException {
        return Integer.toString(getIntegerId());
    }
    
}
