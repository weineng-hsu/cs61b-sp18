package hw3.hash;


import java.util.HashMap;
import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
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
            if (oBuckets.get(buckId) <= (double) oomages.size() / 50) {
                return false;
            }
            if (oBuckets.get(buckId) > (double) oomages.size() / 2.5) {
                return false;
            }
        }
        return true;

    }
}
