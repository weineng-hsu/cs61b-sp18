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
        this.N = N;
        this.T = T;
        thresholds = new double[T];

        for (int i = 0; i < T; i++) {
            Percolation grids = pf.make(N);
            while (!grids.percolates()) {
                int ranX = StdRandom.uniform(N);
                int ranY = StdRandom.uniform(N);
                if (!grids.isOpen(ranX, ranY)) {
                    grids.open(ranX, ranY);
                }
            }
            double threshold = (double) grids.numberOfOpenSites() / (N * N);
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

    public static void main(String[] args) {
        PercolationFactory pf = new PercolationFactory();
        //PercolationStats test = new PercolationStats(20, 10, pf);
        //PercolationStats test1 = new PercolationStats(50, 20, pf);
        //PercolationStats test2 = new PercolationStats(100, 50, pf);
        //PercolationStats test3 = new PercolationStats(50, 20, pf);
        PercolationStats test4 = new PercolationStats(64, 150, pf);
        System.out.println(test4.mean());
        System.out.println(test4.confidenceHigh());
    }
}
