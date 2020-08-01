package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayDeque;


public class Solver {

    private class SearchNode {
        private SearchNode previous;
        private WorldState node;
        private int moves;
        private Integer priority;

        private SearchNode(WorldState word, SearchNode prev) {
            node = word;
            previous = prev;
            moves = prev == null ? 0 : prev.moves + 1;
            if (priorityCash.containsKey(word)) {
                priority = priorityCash.get(word) + moves;
            } else {
                int distance = word.estimatedDistanceToGoal();
                priorityCash.put(word, distance);
                priority = distance + moves;
            }
        }
    }

    private class SearchNodeComparator implements Comparator<SearchNode> {
        @Override
        public int compare(Solver.SearchNode searchNode, Solver.SearchNode t1) {
            return searchNode.priority.compareTo(t1.priority);
        }

    }

    private Map<WorldState, Integer> priorityCash = new HashMap<>();
    private Deque<WorldState> path = new ArrayDeque<>();
    private SearchNode curNod;

    public Solver(WorldState initial) {
        curNod = new SearchNode(initial, null);
        MinPQ<SearchNode> qToSearch = new MinPQ<>(new SearchNodeComparator());

        while (!curNod.node.isGoal()) {
            for (WorldState nearby: curNod.node.neighbors()) {
                if (curNod.previous == null
                        || !nearby.equals(curNod.previous.node)) {
                    SearchNode toAddNode = new SearchNode(nearby, curNod);
                    qToSearch.insert(toAddNode);
                }
            }
            curNod = qToSearch.delMin();
        }

        while (curNod != null) {
            path.push(curNod.node);
            curNod = curNod.previous;
        }
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return path;
    }
}
