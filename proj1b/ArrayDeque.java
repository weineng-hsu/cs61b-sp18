//import java.util.Queue;
/**
 * Array based linked data structure and it is created to be Circular.
 * @Rule: Add and remove must take constant time, except during resizing operations.
 * @Rule: Start with a 8 size of array.
 * @Rule: should also shrink when array is too big.
 */
public class ArrayDeque<T> implements Deque<T> {

    private int size;           //The size of the array.
    private T[] items;
    private int nextFirst;      //Pointer to the first of the array.
    private int nextLast;       //Pointer to the last of the array.
    private int arraySize;      //shows to size of the array.
    private int printPointer;   //Pointer to print out all the items in the array.
    private int getPointer;     //Pointer to get method.

    /** Create an empty array, size of 8.*/
    public ArrayDeque() {
        size = 0;
        nextFirst = 3;
        nextLast = 4;
        items = (T[]) new Object[8];
        arraySize = 8;
    }

    /** Add item into the array at the end.
     * @param item the item add into the list.
     */
    @Override
    public void addFirst(T item) {
        checkExpand(arraySize * 2);
        items[nextFirst] = item;
        nextFirst = minusOne(nextFirst);
        size += 1;
    }

    /** Remove item into the array at the end.*/
    @Override
    public T removeFirst() {
        T first = items[plusOne(nextFirst)];
        items[plusOne(nextFirst)] = null;
        size -= 1;
        nextFirst = plusOne(nextFirst);
        checkShrink(arraySize * 1 / 2);
        return  first;
    }

    /** Add item into the array at front.
     * @param item the item add into the list.
     */
    @Override
    public void addLast(T item) {
        checkExpand(arraySize * 2);
        items[nextLast] = item;
        nextLast = plusOne(nextLast);
        size += 1;
    }

    /** Remove item into the array at the front.*/
    @Override
    public T removeLast() {
        T first = items[minusOne(nextLast)];
        items[minusOne(nextLast)] = null;
        size -= 1;
        nextLast = minusOne(nextLast);
        checkShrink(arraySize * 1 / 2);
        return  first;
    }

    /** Check if the array is empty.
     *
     * @return false when array is not empty.
     */
    @Override
    public boolean isEmpty() {
        boolean check = true;
        if (size != 0) {
            check = false;
        }
        return check;
    }

    /*Return the size of the array.*/
    @Override
    public int size() {
        if (size < 0) {
            size = 0;
        }
        return size;
    }

    /* Prints the items in the deque from first to last, separated by a space.*/
    @Override
    public void printDeque() {
        printPointer = nextFirst;
        for (int i = 0; i < size; i++) {
            System.out.print(items[plusOne(printPointer)] + " ");
            printPointer = plusOne(printPointer);
        } System.out.println();
    }

    /** Expand the array by two times when only one space left.*/
    private void checkExpand(int capacity) {
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
    /* Shrink the array by half when half of the array is left.*/
    private void checkShrink(int capacity) {
        if (arraySize / 2 > size & arraySize > 8) {
            T[] a = (T[]) new Object[capacity];
            if (nextFirst > nextLast) {
                System.arraycopy(items, plusOne(nextFirst),
                        a, 0, items.length - plusOne(nextFirst));
                System.arraycopy(items, 0, a, items.length - plusOne(nextFirst),
                        size - items.length + plusOne(nextFirst));
                items = a;
                arraySize = arraySize / 2;
                nextFirst = minusOne(0);
                nextLast = size;
            }
            if (nextFirst < nextLast) {
                System.arraycopy(items, plusOne(nextFirst), a, 0, size);
                items = a;
                arraySize = arraySize / 2;
                nextFirst = minusOne(0);
                nextLast = size;
            }
        }
    }

    /**Returns the index-th items in the array
     *
     * @param index the ith items in the array.
     */
    @Override
    public T get(int index) {
        getPointer = plusOne(nextFirst) + index;
        if (getPointer >= items.length) {
            getPointer = getPointer - arraySize;
        }
        return items[getPointer];
    }

    /* Deal with the nextFirst/nextLast pointer when pointer decrease. */
    private int minusOne(int index) {
        index = index - 1;
        if (index < 0) {
            index = arraySize - 1;
        }
        return index;
    }
    /* Deal with the nextFirst/nextLast pointer when pointer increase. */
    private int plusOne(int index) {
        index = index + 1;
        if (index > arraySize - 1) {
            index = 0;
        }
        return index;
    }

/**Uncomment for autoGrader.
 public static void main (String[] args){
 ArrayDeque<Integer> L = new ArrayDeque();
 L.addLast(1);
 L.addLast(2);
 L.addLast(3);
 L.addLast(4);
 L.addLast(5);
 L.addLast(6);
 L.addLast(7);
 L.addLast(1);
 L.addLast(2);
 L.addLast(3);
 L.addLast(4);
 L.addLast(5);
 L.addLast(6);
 L.addLast(7);
 L.addLast(1);
 L.addLast(2);
 L.addLast(3);
 L.addLast(4);
 L.addLast(5);
 L.addLast(6);
 L.addLast(7);
 //L.addFirst(1);
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeLast();
 L.removeFirst();
 L.removeFirst();
 L.removeFirst();
 L.removeFirst();
 L.addFirst(1);
 L.addFirst(1);
 L.addFirst(1);
 L.addFirst(1);
 L.addFirst(1);
 L.addFirst(1);
 L.removeFirst();
 L.printDeque();
 System.out.println(L.removeFirst());
 System.out.println(L.removeLast());
 //System.out.println(L.size());
 //System.out.println(L.isEmpty());
 }*/
}