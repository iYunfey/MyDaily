public class ThreadSortTest {

    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2");
        });
        Thread thread3 = new Thread(() -> {
            System.out.println("thread3");
        });
        thread1.start();
        System.out.println("1111111111111111-start");
        thread1.join();
        System.out.println("1111111111111111-end");
        thread2.start();
        System.out.println("2222222222222222-start");
        thread2.join();
        System.out.println("2222222222222222-end");
        thread3.start();
        System.out.println("3333333333333333-start");
        thread3.join();
        System.out.println("3333333333333333-end");
    }
}
