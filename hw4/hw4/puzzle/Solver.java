package hw4.puzzle;

import edu.princeton.cs.algs4.BinaryIn;
import edu.princeton.cs.algs4.MinPQ;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;


public class Solver {

    protected class SearchNode {
        SearchNode previous;
        WorldState node;
        int moves;
        Integer priority;

        private SearchNode(WorldState word, SearchNode prev) {
            node = word;
            previous = prev;
            moves = prev == null ? 0 : prev.moves + 1;
            if (priorityCash.containsKey(word)) {
                priority = priorityCash.get(word) + moves ;
            } else {
                int distance = word.estimatedDistanceToGoal();
                priorityCash.put(word, distance);
                priority = distance + moves;
            }
        }
    }

    public class SearchNodeComparator implements Comparator<Solver.SearchNode> {
        @Override
        public int compare(Solver.SearchNode searchNode, Solver.SearchNode t1) {
            return searchNode.priority.compareTo(t1.priority);
        }

    }

    private Map<WorldState, Integer> priorityCash = new HashMap<>();
    private Deque<WorldState> path = new ArrayDeque<>();
    private SearchNode currNode;

    public Solver(WorldState Initial) {
        currNode = new SearchNode(Initial, null);
        MinPQ<SearchNode> queueToSearch = new MinPQ<>(new SearchNodeComparator());

        while (!currNode.node.isGoal()) {
            for (WorldState nearby: currNode.node.neighbors()) {
                if (currNode.previous == null || !nearby.equals(currNode.previous.node)) {
                    SearchNode toAddNode = new SearchNode(nearby, currNode);
                    queueToSearch.insert(toAddNode);
                }
            }
            currNode = queueToSearch.delMin();
        }

        while (currNode != null) {
            path.push(currNode.node);
            currNode = currNode.previous;
        }
    }

    public int moves() {
        return path.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return path;
    }

}