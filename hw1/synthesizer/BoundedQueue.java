package synthesizer;
import java.util.Iterator;

public interface BoundedQueue<T> extends Iterable<T> {
    int capacity();
    int fillCount();
    void enqueue(T x);
    T dequeue();
    T peek();

    default boolean isEmpty() {
        if (fillCount() != 0) {
            return false;
        }
        return true;
    }

    default boolean isFull() {
        if (fillCount() != capacity()) {
            return false;
        }
        return true;
    }

    Iterator<T> iterator();

}
