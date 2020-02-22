import java.util.ArrayList;
import java.util.List;

public class SynchronizedQueue<T> {

    private final int maxLength;
    private final List<T> queue;

    public SynchronizedQueue(int maxLength) {
        this.maxLength = maxLength;
        queue = new ArrayList<>(maxLength);
    }

    public synchronized int size() {
        return queue.size();
    }

    public synchronized boolean isEmpty() {
        return size() == 0;
    }

    public synchronized boolean isMaxLength() {
        return size() == maxLength;
    }

    public synchronized T get() {
        return queue.remove(0);
    }

    public synchronized boolean put(T item) {
        if (isMaxLength()) {
            return false;
        }
        return queue.add(item);
    }
}
