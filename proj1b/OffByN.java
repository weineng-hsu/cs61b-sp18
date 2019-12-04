public class OffByN implements CharacterComparator {
    private int count;

    public OffByN(int n) {
        count = n;
    }

    @Override
    public boolean equalChars(char x, char y) {
        int a = x;
        int b = y;
        int c = count;
        if (Math.abs(a-b) == c) {
            return true;
        } return false;
    }
    /**public static void main (String[] args) {
        OffByN offBy5 = new OffByN(5);
        offBy5.equalChars('a', 'f');
        System.out.println(offBy5.equalChars('a', 'f'));
    }*/
}