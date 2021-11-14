import java.util.Random;
import  java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ProducerConsumer {
    private static   BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(10); // количество элементов;
    public static void main(String[] args) throws InterruptedException {

        /***
            Очередь первый зашел первый вышел
         !!!потообезовапсна, предельный размер

         **/

        Thread threadProducer = new Thread(new Runnable() {
            @Override
            public void run() {
                produce();
            }
        });

        Thread threadConsumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        threadConsumer.start();
        threadProducer.start();

        threadConsumer.join();
        threadProducer.join();

        /**
         *  Первый поток кладет объекты в очередь , как только место освободилось он добавляет еще элемнты!
         *  Второй поток извлекает потоки из очереди
         *   Реализация простой системы взаимодействия на основе очереди
         */




    }
    private static void produce() {
        Random random = new Random();
        while (true) {
            try {
                //Если очередь заполнена будет ждать пока очередь освободится
                blockingQueue.put(random.nextInt(100)); // Положить в очередь
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
    private static void consumer() throws InterruptedException {
    while (true)
    {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Взять элемент
        System.out.println(blockingQueue.take());
        System.out.println("Размер очереди :" + blockingQueue.size());

    }
    }
}
