import java.util.Deque;
import java.util.concurrent.ConcurrentLinkedDeque;


public class ThreadPool {

    private Deque<Runnable> tasks;
    private PoolWorker threads[];

    public ThreadPool(int threadsCount) {
        this.tasks = new ConcurrentLinkedDeque<>();
        this.threads = new PoolWorker[threadsCount];

        for (int i = 0; i < this.threads.length; i++) {
            this.threads[i] = new PoolWorker();
            this.threads[i].start();
        }
    }

    public void submit(Runnable task) {
        synchronized (tasks) {
            tasks.add(task);
            tasks.notify();
        }
    }

    private class PoolWorker extends Thread {
        @Override
        public void run() {
            Runnable task;
            while (true) {
                synchronized (tasks) {
                    while (tasks.isEmpty()) {
                        try {
                            tasks.wait();
                        } catch (InterruptedException e) {
                            throw new IllegalArgumentException(e);
                        }
                    }
                    task = tasks.poll();
                }

                try {
                    task.run();
                } catch (RuntimeException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

    }
}