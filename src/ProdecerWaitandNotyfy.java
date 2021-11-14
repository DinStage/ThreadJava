import javax.swing.plaf.TableHeaderUI;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

/**
 * Реализвация патерна Продюсер консумер с помощью обычной очереди и
**/
public class ProdecerWaitandNotyfy{

    // Обычная очередь не потокобезопасная



    public static void main(String[] args) throws InterruptedException {


        ProducerConsumerWaitNotyfy prodecerWaitandNotyfy = new ProducerConsumerWaitNotyfy();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prodecerWaitandNotyfy.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    prodecerWaitandNotyfy.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });



        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

    }
}


class ProducerConsumerWaitNotyfy {
    private Queue<Integer> queue = new LinkedList<>();
    private final int LIMIT = 10;
    private Object lock = new Object();



    public void produce() throws InterruptedException {
        int value = 0;
        while (true)
            synchronized (lock) {
                while (queue.size() == LIMIT) {
                        lock.wait();
                }

                queue.offer(value++);
                lock.notify(); // Положили элемент - необходимо передаль далее на исполнение!!!
            }
        }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized (lock) {
                while (queue.size() == 0) {
                    lock.wait();

                }
               int value =  queue.poll(); // забрали элемент из очереди
               System.out.println(value);
                System.out.println("Размер очереди :" + queue.size() );
               lock.notify(); // передаем управление
            }
            Thread.sleep(1000);
        }

    }
}
