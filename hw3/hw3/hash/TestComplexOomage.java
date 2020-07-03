package hw3.hash;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class TestComplexOomage {

    @Test
    public void testHashCodeDeterministic() {
        ComplexOomage so = ComplexOomage.randomComplexOomage();
        int hashCode = so.hashCode();
        for (int i = 0; i < 100; i += 1) {
            assertEquals(hashCode, so.hashCode());
        }
    }

    /* This should pass if your OomageTestUtility.haveNiceHashCodeSpread
       is correct. This is true even though our given ComplexOomage class
       has a flawed hashCode. */
    @Test
    public void testRandomOomagesHashCodeSpread() {
        List<Oomage> oomages = new ArrayList<>();
        int N = 10000;

        for (int i = 0; i < N; i += 1) {
            oomages.add(ComplexOomage.randomComplexOomage());
        }

        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(oomages, 10));
    }


    @Test
    public void testWithDeadlyParams() {

        List<Oomage> deadlyList = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            ComplexOomage comToAdd = new ComplexOomage(genIntReplyList(i, 9));
            deadlyList.add(comToAdd);
        }

        /*
        for (int i = 0; i < 1000; i += 1) {
            List<Integer> params = new ArrayList<>();

            for (int j = 0; j < 4; j += 1) { // Generates random integers for the first 32 bits
                params.add(StdRandom.uniform(255));
            }

            for (int j = 0; j < 4; j += 1) { // Add additional fixed 32 bits integers
                params.add(j);
            }

            deadlyList.add(new ComplexOomage(params));
        }

         */
        assertTrue(OomageTestUtility.haveNiceHashCodeSpread(deadlyList, 10));
    }

    private List<Integer> genIntReplyList(int listLen, int intInlist) {
        List<Integer> intRecur = new ArrayList<>(listLen);
        for (int i = 0; i < listLen; i++) {
            intRecur.add(intInlist);
        }
        return intRecur;
    }

    /** Calls tests for SimpleOomage. */
    public static void main(String[] args) {
        jh61b.junit.textui.runClasses(TestComplexOomage.class);
    }
}
