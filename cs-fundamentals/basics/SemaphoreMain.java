public class SemaphoreMain {
    public static void main(String[] args) {
        Resource r = new Resource();

        Thread p1 = new Thread(new Process1(r), "process-1");
        Thread p2 = new Thread(new Process2(r), "process-2");

        p1.start();
        p2.start();
    }
}

class Semaphore {
    private int S = 0; // semaphore = 0 (available) or > 0 (not available)

    public void acquire() throws InterruptedException {
        while (S > 0) { // wait if the resource is not available
            Thread.sleep(1);
        }

        S++; // make the resource acquired
    }

    public void release() {
        S--; // release the resource
    }
}

class Resource {
    private Semaphore s = new Semaphore();

    public void use(long time) throws InterruptedException { // critical section
        s.acquire();

        System.out.println("Using the resource from " + Thread.currentThread().getName());
        Thread.sleep(time); // simulate some work on the resource taking time to complete
        System.out.println("Finished using the resource from " + Thread.currentThread().getName());

        s.release();
    }
}

class Process1 implements Runnable {
    private Resource r;

    public Process1(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        System.out.println("Starting " + Thread.currentThread().getName());
        try {
            r.use(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class Process2 implements Runnable {
    private Resource r;

    public Process2(Resource r) {
        this.r = r;
    }

    @Override
    public void run() {
        System.out.println("Starting " + Thread.currentThread().getName());
        try {
            r.use(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}