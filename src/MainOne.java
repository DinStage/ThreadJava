import java.util.Scanner;

/*
* @ Пример использования конструкции volataile
* */
public class MainOne {
    public static void main(String[] args) throws InterruptedException {
        MyThread thread = new MyThread();
        thread.start();

        Scanner scanner
                = new Scanner(System.in);
        scanner.nextLine();

        thread.shuutdown();

    }
}


class MyThread extends  Thread {
    private static int i = 0;
    volatile  private  boolean running = true;

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println("helo world " + i);
            i++;
        }
    }
    public  void  shuutdown() {
        this.running = false;
    }
}