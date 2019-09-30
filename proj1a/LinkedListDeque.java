public class LinkedListDeque <T> {

    private class stufNode {
        public T item;
        public stufNode prev;
        public stufNode next;

        public stufNode(T i, stufNode m, stufNode n) {
            item = i;
            prev = m;
            next = n;
        }
    }

    private stufNode sentinel;
    private int size;

    /** check linked list deque is empty or not
     * @return Return true if the list is empty, otherwise false.
     */
    public boolean isEmpty() {
        boolean check = true;
        if (this.size != 0) {
            check = false;
        } return check;
    }

    /** Create a empty LLD.
     *
     */
    public LinkedListDeque() {
        sentinel = new stufNode(null,null,null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /**Add a item in front of the LLD.
     * @param item is the added item.
     */
    public void addFirst(T item) {
        sentinel.next = new stufNode(item, sentinel, sentinel.next);
        sentinel.next.next.prev=sentinel.next;
        size = size + 1;
    }

    /**Remove the first item in the LLD, and also return the item in the front.
     * @return T next to the removed item.
     */
    public T removeFirst() {
        stufNode pt1 = sentinel;
        T first = pt1.next.item;
        pt1.next.prev = null;
        pt1.next.next.prev = sentinel;
        pt1.next = pt1.next.next;
        size -=1;
        return first;
    }

    /** Add item into the LLD in the end.
     * @param item T is added at the end of the LLD.
     */
    public void addLast(T item) {
        sentinel.prev = new stufNode(item, sentinel.prev, sentinel);
        sentinel.prev.prev.next = sentinel.prev;
        size = size + 1;
    }

    /** Remove the end of the LLD and also return the newest item in the end.
     * @return the T item in the end of LLD.
     */
    public T removeLast() {
        stufNode pt1 = sentinel;
        T Last = pt1.prev.item;
        pt1.prev.prev.next = sentinel;
        pt1.prev = pt1.prev.prev;
        size -= 1;
        return Last;
    }

    /** Return the size of the LLD.
     * If LLD is empty, return 0.
     * @return the size of the LLD.
     */
    public int size() {
        if (size < 0) {
            return 0;
        }
        return size;
    }

    /** print out all the item in the LLD.
     * print out all the item in the LLD with a space.
     */
    public void printDeque() {
        stufNode pt1 = sentinel;
        for (int i = 0; i < size; i++) {
            pt1 = pt1.next;
            System.out.print(pt1.item+" ");
        }
    }

    /** Use iterative way to get the item in the LLD.
     *
     * @param index Give the nth item in the LLD.
     * @return the nth item in the LLD.
     */
    public T get(int index) {
        stufNode pt1 = sentinel;
        for (int i = 0; i < index; i++) {
            pt1 = pt1.next;
        }return pt1.item;
    }

    /** Use recursive way to get the item in the LLD.
     *
     * @param index Give the nth item in the LLD.
     * @return the nth item in the LLD.
     */
    public T getRecursive(int index) {
        if (index == 0) {
            return sentinel.item;
        }
        return getRecursiveHelper(sentinel,index);
    }

    /** To help getRecursive method.
     *
     * @param m shrink as the n goes down.
     * @param n indicator for the remaining shrinking times.
     * @return the nth item in the LLD.
     */
    private T getRecursiveHelper(stufNode m, int n) {
        if (n == 0) {
            return m.item;
        }return getRecursiveHelper(m.next,n-1);
    }

    /**Comment out for uploading to autograder
    public static void main(String[] args) {
        LinkedListDeque<Integer> L  = new LinkedListDeque();

        L.addFirst(2);
        L.addFirst(3);
        L.addLast(1);
        System.out.println(L.removeFirst());
        System.out.println(L.removeLast());
        System.out.println(L.removeLast());

        L.addLast(3);
        L.addFirst(1);
        System.out.println(L.size());
        System.out.println(L.get(1));
        System.out.println(L.getRecursive(1));
        L.printDeque();
    }*/

}