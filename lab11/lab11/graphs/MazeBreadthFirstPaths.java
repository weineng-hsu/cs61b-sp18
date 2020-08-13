package lab11.graphs;

import edu.princeton.cs.algs4.Queue;

/**
 *  @author Josh Hug
 */
public class MazeBreadthFirstPaths extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int s;
    private int t;
    Queue<Integer> fringe;


    public MazeBreadthFirstPaths(Maze m, int sourceX, int sourceY, int targetX, int targetY) {
        super(m);
        // Add more variables here!
        s = m.xyTo1D(sourceX, sourceY);
        t = m.xyTo1D(targetX, targetY);
        distTo[s] = 0;
        edgeTo[s] = s;
        fringe = new Queue<>();
        fringe.enqueue(s);
        marked[s] = true;
    }

    /** Conducts a breadth first search of the maze starting at the source. */
    private void bfs() {
        while (!fringe.isEmpty()) {
            int cur = fringe.dequeue();
            for (int nearBy : maze.adj(cur)) {
                if (!marked[nearBy]) {
                    announce();
                    edgeTo[nearBy] = cur;
                    distTo[nearBy] = distTo[cur] + 1;
                    marked[nearBy] = true;
                    fringe.enqueue(nearBy);
                }
            }
            if (cur == t) {
                return;
            }
        }
    }


    @Override
    public void solve() {
        bfs();
    }
}

