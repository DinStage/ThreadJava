import javax.swing.plaf.TableHeaderUI;
import java.util.Scanner;

public class TestWaitNotity{
    /***
     *  023 Методы w
     *  ait и notify  есть у всех так как есть в Object
     * */

    public static void main(String[] args) throws InterruptedException {
        WaitandNotyfy waitandNotyfy = new WaitandNotyfy();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitandNotyfy.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitandNotyfy.consumer();
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

/**
 * синхронизация происходит на обном объетке
 * 1 поток исполнил wait передал управление 2 потоку , 2 поток исполнил notify передал управление 1 потоку , 1 поток Продолжил работу!!!!
 */

class WaitandNotyfy {
    public void produce() throws InterruptedException {
        synchronized (this) {
            System.out.println("Продусер работает");
            // если вместо this мы передаем lock тогда необходимо делать lock.wait()
            wait(); // вызывается только в пределах синхронизованного блока эквивалентно this.wait()
            //wait(1000);  - столько будет ждать notyfy, если монитор свободенон продолжит
            /**
             *  после вызова wait отдаем внутренний лок ( как будто вышли из синхронизации)1 -  другие потоки могут забрать лог этого объекта
             *  2 - ждем когда будет вызван notify
             *
             *  WAIT и NOTYFY полжн вызваться на обном объекте
             * NOTYFY не освобождает монитор
             * */
            System.out.println("Продусер продолжил работу");
        }
    }
    public void consumer() throws InterruptedException {
        Thread.sleep(200);
        Scanner scanner = new Scanner(System.in);
        synchronized (this) {
            System.out.println("Ждем нажатия клавиши!");
            scanner.nextLine();
            notify(); /*  Поток передаст управление 1 потоку Все потоки которые жду wait проснулись
                пробуждает 1 поток
                  notifyall(); пробуждает все

            */
            Thread.sleep(5000);//NOTYFY не освобождает монитор
        }
    }
}
