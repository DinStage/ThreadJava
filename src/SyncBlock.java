import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SyncBlock {
    private int counter;

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        worker.main();
    }
}

class Worker {
    Random random = new Random();
    Object lock1 = new Object(); // блок элемента
    Object lock2 = new Object();

    // Тут происходит последовательная запись в 2 листа так как используется синхронизация потоков
    // для отказа от лишней работы исползуется элемент лок который по очереди выполняет блокировку

   private List<Integer> list1 = new ArrayList<Integer>();
   private List<Integer> list2 = new ArrayList<Integer>();
   public void  addToList1() {
       synchronized (lock1) {


           try {
               Thread.sleep(1);

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           list2.add(random.nextInt(100));
       }

   }
   public  void  addToList2(){
       synchronized (lock2) {

           try {
               Thread.sleep(1);

           } catch (InterruptedException e) {
               e.printStackTrace();
           }
           list1.add(random.nextInt(100));
       }

   };
   public  void work(){
       for ( int i = 0 ; i <1000 ; i ++) {
           addToList1();
           addToList2();
       }


   }

    public void  main () throws InterruptedException {
        long before = System.currentTimeMillis();
            Thread thread1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    work();
                }
            })   ;
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        })   ;

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long after = System.currentTimeMillis();
        System.out.println("program to run  (ms): " + (after - before));
        System.out.println(list1.size());
        System.out.println(list2.size());

    }
}