package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private boolean[][] earth;
    private int side;
    private int openCount;
    private WeightedQuickUnionUF connectedGrids;
    private WeightedQuickUnionUF connectedGridsToTop;


    public Percolation(int N) {
        if (N < 0 || N == 0) {
            throw new java.lang.IllegalArgumentException();
        }
        side = N;
        earth = new boolean[side][side];

        connectedGrids = new WeightedQuickUnionUF(N * N + 2);
        connectedGridsToTop = new WeightedQuickUnionUF(N * N + 1);
        /*
        for (int i = 0; i < N; i++) {
            connectedGrids.union(N * N, xyTo1D(0, i));
            connectedGridsToTop.union(N * N, xyTo1D(0, i));
        }
        for (int i = 0; i < N; i++) {
            connectedGrids.union(N * N + 1, xyTo1D(N - 1, i));
        }

         */
        this.clear();

    }

    private void clear() {
        for (int x = 0; x < side; x++) {
            for (int y = 0; y < side; y++) {
                earth[x][y] = false;
            }
        }
        openCount = 0;
    }

    public void open(int row, int col) {
        if (!inputInGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        if (earth[row][col]) {
            return;
        }
        earth[row][col] = true;
        openCount += 1;
        if (inputInGrid(row + 1, col)) {
            if (isOpen(row + 1, col)) {
                connectedGrids.union(xyTo1D(row, col), xyTo1D(row + 1, col));
                connectedGridsToTop.union(xyTo1D(row, col), xyTo1D(row + 1, col));
            }
        }
        if (inputInGrid(row - 1, col)) {
            if (isOpen(row - 1, col)) {
                connectedGrids.union(xyTo1D(row, col), xyTo1D(row - 1, col));
                connectedGridsToTop.union(xyTo1D(row, col), xyTo1D(row - 1, col));
            }
        }
        if (inputInGrid(row, col + 1)) {
            if (isOpen(row, col + 1)) {
                connectedGrids.union(xyTo1D(row, col), xyTo1D(row, col + 1));
                connectedGridsToTop.union(xyTo1D(row, col), xyTo1D(row, col + 1));
            }
        }
        if (inputInGrid(row, col - 1)) {
            if (isOpen(row, col - 1)) {
                connectedGrids.union(xyTo1D(row, col), xyTo1D(row, col - 1));
                connectedGridsToTop.union(xyTo1D(row, col), xyTo1D(row, col - 1));
            }
        }
        if (row == 0) {
            connectedGrids.union(xyTo1D(row, col), side * side);
            connectedGridsToTop.union(xyTo1D(row, col), side * side);
        }
        if (row == side - 1) {
            connectedGrids.union(xyTo1D(row, col), side * side + 1);
        }

    }

    public boolean isOpen(int row, int col) {
        if (!inputInGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return earth[row][col];
    }

    public boolean isFull(int row, int col) {
        if (!inputInGrid(row, col)) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return connectedGridsToTop.connected(xyTo1D(row, col), side * side);
    }

    private int xyTo1D(int row, int col) {
        return row * side + col;
    }

    private boolean inputInGrid(int row, int col) {
        return (row >= 0 && row <= side - 1 && col >= 0 && col <= side - 1);
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        return connectedGrids.connected(side * side, side * side + 1);
    }

    public static void main(String[] args) {

    }
}
