/* *****************************************************************************
 *  Name:           Lucas Kamakura
 *  Date:           10/11/2021
 *  Description:    Board class for 8puzzle
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private final int N;
    private final int gridSize;
    private final int[] board;
    private final int[] goalBoard;
    private int zeroIndex;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        // Throw IllegalArgumentException if board == null;
        checkNull(tiles);

        // Keep track of board dimension
        N = tiles.length;

        // Keep track of board size (dimension * dimension)
        gridSize = N * N;

        // Creating a 1D copies of tiles (board and goalBoard)
        board = new int[gridSize];
        goalBoard = new int[gridSize];
        int x = 0;
        zeroIndex = 0;
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                board[x] = tiles[row][col];
                goalBoard[x] = tiles[row][col];
                if (tiles[row][col] == 0) {
                    zeroIndex = x;
                }
                x++;
            }
        }

        // I set the value 0 in the array to the max value, so when sorted
        // it is at the end of the array. Then after sorting I change it back
        // to 0
        goalBoard[zeroIndex] = Integer.MAX_VALUE;
        Arrays.sort(goalBoard);
        goalBoard[gridSize - 1] = 0;

    }

    // string representation of this board
    public String toString() {
        int x = 0;
        StringBuilder stringBoard = new StringBuilder();
        stringBoard.append(N + "\n");
        for (int row = 0; row < N; row++) {
            for (int col = 0; col < N; col++) {
                stringBoard.append(board[(row * N) + col] + " ");
            }
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        for (int i = 0; i < gridSize; i++) {
            if (board[i] != goalBoard[i] && board[i] != 0) {
                count++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int manhattanDistance = 0;
        for (int i = 0; i < gridSize; i++) {
            if (board[i] != goalBoard[i] && board[i] != 0) {
                int delta = Math.abs(board[i] - 1 - i);
                manhattanDistance += (delta / N) + (delta % N);
            }
        }
        return manhattanDistance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return Arrays.equals(board, goalBoard);
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (this.board != that.board) return false;
        if (this.goalBoard != that.goalBoard) return false;
        if (this.dimension() != that.dimension()) return false;
        return true;
    }

    // all neighboring boards
    // Create an array of neighboring boards
    // First we check where the empty tile is.
    // If zeroIndex > N then it is not in the first row
    // If zeroIndex < gridSize - N then it is not in the last row
    // If zeroIndex % N is between 1 and N - 1 then it is not
    // in the first or last column
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        int[] tempArray = board.clone();
        if (zeroIndex >= N) {
            Board upNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex - N);
            neighbors.add(upNeighbor);
        }
        if (zeroIndex <= gridSize - N) {
            Board downNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex + N);
            neighbors.add(downNeighbor);
        }
        if (zeroIndex % N != 0 && zeroIndex % N != (N - 1)) {
            Board leftNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex - 1);
            Board rightNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex + 1);
            neighbors.add(leftNeighbor);
            neighbors.add(rightNeighbor);
        }
        if (zeroIndex % N != 0) {
            Board leftNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex - 1);
            neighbors.add(leftNeighbor);
        }
        if (zeroIndex % N != (N - 1)) {
            Board rightNeighbor = getNeighbor(tempArray, zeroIndex, zeroIndex + 1);
            neighbors.add(rightNeighbor);
        }

        return neighbors;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int[] twinBoardArray = board.clone();
        int tile1 = 0;
        int tile2 = gridSize - 1;
        while (twinBoardArray[tile1] == 0 && twinBoardArray[tile2] == 0) {
            tile1++;
            tile2--;
        }
        exchange(twinBoardArray, tile1, tile2);
        int[][] twinBoardMatrix = arrayToMatrix(twinBoardArray);
        return new Board(twinBoardMatrix);
    }

    private void checkNull(int[][] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Matrix for constructor cannot be null");
        }
    }

    private int[][] arrayToMatrix(int[] tiles) {
        if (tiles == null) {
            throw new IllegalArgumentException("Argument to ArrayToMatrix cannot be null");
        }
        int[][] matrix = new int[N][N];

        int x = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                matrix[i][j] = tiles[x];
                x++;
            }
        }
        return matrix;
    }

    private void exchange(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    private Board getNeighbor(int[] tempArray, int zeroIndex, int direction) {
        exchange(tempArray, zeroIndex, direction);
        int[][] leftNeighborMatrix = arrayToMatrix(tempArray);
        Board neighbor = new Board(leftNeighborMatrix);
        exchange(tempArray, direction, zeroIndex);
        return neighbor;
    }

    // unit testing (not graded)
    public static void main(String[] args) {

        for (String filename : args) {

            // read in the board specified in the filename
            In in = new In(filename);
            int n = in.readInt();
            int[][] tiles = new int[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    tiles[i][j] = in.readInt();
                }
            }

            // solve the slider puzzle
            Board initial = new Board(tiles);
            StdOut.println(initial.toString());
            StdOut.println(initial.hamming());
            StdOut.println(initial.manhattan());
            for (int i : initial.goalBoard) {
                StdOut.print(i + " ");
            }
            StdOut.println();
            for (Board neighbor : initial.neighbors()) {
                StdOut.println(neighbor.toString());
            }
            StdOut.println(initial.twin().toString());
        }
    }

}
