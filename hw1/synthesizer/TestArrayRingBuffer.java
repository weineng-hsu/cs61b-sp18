package synthesizer;
import org.junit.Test;
import static org.junit.Assert.*;

/** Tests the ArrayRingBuffer class.
 *  @author Josh Hug
 */

public class TestArrayRingBuffer {
    @Test
    public void someTest() {
        ArrayRingBuffer arb = new ArrayRingBuffer(4);
        arb.enqueue(33.1);
        //arb.dequeue();
        assertEquals(arb.dequeue(),33.1);
        arb.enqueue(33.1);
        arb.enqueue(33.2);
        arb.enqueue(33.3);
        assertEquals(arb.dequeue(),33.1);
        arb.enqueue(33.4);
        assertEquals(arb.peek(),33.2);
        arb.enqueue(33.5);
        //arb.enqueue(33.6);
        //arb.enqueue(33.7);
    }

    /** Calls tests for ArrayRingBuffer. */

    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestArrayRingBuffer.class);
    }
} 
