public class ArrayDeque<T> {

    private int size;
    private T[] items;

    public ArrayDeque() {
        size = 0;
        items = (T[]) new Object[8];
    }

    public void addFirst(T item) {
        if (size == items.length-1) {
            this.reSize(size*2);
        }
        System.arraycopy(items, 0, items, 1, size);
        items[0] = item;
        size +=1;
    }

    public T removeFirst() {
        items[0] = null;
        System.arraycopy(items,1,items,0,size);
        size -=1;
        return items[0];
    }

    public void addLast(T item) {
        if (size == items.length-1) {
            this.reSize(size*2);
        }
        items[size] = item;
        size = size + 1;
    }

    public T removeLast() {
        items[size-1] = null;
        size -=1;
        return items[size-1];
    }

    public boolean isEmpty() {
        boolean check = true;
        if (size != 0){
            check = false;
        } return check;
    }

    public int size() {
        return size;
    }

    public void printDeque() {
        for (int i = 0; i<size; i++) {
            System.out.print(items[i]+" ");
        } System.out.println();
    }

    private void reSize (int capacity) {
        T[] a = (T[]) new Object[capacity];
        System.arraycopy(items, 0, a, 0, size);
        items = a;
    }

    public T get(int index) {
        return items [index];
    }
    
    public static void main (String[] args){
            ArrayDeque<Integer> L = new ArrayDeque();
            L.addLast(2);
            L.addLast(3);
            L.addFirst(1);
            L.printDeque();
            System.out.println(L.removeFirst());
            System.out.println(L.removeLast());
            System.out.println(L.size());
            System.out.println(L.get(0));
            //System.out.println(L.isEmpty());
    }

}
