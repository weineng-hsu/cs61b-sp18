package hw4.puzzle;

import java.security.DrbgParameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Board implements WorldState {
    private final int[][] board;
    private final int N;

    public Board(int[][] tiles) {
        N = tiles.length;
        board = new int[N][N];
        for (int row = 0; row < N; row += 1) {
            for (int column = 0; column < N; column += 1) {
                board[row][column] = tiles[row][column];
            }
        }
    }

    public int tileAt(int i, int j) {
        if (i > N - 1 || j > N - 1 || i < 0 || j < 0) {
            throw new java.lang.IndexOutOfBoundsException("Invalid index given: i == " + i + " j == " + j);
        } else {
            return board[i][j];
        }
    }

    public int size() {
        return N;
    }

    public Iterable<WorldState> neighbors() {
        List<WorldState> neighbors = new ArrayList<>();
        int spaceC = - 2, spaceR = - 2;
        for (int row = 0; row < N; row += 1) {
            for (int column = 0; column < N; column += 1) {
                if (board[row][column] == 0) {
                    spaceC = column;
                    spaceR = row;
                    break;
                }
            }
        }
        if (spaceC + 1 < N) {
            Board neighbor = new Board(board);
            neighbor.board[spaceR][spaceC] = board[spaceR][spaceC + 1];
            neighbor.board[spaceR][spaceC + 1] = 0;
            neighbors.add(neighbor);
        }
        if (spaceR + 1 < N) {
            Board neighbor = new Board(board);
            neighbor.board[spaceR][spaceC] = board[spaceR + 1][spaceC];
            neighbor.board[spaceR + 1][spaceC] = 0;
            neighbors.add(neighbor);
        }
        if (spaceC - 1 >= 0) {
            Board neighbor = new Board(board);
            neighbor.board[spaceR][spaceC] = board[spaceR][spaceC - 1];
            neighbor.board[spaceR][spaceC - 1] = 0;
            neighbors.add(neighbor);
        }
        if (spaceR - 1 >= 0) {
            Board neighbor = new Board(board);
            neighbor.board[spaceR][spaceC] = board[spaceR - 1][spaceC];
            neighbor.board[spaceR - 1][spaceC] = 0;
            neighbors.add(neighbor);
        }
        return neighbors;
    }

    public int hamming() {
        int hamming = 0;
        int expectedValue = 1;
        for (int row = 0; row < N; row += 1) {
            for (int column = 0; column < N; column += 1) {
                if (expectedValue == N * N) {
                    break;
                }
                if (board[row][column] != expectedValue) {
                    hamming += 1;
                }
                expectedValue += 1;
            }
        }
        return hamming;
    }

    public int manhattan() {
        int manhattan = 0;
        for (int row = 0; row < N; row += 1) {
            for (int column = 0; column < N; column += 1) {
                if (board[row][column] == 0) {
                    continue;
                }
                int expRow = (board[row][column] - 1) / N;
                int expCol = (board[row][column] - 1) % N;
                manhattan += Math.abs(expRow - row);
                manhattan += Math.abs(expCol - column);
            }
        }
        return manhattan;
    }

    public int estimatedDistanceToGoal() {
        return manhattan();
    }

    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || y.getClass() != this.getClass()) {
            return false;
        }
        Board otherBoard = (Board) y;
        for (int row = 0; row < N; row += 1) {
            for (int column = 0; column < N; column += 1) {
                if (otherBoard.board[row][column] !=  board[row][column]) {
                    return false;
                }
            }
        }
        return true;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
