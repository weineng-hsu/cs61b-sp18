import org.junit.Test;
import static org.junit.Assert.*;

public class TestPalindrome {
    // You must use this palindrome, and not instantiate
    // new Palindromes, or the autograder might be upset.
    static Palindrome palindrome = new Palindrome();
    static CharacterComparator cc1 = new OffByOne();
    static CharacterComparator cc3 = new OffByN(3);

    @Test
    public void testWordToDeque() {
        Deque d = palindrome.wordToDeque("persiflage");
        String actual = "";
        for (int i = 0; i < "persiflage".length(); i++) {
            actual += d.removeFirst();
        }
        assertEquals("persiflage", actual);
    } /*Uncomment this class once you've created your Palindrome class. */

    @Test
    public void testIsPalindrome() {
        String a = "cat";
        String b = "a";
        String c = "racer";
        String d = "aabaa";
        String e = "";
        String f = "yucky";
        //boolean exp = palindrome.isPalindrome(d);
        assertFalse(palindrome.isPalindrome(a));
        assertTrue(palindrome.isPalindrome(b));
        //boolean cExp = palindrome.isPalindrome(c);
        assertFalse(palindrome.isPalindrome(c));
        //boolean dExp = palindrome.isPalindrome(d);
        assertTrue(palindrome.isPalindrome(d));
        //boolean eExp = palindrome.isPalindrome(e);
        assertTrue(palindrome.isPalindrome(e));
        //boolean fExp = palindrome.isPalindrome(f);
        assertFalse(palindrome.isPalindrome(f));
    }

    @Test
    public void newOffByOne() {
        String a = "flake";
        String b = "frake";
        String c = "a";
        String d = "aab";
        //Boolean aEXP = palindrome.isPalindrome(a,cc1);
        assertTrue(palindrome.isPalindrome(a, cc1));
        assertFalse(palindrome.isPalindrome(b, cc1));
        assertTrue(palindrome.isPalindrome(c, cc1));
        assertTrue(palindrome.isPalindrome(d, cc1));
    }

    @Test
    public void newOffByN() {
        String a = "abed";
        String b = "frake";
        String c = "a";
        String d = "";
        assertTrue(palindrome.isPalindrome(a, cc3));
        assertFalse(palindrome.isPalindrome(b, cc3));
        assertTrue(palindrome.isPalindrome(c, cc3));
        assertTrue(palindrome.isPalindrome(d, cc3));
    }
}
