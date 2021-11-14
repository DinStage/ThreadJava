import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * При вызове в обратном порядке в разных потоках  lock2.lock() и  lock.lock(); возникает ДЕДЛОК
 * 1 Способо не допуска забирать локи в одном порядке ! если в программе
 * 2 ReentrantLock при сипользовании его
 */


public class Deadlock {

    public static void main(String[] args) throws InterruptedException {
        Runner runner = new Runner();
        Thread  thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.firstThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    runner.secondThread();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();

        runner.finished();

    }

}
class Runner {
    private Account account1 = new Account();
    private  Account account2 = new Account();

    private Lock lock = new ReentrantLock(); // Лок для первого объекта ,
    private Lock lock2 = new ReentrantLock();

    /**
     * Для решения переносим метод локов в отдельный метод
     */

    private  void  takeLock(Lock lock1,Lock lock2) throws InterruptedException {
            boolean firstLockTaken = false;
            boolean secondLockTaken = false;

            while (true) { // крутимя пока не отдадим объекты
                try {

                    //firstLockTaken  и secondLockTaken подаем результаты произведенного забора lock1 и  lock2
                    firstLockTaken = lock1.tryLock(); // патыется забрать лок , если успешно то он возварщает истину , если не успешно , если не получилось вернут ложь
                    secondLockTaken = lock2.tryLock();
                } finally {

                    /**
                     *   Если мы забрали оба лока тогда выходим из метода
                     *   если мы забрали только 1 лог (первый или второй) программа пойдет далее исполняться и мы  освододим нужный нам лок
                     * */
                    if (firstLockTaken && secondLockTaken) {
                        return;
                    }
                    if (firstLockTaken) {
                        lock1.unlock();
                    }
                    if (secondLockTaken) {
                        lock2.unlock();
                    }

                }
                Thread.sleep(1); // спим 1 милисекунду для ожидания , даем возможность отдать другому потоку локи
            }
    }


    public void firstThread() throws InterruptedException {
        Random random = new Random();
        /**
         * делаем синхронизацию
         * **/
        for (int i = 0; i < 10000; i++) {
           // lock2.lock(); /* При вызове в обратном порядке lock2.lock() и  lock.lock(); возникает ДЕДЛОК */
            //lock.lock();
            takeLock(lock,lock2);
            try {
                Account.transfer(account1, account2, random.nextInt(100));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                lock.unlock(); // разблокировку всегда делать в файнале блоке , чтобы если возникла ошибка отпускать ее
                lock2.unlock();

            }


        /**
        *  этот варинт позволяет синхронизироваться но он не приветствуется ухудшается понимаемость программы , Лучше делать с помощию рентран локов
        // Если блоки synchronized в разных поткоах пометь местами то мы создадим дедлок
         synchronized (account1) {
            synchronized (account2) {

                    Account.transfer(account1, account2, random.nextInt(100));
                }
            } }**/
        }

    }

    public void secondThread() throws InterruptedException {

        Random random = new Random();

        for (int i = 0; i < 10000; i++) {
           // lock.lock();
            //lock2.lock();
            takeLock(lock2,lock);
            try {
                Account.transfer(account2, account1, random.nextInt(100));
            } catch (Exception e) {
                e.printStackTrace();
            }
            finally {
                lock.unlock(); // разблокировку всегда делать в файнале блоке , чтобы если возникла ошибка отпускать ее
                lock2.unlock();

            }


            /**
             *  этот варинт позволяет синхронизироваться но он не приветствуется ухудшается понимаемость программы , Лучше делать с помощию рентран локов
            synchronized (account1) {
                synchronized (account2) {

                    Account.transfer(account1, account2, random.nextInt(100));
                }
            }**/


        }



    }

    public void finished(){
        System.out.println("Балан 1 :" + account1.getBalance());
        System.out.println("Балан 2 :" + account2.getBalance());
        System.out.println("Итог = " +( account1.getBalance() +  account2.getBalance()));
    }
}

class  Account {
    private  int balance = 10000;
    public  void  deposit (int amount) {
      balance+=amount ;

    }
    public  void  withdrow(int amount) {
        balance-=amount ;
    }

    public int getBalance() {
        return balance;
    }
    public  static  void  transfer(Account account1 ,  Account account2 , int amount) {

        account1.withdrow(amount);
        account2.deposit(amount);
    }
}
