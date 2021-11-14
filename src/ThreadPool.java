import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    public static void main(String[] args) throws InterruptedException {

        // пул потоков!
        ExecutorService executorService = Executors.newFixedThreadPool(2); // на вход требует количество потоков
            for (int i = 0 ; i<5 ; i++) {
                executorService.submit(new Work(i));
            }
            executorService.shutdown(); // перестаем принимать новые задания , начинаем выполнять переданные задания
            System.out.println(" задания в работе");

        /**
         * Вы передали двум работникам Потокам  ! 5 коробок и ждем когда они отработают!!!!!
         * */
            executorService.awaitTermination(1, TimeUnit.DAYS); // ожидаем выполнения заданий , указываем предельное время ожидания
        // , если не выполнят то вы пойдем дальше и завешимся




    }
}
class Work implements  Runnable {
    private int id;
    Work(int id) {
        this.id = id;
    }
    @Override
    public void run() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Work id = "  + id + "completed ");

    }
}
