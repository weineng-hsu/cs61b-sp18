import org.junit.Test;
import static org.junit.Assert.*;

public class TestOffByN {
    static CharacterComparator offBy1 = new OffByN(1);
    static CharacterComparator offBy2 = new OffByN(2);
    static CharacterComparator offBy0 = new OffByN(0);

    @Test
    public void testOffBy1() {
        assertTrue(offBy1.equalChars('a', 'b'));
        assertFalse(offBy1.equalChars('a', 'a'));
        assertFalse(offBy1.equalChars('a', 'g'));
        assertTrue(offBy1.equalChars('m', 'n'));
        assertFalse(offBy2.equalChars('a', 'B'));
    }

    @Test
    public void testOffBy2() {
        assertTrue(offBy2.equalChars('a', 'c'));
        assertFalse(offBy2.equalChars('a', 'a'));
        assertFalse(offBy2.equalChars('a', 'g'));
        assertTrue(offBy2.equalChars('m', 'o'));
        assertFalse(offBy2.equalChars('a', 'B'));
    }

    @Test
    public void testOffBy0() {
        assertTrue(offBy0.equalChars('a', 'a'));
        assertFalse(offBy0.equalChars('a', 'b'));
        assertFalse(offBy0.equalChars('a', 'g'));
        assertTrue(offBy0.equalChars('m', 'm'));
        assertFalse(offBy0.equalChars('a', 'B'));
    }
}
