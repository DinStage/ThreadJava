import java.util.Random;

public class InteraptedExample {
    public static void main(String[] args) throws InterruptedException {


    Thread thread = new Thread(new Runnable() {
        @Override
        public void run() {

            Random random = new Random();
                for (long i =0 ; i<1000_000_000 ; i++) {

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                         // InterruptedException будет выброшен когда произойдет прерывание потока!
                        e.printStackTrace();
                    }

                    /** currentThread - возварщает текущий поток ,
                     isInterrupted передает информацию что токо прерван
                     таким образом прерывается поток
                     */
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("Поток прерван");
                        break;

                    }
                    Math.sin(random.nextInt(1000));
                }
        }
    });

        System.out.println("Поток запущен");
        thread.start();

        Thread.sleep(1000);
        thread.interrupt();

        thread.join();
        System.out.println("Поток окончен");




    }

}
