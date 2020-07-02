package hw2;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int N;
    private int T;
    private double[] thresholds;

    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        for (int i = 0; i < T; i++) {
            Percolation grids = pf.make(N);
            while (!grids.percolates()) {
                int ranX = StdRandom.uniform(N);
                int ranY = StdRandom.uniform(N);
                if (!grids.isOpen(ranX, ranY)) {
                    grids.open(ranX, ranY);
                }
            }
            double threshold = grids.numberOfOpenSites() / (N * N);
            thresholds[i] = threshold;
        }
    }

    public double mean() {
        return StdStats.mean(thresholds);
    }

    public double stddev() {
        return StdStats.stddev(thresholds);
    }

    public double confidenceLow() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }

    public double confidenceHigh() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
}
