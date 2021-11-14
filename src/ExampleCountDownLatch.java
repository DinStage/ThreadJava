import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *  Защелка позволяет использовать синхронизацию между потоками без каких либо дополнительных вещей
 * **/
public class ExampleCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        // защелка обратного отсчета , передается количество итераций , после которых защелка отопрется
        // класс потокобезопасный
        CountDownLatch countDownLatch = new CountDownLatch(3);
        // пулл потоков
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        for (int i = 0 ; i<3 ; i++)
            executorService.submit(new Processor(i , countDownLatch)); // countDownLatch отщелкивает на 3 единицы назад


        executorService.shutdown();

        // ожидаем когда защелка открылась
        countDownLatch.await();
        System.out.println("Защелка открыта, поток продолжает свою работу!");



    }
}

class Processor implements Runnable {
    private int id;
    private  CountDownLatch countDownLatch;
    Processor(int id, CountDownLatch countDownLatch ) {
        this.id = id;

        this.countDownLatch = countDownLatch;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
    }
}
