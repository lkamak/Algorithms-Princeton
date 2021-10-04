/* *****************************************************************************
 *  Name:              Lucas Kamakura
 *  Coursera User ID:  123456
 *  Last modified:     23/09/2021
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int gridSize;
    private final int virtualTop;
    private final int virtualBottom;
    private boolean[][] grid;
    private int openCount;
    private WeightedQuickUnionUF unionFindArray;

    public Percolation(int n) {
        if (n < 1) throw new IllegalArgumentException("n must be > 0");

        // Initializing grid and gridSize to keep track of open and closed sites,
        // and size of the grid respectively. Grid is initially all blocked.
        // Grid is initialized as [n + 1][n + 1] to account for 1 indexing
        // instead of 0 indexing as per the project requirements
        gridSize = n;
        grid = new boolean[gridSize + 1][gridSize + 1];

        for (int row = 0; row < gridSize + 1; row++) {
            for (int col = 0; col < gridSize + 1; col++) {
                grid[row][col] = false;
            }
        }

        // Initializing UF object to keep track of unions
        // UF object is of size (n * n) + 2 to account for virtual top and bottom
        unionFindArray = new WeightedQuickUnionUF((gridSize * gridSize) + 2);
        virtualTop = 0;
        virtualBottom = (gridSize * gridSize) + 1;

        // Setting initial count of open sites to 0
        openCount = 0;
    }

    // Helper function to map locations in my grid to their index in
    // the UF object
    private int map2Dto1D(int row, int col) {
        row--;
        col--;
        return ((row * gridSize) + col + 1);
    }

    // Helper function to validate that input > 0 and < n
    private boolean outOfBounds(int row, int col) {
        if (row < 1 || col < 1 || row > gridSize || col > gridSize) {
            return true;
        }
        return false;
    }

    private boolean connected(int row, int col, int row2, int col2) {
        return unionFindArray.find(map2Dto1D(row, col)) == unionFindArray
                .find(map2Dto1D(row2, col2));
    }

    // We will connect a site to the virtual top iff row == 0
    public void open(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "row = " + row + " or col = " + col + "are out of bounds of grid 0, "
                            + gridSize);
        }

        // Checking if the site is open.

        if (isOpen(row, col)) {
            return;
        }

        // Need to check sites above, below, to the left, and to the right
        grid[row][col] = true;
        int siteIndex = map2Dto1D(row, col);
        int upIndex = map2Dto1D(row - 1, col);
        int downIndex = map2Dto1D(row + 1, col);
        int leftIndex = map2Dto1D(row, col - 1);
        int rightIndex = map2Dto1D(row, col + 1);

        if (row == 1) {
            unionFindArray.union(siteIndex, virtualTop);
        }
        if (row == gridSize) {
            unionFindArray.union(siteIndex, virtualBottom);
        }

        if (row - 1 > 0) {
            if (isOpen(row - 1, col)) {
                unionFindArray.union(siteIndex, upIndex);
            }
        }
        if (row + 1 <= gridSize) {
            if (isOpen(row + 1, col)) {
                unionFindArray.union(siteIndex, downIndex);
            }
        }
        if (col - 1 > 0) {
            if (isOpen(row, col - 1)) {
                unionFindArray.union(siteIndex, leftIndex);
            }
        }
        if (col + 1 <= gridSize) {
            if (isOpen(row, col + 1)) {
                unionFindArray.union(siteIndex, rightIndex);
            }
        }
        openCount++;
    }

    public boolean isOpen(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "row = " + row + " or col = " + col + "are out of bounds of grid 0, "
                            + gridSize);
        }
        if (grid[row][col]) return true;
        return false;
    }

    public boolean isFull(int row, int col) {
        if (outOfBounds(row, col)) {
            throw new IllegalArgumentException(
                    "row = " + row + " or col = " + col + "are out of bounds of grid 0, "
                            + gridSize);
        }
        int indexOfSite = map2Dto1D(row, col);
        if (unionFindArray.find(indexOfSite) == unionFindArray.find(virtualTop)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return openCount;
    }

    public boolean percolates() {
        if (unionFindArray.find(virtualTop) == unionFindArray.find(virtualBottom)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
