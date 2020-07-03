package hw3.hash;

import edu.princeton.cs.algs4.In;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        /* TODO:
         * Write a utility function that returns true if the given oomages
         * have hashCodes that would distribute them fairly evenly across
         * M buckets. To do this, convert each oomage's hashcode in the
         * same way as in the visualizer, i.e. (& 0x7FFFFFFF) % M.
         * and ensure that no bucket has fewer than N / 50
         * Oomages and no bucket has more than N / 2.5 Oomages.
         */
        HashMap<Integer, Integer> oBuckets = new HashMap();
        for (Oomage oomage: oomages) {
            int ooHash = (oomage.hashCode() & 0x7FFFFFFF) % M;
            if (!oBuckets.containsKey(ooHash)) {
                oBuckets.put(ooHash, 1);
            } else {
                oBuckets.put(ooHash, oBuckets.get(ooHash) + 1);
            }
        }
        for (Integer buckId: oBuckets.keySet()) {
            if (oBuckets.get(buckId) <= (double)oomages.size() / 50) {
                return false;
            }
            if (oBuckets.get(buckId) > (double)oomages.size() / 2.5) {
                return false;
            }
        }
        return true;

    }
}
