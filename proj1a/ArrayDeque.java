public class ArrayDeque<T> {

    private int size;
    private T[] items;
    private int nextFirst;
    private int nextLast;
    private int arraySize;
    private int pointer;

    public ArrayDeque() {
        size = 0;
        nextFirst = 3;
        nextLast = 4;
        items = (T[]) new Object[8];
        arraySize = 8;
    }

    public void addFirst(T item) {
        checkExpand(arraySize * 2);
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    public T removeFirst() {
        T First = items[plusOne(nextFirst)];
        items[plusOne(nextFirst)] = null;
        size -= 1;
        nextFirst = plusOne(nextFirst);
        checkShrink(arraySize * 1/2);
        return  First;
        /**
        if (nextFirst == arraySize - 1) {
            T First = items[0];
            items[0] = null;
            size -= 1;
            nextFirst = plusOne(nextFirst);
            checkShrink(arraySize * 1/2);
            return First;
        }
        T First = items[nextFirst + 1];
        items[nextFirst + 1] = null;
        size -= 1;
        nextFirst = plusOne(nextFirst);
        checkShrink(arraySize * 1/2);
        return First;*/
    }

    public void addLast(T item) {
        checkExpand(arraySize*2);
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    public T removeLast() {
        T First = items[minusOne(nextLast)];
        items[minusOne(nextLast)] = null;
        size -= 1;
        nextLast = minusOne(nextLast);
        checkShrink(arraySize * 1/2);
        return  First;
        /**
        if (nextLast == 0) {
            T Last = items[7];
            items[7] = null;
            size -= 1;
            nextLast = minusOne(nextLast);
            checkShrink(arraySize * 1/2);
            return Last;
        }
        T Last = items[nextLast - 1];
        items[nextLast - 1] = null;
        size -= 1;
        nextLast = minusOne(nextLast);
        checkShrink(arraySize * 1/2);
        return Last;*/
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
        pointer = nextFirst;
        for (int i = 0; i < size; i++) {
            System.out.print(items[plusOne(pointer)]+" ");
            pointer = plusOne(pointer);
        } System.out.println();
    }

    private void checkExpand (int capacity) {
        if (nextFirst == nextLast) {
            T[] a = (T[]) new Object[capacity];
            System.arraycopy(items, nextFirst + 1, a, capacity / 4, items.length - nextFirst - 1);
            System.arraycopy(items, 0, a, capacity / 4 + items.length - nextFirst - 1, nextFirst);
            items = a;
            nextFirst = (capacity / 4) - 1;
            nextLast = nextFirst + size + 1;
            arraySize = arraySize * 2;
        }
    }

    private void checkShrink (int capacity) {
        if (arraySize / 4 > size & arraySize > 8) {
            T[] a = (T[]) new Object[capacity];
            if (nextFirst > nextLast) {
                System.arraycopy(items, nextFirst + 1, a, capacity / 4, items.length - nextFirst - 1);
                System.arraycopy(items, 0, a, capacity / 4 + items.length - nextFirst - 1, nextLast - 1);
                items = a;
                nextFirst = (capacity / 4) - 1;
                nextLast = nextFirst + size + 1;
            }
            if (nextFirst < nextLast) {
                System.arraycopy(items, nextFirst + 1, a, capacity / 4, size);
                items = a;
                nextFirst = (capacity / 4) - 1;
                nextLast = nextFirst + size + 1;
            }
        }
    }

    public T get(int index) {
        return items [index];
    }

    public int minusOne(int index) {
        index = index - 1;
        if (index < 0) {
            index = arraySize - 1;
        }return index;
    }

    public int plusOne(int index) {
        index = index + 1;
        if (index > arraySize - 1) {
            index = 0;
        } return index;
    }

/**Uncomment for autoGrader.
    public static void main (String[] args){
        ArrayDeque<Integer> L = new ArrayDeque();
        //L.addLast(2);
        //L.addLast(3);
        //L.addFirst(1);
        L.addFirst(1);
        L.addFirst(2);
        L.addFirst(3);
        L.addFirst(4);
        L.addFirst(5);
        L.addFirst(6);
        //L.removeFirst();
        //L.removeFirst();
        L.addLast(0);
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.addLast(-1);
        L.addLast(-2);
        L.addLast(-3);
        L.addLast(-4);
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeFirst();
        L.removeLast();
        L.removeLast();
        L.removeLast();
        L.addLast(-6);
        L.addLast(-7);
        L.removeLast();
        L.removeFirst();
        L.printDeque();
        //System.out.println(L.removeFirst());
        //System.out.println(L.removeLast());
        //System.out.println(L.size());
        //System.out.println(L.get(9));
        //System.out.println(L.isEmpty());
    }*/

}