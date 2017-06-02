package advance.codeComprators.greedyStringTiling;

import java.util.*;

/**
 * Supporting data structure for Greedy String Tiling algorithm.
 * Represents Hash Map, where for each KEY there is a list of corresponding objects
 */
public class GSTHashMap {

    /**
     * Stores data for GSTHashMap.
     * KEY represents hash of substring
     * VALUE represents list of substrings with hash, stored in KEY (their start indexes are stored)
     */
    private final Map<Long, List<Integer>> data;

    public GSTHashMap() {
        data = new HashMap<>();
    }

    /**
     * Stores new 'index' for key 'hash' in a data.
     * If there is already objects for the key 'hash' -> 'index' is appended
     */
    public void add(long hash, int index) {
        if (data.containsKey(hash)) {
            data.get(hash).add(index);
        } else {
            final List<Integer> list = new ArrayList<>();
            list.add(index);
            data.put(hash, list);
        }
    }

    public List<Integer> get(long hash) {
        return data.getOrDefault(hash, new LinkedList<>());
    }

    public void clear() {
        data.clear();
    }
}
