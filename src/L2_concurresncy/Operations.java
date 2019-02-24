package L2_concurresncy;

import L2_concurresncy.exception.InsufficientFundsException;

import java.util.concurrent.TimeUnit;

/**
 * ПРОЦЕСС имеет свой отдельный выделенный кусок памяти. ПОТОКИ - отдельные легковесные процессы в рамках одного процесса.
 * Потоки шарят общую память и с этим могут быть проблемы.
 * <p>
 * Диагностика DEADLOCK: В консоли вводим jps - получаем все процессы. Вводим jstack пробел id процесса - получаем thread dump
 * конкретного процесса, в котором есть инфа о дедлоке. Либо в консоли пишем jconsole пробел id процесса - запускается тулза
 * jconsole для мониторинга. Там можно смотреть состояние программы (память,...). Через эту консоль можно даже удаленно
 * подключаться.
 */

public class Operations {

    private static long WAIT_SEC = 1000;

    public static void main(String[] args) {
        final Account a = new Account(1000);
        final Account b = new Account(2000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                transfer(a, b, 500);
            }
        }).start();

        transfer(b, a, 300);
    }

    private static void transfer(Account acc1, Account acc2, int amount) throws InsufficientFundsException {
        if (acc1.getBalance() < amount) {
            throw new InsufficientFundsException();
        }
        try {
            if (acc1.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) { //Если лок занят, то пытаемся дождаться 1 сек. Если получили, то true и идем дальше.
                try {
                    if (acc2.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)){
                        try {
                            acc1.withdraw(amount);
                            acc2.deposit(amount);
                            System.out.println(Thread.currentThread() + ": transfer successible");
                        } finally {
                            acc2.getLock().unlock();
                        }
                    }
                } finally {
                    acc1.getLock().unlock();
                }
            } else {
                acc1.incFailedTransferCount();
                acc2.incFailedTransferCount();
                System.out.println("Не получилось");
                //Error waiting lock
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


//        synchronized (acc1) {
//            try {
//                Thread.sleep(100);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            synchronized (acc2) {
//                acc1.withdraw(amount);
//                acc2.deposit(amount);
//                System.out.println(Thread.currentThread() + ": transfer successible");
//            }
//        }

    }
}
