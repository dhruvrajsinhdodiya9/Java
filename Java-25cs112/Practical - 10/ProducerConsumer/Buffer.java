import java.util.LinkedList;

class Buffer {
    LinkedList<Integer> list = new LinkedList<>();
    int capacity = 3;

    synchronized void produce(int n) throws InterruptedException {
        while (list.size() == capacity) {
            wait();
        }

        list.addLast(n);
        System.out.println("Produced: " + n);
        notify();
    }

    synchronized int consume() throws InterruptedException {
        while (list.isEmpty()) {
            wait();
        }

        int n = list.removeFirst();
        System.out.println("Consumed: " + n);
        notify();

        return n;
    }
}