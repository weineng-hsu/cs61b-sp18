public class LinkedListDeque<T> implements Deque<T> {

    private class StufNode {
        private T item;
        private StufNode prev;
        private StufNode next;

        public StufNode(T i, StufNode m, StufNode n) {
            item = i;
            prev = m;
            next = n;
        }
    }

    private StufNode sentinel;
    private int size;

    /** check linked list deque is empty or not
     * @return Return true if the list is empty, otherwise false.
     */
    @Override
    public boolean isEmpty() {
        boolean check = true;
        if (this.size != 0) {
            check = false;
        }
        return check;
    }

    /** Create a empty LLD.
     *
     */
    public LinkedListDeque() {
        sentinel = new StufNode(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**Add a item in front of the LLD.
     * @param item is the added item.
     */
    @Override
    public void addFirst(T item) {
        sentinel.next = new StufNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev = sentinel.next;
        size = size + 1;
    }

    /**Remove the first item in the LLD, and also return the item in the front.
     * @return T next to the removed item.
     */
    @Override
    public T removeFirst() {
        StufNode pt1 = sentinel;
        T first = pt1.next.item;
        pt1.next.prev = null;
        pt1.next.next.prev = sentinel;
        pt1.next = pt1.next.next;
        //sentinel.next=sentinel.next.next;
        //sentinel.next.prev.prev=null;
        //sentinel.next.prev.next=null;
        //sentinel.next.prev=sentinel;
        size -= 1;
        return first;
    }

    /** Add item into the LLD in the end.
     * @param item T is added at the end of the LLD.
     */
    @Override
    public void addLast(T item) {
        sentinel.prev = new StufNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size = size + 1;
    }

    /** Remove the end of the LLD and also return the newest item in the end.
     * @return the T item in the end of LLD.
     */
    @Override
    public T removeLast() {
        StufNode pt1 = sentinel;
        T last = pt1.prev.item;
        pt1.prev.prev.next = sentinel;
        pt1.prev = pt1.prev.prev;
        size -= 1;
        return last;
    }

    /** Return the size of the LLD.
     * If LLD is empty, return 0.
     * @return the size of the LLD.
     */
    @Override
    public int size() {
        if (size < 0) {
            return 0;
        }
        return size;
    }

    /** print out all the item in the LLD.
     * print out all the item in the LLD with a space.
     */
    @Override
    public void printDeque() {
        StufNode pt1 = sentinel;
        for (int i = 0; i < size; i++) {
            pt1 = pt1.next;
            System.out.print(pt1.item + " ");
        }
    }

    /** Use iterative way to get the item in the LLD.
     *
     * @param index Give the nth item in the LLD.
     * @return the nth item in the LLD.
     */
    @Override
    public T get(int index) {
        StufNode pt1 = sentinel;
        for (int i = 0; i < index + 1; i++) {
            pt1 = pt1.next;
        } return pt1.item;
    }

    /** Use recursive way to get the item in the LLD.
     *
     * @param index Give the nth item in the LLD.
     * @return the nth item in the LLD.
     */
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.next.item;
        }
        return getRecursiveHelper(sentinel, index);
    }

    /** To help getRecursive method.
     *
     * @param m shrink as the n goes down.
     * @param n indicator for the remaining shrinking times.
     * @return the nth item in the LLD.
     */
    private T getRecursiveHelper(StufNode m, int n) {

        return getRecursiveHelper(m.next, n - 1);
    }

    /**Comment out for uploading to autograder
    public static void main(String[] args) {
        LinkedListDeque<Integer> L  = new LinkedListDeque();

        L.addFirst(0);
        L.addLast(1);
        L.addLast(2);
        L.removeFirst();
        L.removeFirst();
        //System.out.println(L.removeFirst());
        //L.getRecursive(1);
        //System.out.println(L.getRecursive(1));
        L.addFirst(1);
        L.addFirst(0);
        //L.getRecursive(1);
        //System.out.println(L.getRecursive(1));
        //System.out.println(L.removeLast());
        //System.out.println(L.removeLast());
        //L.addLast(3);
        //L.addFirst(1);
        //System.out.println(L.size());
        System.out.println(L.get(0));
        System.out.println(L.getRecursive(0));
        //L.printDeque();
    }*/
}
