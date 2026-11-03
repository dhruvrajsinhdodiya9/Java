class ThreadPoolRunner implements Runnable {
    int id;

    ThreadPoolRunner(int id) {
        this.id = id;
    }

    public void run() {
        System.out.println("Task " + id + " running on "
                + Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println("Task " + id + " completed by "
                + Thread.currentThread().getName());
    }
}