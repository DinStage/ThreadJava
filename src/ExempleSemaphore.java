/**
 *  Семаофр используется в том случае когда есть ресур  и много поток используеют ресурс,
 *  мы ходим ограничить количесвто потоков которые могут работать с ресурсом
 *  с помощью семофора можно делить ресур межу поткоами
 */

import java.util.concurrent.*;

public class ExempleSemaphore {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(200);
        Connection connection = Connection.getConnection();
        for (int i=0 ; i<10 ; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        connection.work();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS);


     /***
      *
      *   Semaphore semaphore = new Semaphore(3); // Количество одновременных потоков которе могу проходить (РАЗРЕШЕНИЕ)  , максимум 3 потока используют ресурс
        try {
            // когда в потоке используем ресурс говорим что мы взяли один ресурс в поке
            // если занямы все доступные разрешения будет происходить ожидание
            semaphore.acquire(); // начинаем в потке взаимодествовать с ресурсом
            semaphore.acquire();
            semaphore.acquire();

            System.out.println("все разрешения истрачены!");
            semaphore.acquire();
            System.out.println("Очередь есть! ");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //говорим семафору что один ресур освободили
        semaphore.release(); // отпускает разрешение , когда в потоке заканчиваем работу с ресурсом

        System.out.println(semaphore.availablePermits()); // Показывает сколько достпуно разрешений
      **/




    }
}
// singleton
class  Connection {
    private  static Connection connection = new Connection();
    private Semaphore semaphore = new Semaphore(10);
    private int connectionCount;
    private Connection(){   }
    public static   Connection getConnection() {
       return connection;
   }

   public  void work() throws InterruptedException {
        semaphore.acquire(); // получим допуный , доспуно всего  10 Semaphore(10) , 11 поток будет ждать
       try {
           doWork(); // надо оборачивать
       } catch (InterruptedException e) {
           e.printStackTrace();
       }
       finally {
           // release нужно всегда ставить в финали так как если возникнет ошибка его не отпустит
           semaphore.release(); // освободим  для очередного потока
           // 200 потоков будут вработать все но , более 10 не будет лезть
       }

   }

   private void doWork() throws InterruptedException {
        synchronized (this){
                connectionCount++;
            System.out.println(connectionCount);
        }
        Thread.sleep(5000);

       synchronized (this){
           connectionCount--;
       }
   }

}
