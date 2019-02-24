package L2_concurresncy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private int balance;

    private Lock lock;

    /**счетчик неудавшихся трансферов*/
//    private int failCounter;
    private AtomicInteger failCounter;

    public Account(int balance) {
        this.balance = balance;
        this.lock = new ReentrantLock();
        this.failCounter = new AtomicInteger(0);
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void incFailedTransferCount() {
//        failCounter++; //операция неатомарна, т.к. сначала получаем (кэшируем), инкрементируем, обновляем значение. И так могут одновременно делать несколько потоков
        failCounter.incrementAndGet(); //операция атомарна, но она неблокирующаяся.
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public Lock getLock() {
        return lock;
    }

    public void setLock(Lock lock) {
        this.lock = lock;
    }

//    public int getFailCounter() {
    public AtomicInteger getFailCounter() {
        return failCounter;
    }

}
