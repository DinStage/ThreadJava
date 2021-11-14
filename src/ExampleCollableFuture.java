import java.util.Random;
import java.util.concurrent.*;

/**
 * Данные класы Collable и Future позволяют  возвращать значения из потоков
 *
 *
 * **/
public class ExampleCollableFuture {
   // private static int result ;
    public static void main(String[] args) throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(1);


        /**
        executorService.submit(new Runnable() {
            @Override

          //   * так как  rUNABLE VOID из него мы не сможем возвращать . Нельзя вернуть значение из потока

            public void run() {


                System.out.println("Поток запущен");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Поток выполнился");
                    result ++;
            }
        });

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.DAYS); // ждем пока все не завершится
        так можно получить данные из потока
        System.out.println(result);
        **/

       // Вместо интерфейса Runnable можно  использовать интерфейс Callable

                                             //  В Интерфейсе Callable Указываем возвращаемое значение из потока

      // интерфейс параметризиркем тем же типом что и  Callable                         // если преобразовать к лямбде визуально все будет выглядеть одинаково но java сама поймет что будет уже интерфейс Callable
       Future<Integer>  future =  executorService.submit(new Callable<Integer>() {
            @Override
            // тут указывается возвращаемое значение
            public Integer call() throws Exception {
                System.out.println("Поток запущен");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Поток выполнился");
                Random random = new Random();
                int randomValue = random.nextInt(10);
                if ( randomValue <5) {
                    throw new Exception("Возникла ошибка");
                }
                return  random.nextInt(10 ) ; // пример того что мы будем возвращать из потока
            }
        });

        executorService.shutdown();

        try {
            int result = future.get(); // get дожидается окончание выполнения потока
            System.out.println("Результат  :" + result);

        } catch (ExecutionException e) {
           Throwable throwable =  e.getCause(); // Причина исключения
            System.out.println(throwable.getMessage());
            //e.printStackTrace();
        }
        executorService.awaitTermination(1 , TimeUnit.DAYS);
        System.out.println("Конец");

    }


}
