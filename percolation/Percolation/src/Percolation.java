
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;

public class Percolation {

    private int size;
    int[][] grid;
    private WeightedQuickUnionUF wqu;
    int openSites;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {

        this.size = n;
        grid = new int[n][n];
//        Arrays.fill(grid, 0);
        wqu = new WeightedQuickUnionUF(size * size);
        openSites = 0;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }

    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {

        // remember to add check out of bounds

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = 1;  // by convention, grid is 1-indexed, not zero
            if (col > 1 && isOpen(row, col - 1)) wqu.union(convertCoordinates(row, col), convertCoordinates(row, col - 1));
            if (col < size && isOpen(row, col + 1)) wqu.union(convertCoordinates(row, col), convertCoordinates(row, col + 1));
            if (row > 1 && isOpen(row - 1, col)) wqu.union(convertCoordinates(row, col), convertCoordinates(row - 1, col));
            if (row < size && isOpen(row + 1, col)) wqu.union(convertCoordinates(row, col), convertCoordinates(row + 1, col));
        }

        // next, connect to all adjacent open cells



    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        return (grid[row][col] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        //  PSEUDO
        //  for each cell on top row:
        //      check if row / col cell is connected
        //      if connection count > 0, return true;
        //

        for (int i = 0; i < size; i++) {
            if (wqu.find(i) == wqu.find(convertCoordinates(row, col))) return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {

        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {

        for (int i = 0; i < size; i++) {
            if (isFull(wqu.find(i), size)) return true;
        }

        return false;
    }

    // converts 2D coordinates to UF index

    public int convertCoordinates(int row, int col) {

        return (row - 1) * size + col;
    }

    // test client
    public static void main(String[] args) {

        Percolation perc = new Percolation(10);

        System.out.println("***** BEFORE *****\nPERCOLATES? " + perc.percolates() + "\nOPEN SITES: " + perc.numberOfOpenSites() + "\n\n");

        for (int i = 0; i < perc.size; i++) {
            perc.grid[i][5] = 1;
        }

        System.out.println("***** AFTER *****\nPERCOLATES? " + perc.percolates() + "\nOPEN SITES: " + perc.numberOfOpenSites() + "\n\n");

    }

}
