package dis_pr_4_1;

public class PR1 {
    public static void main(String[] args) {
        Thread1 t1 = new Thread1();
        Thread2 t2 = new Thread2();
        Thread thr1 = new Thread(t1);
        Thread thr2 = new Thread(t2);
        thr1.start();
        thr2.start();
        
        try {
            thr1.join();
            thr2.join();
            System.out.println("Both threads have finished");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class Thread1 implements Runnable {

    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(1000);
                System.out.println("HELLO WORLD!");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}

class Thread2 implements Runnable {

    public void run() {
        try {
            for (int i = 0; i < 3; i++) {
                Thread.sleep(2000);
                System.out.println("HOW ARE YOU?");
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
