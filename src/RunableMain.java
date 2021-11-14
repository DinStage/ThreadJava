public class RunableMain {

    public static void main(String[] args) {

        Thread thread = new Thread(new RunnableImpl());
        thread.start();
        System.out.println("hello1");
    }

}


class  RunnableImpl implements Runnable {

    @Override
    public void run() {
        for (int i = 0 ; i <1000 ; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("hello interface runnable");
        }

    }
}
