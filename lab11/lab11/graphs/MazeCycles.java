package lab11.graphs;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Random;

/**
 *  @author Josh Hug
 */
public class MazeCycles extends MazeExplorer {
    /* Inherits public fields:
    public int[] distTo;
    public int[] edgeTo;
    public boolean[] marked;
    */
    private int[] cameFrom;
    private boolean foundCircle = false;

    public MazeCycles(Maze m) {
        super(m);
    }

    private void dfs(int v) {
        if (foundCircle) {
            return;
        }

        for (int near: maze.adj(v)) {
            if (!marked[near]) {
                edgeTo[near] = v;
                marked[near] = true;
                dfs(near);
            } else {
                if (near != edgeTo[v]) {
                    edgeTo[near] = v;
                    foundCircle = true;
                    if (foundCircle) {
                        return;
                    }
                }
            }
            if (foundCircle) {
                return;
            }
        }
    }

    @Override
    public void solve() {

        /* Serves like `edgeTo`, created because I don't want to use `edgeTo` until circle found */
        cameFrom = new int[maze.V()];

        /* Set point where circle search starts */
        Random rand = new Random();
        int startX = rand.nextInt(maze.N());
        int startY = rand.nextInt(maze.N());
        int s = maze.xyTo1D(startX, startY);
        marked[s] = true;
        edgeTo[s] = s;
        dfs(s);
        announce();             // Render the results of DFS
    }
}
