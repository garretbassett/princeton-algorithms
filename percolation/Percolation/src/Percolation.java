
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import java.util.Arrays;
import java.util.Random;

public class Percolation {

    private int size;
    int[][] grid;
    private WeightedQuickUnionUF wqu;
    private int openSites;

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
//            System.out.println(row + ", " + col + " NOT OPEN");
            grid[row - 1][col - 1] = 1;  // by convention, grid is 1-indexed, not zero
            openSites++;
            if (col > 1) {
                if (isOpen(row, col - 1)) wqu.union(convertCoordinates(row, col), convertCoordinates(row, col - 1));
            }
            if (col < size - 1) {
                if (isOpen(row, col + 1)) wqu.union(convertCoordinates(row, col), convertCoordinates(row, col + 1));
            }
            if (row > 1) {
                if (isOpen(row - 1, col)) wqu.union(convertCoordinates(row, col), convertCoordinates(row - 1, col));
            }
            if (row < size - 1) {
                if (isOpen(row + 1, col)) wqu.union(convertCoordinates(row, col), convertCoordinates(row + 1, col));
            }
        }

        // next, connect to all adjacent open cells



    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {

        return (grid[row - 1][col - 1] == 1);
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {

        //  PSEUDO
        //  for each cell on top row:
        //      check if row / col cell is connected
        //      if connection count > 0, return true;

//        System.out.println("ROW: " + row + ", COL: " + col);

        for (int i = 0; i < size; i++) {
//            System.out.println(wqu.find(i));
//            System.out.println(wqu.find(convertCoordinates(row, col)));
            if (wqu.find(i) == wqu.find(convertCoordinates(row - 1, col - 1))) return true;
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
//            System.out.println("SIZE: " + size + ", WQU: " + wqu.find(i));
//            if (isFull(size, wqu.find(i))) return true;
            if (isFull(size, size - i)) return true;
        }

        return false;
    }

    // converts 2D coordinates to UF index

    public int convertCoordinates(int row, int col) {
//        System.out.println("CONVERTING: " + row + ", " + col);
//        System.out.println("RESULT: " + ((row - 1) * size + col));
        return (row - 1) * size + col - 1;
    }

    public void printGrid() {

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                System.out.print(grid[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    // test client
    public static void main(String[] args) {

        Percolation perc = new Percolation(10);

//        System.out.println(perc.grid[1][1]);
//        System.out.println(perc.grid[9][5]);

        for (int i = 0; i <= perc.size * perc.size - 1; i++) {
            System.out.print(perc.wqu.find(i));
            System.out.print("\t");
            if (i % perc.size == 9) System.out.println();
        }

        System.out.println();

        for (int i = 0; i < perc.size; i++) {
            for (int j = 0; j < perc.size; j++) {
                System.out.print(perc.grid[i][j]);
                System.out.print("\t");
            }
            System.out.println();
        }

        System.out.println("***** BEFORE *****\nPERCOLATES? " + perc.percolates() + "\nOPEN SITES: " + perc.numberOfOpenSites() + "\n\n");

        // TEST 1

//        for (int i = 1; i <= perc.size; i++) {
//            System.out.println("i: " + i);
//            perc.open(i, 5);
//        }

        //////////

        // TEST 2

//        int column = 1;
//
//        for (int i = 1; i <= perc.size; i++) {
//            perc.open(i, column);
//            column ++;
//            if (column <= 10) perc.open(i, column);
//        }

        //////////

        // TEST 3

        perc.open(1, 3);
        perc.open(2, 3);
        perc.open(2, 4);
        perc.open(2, 5);
//        perc.open(3, 4);
        perc.open(4, 4);
        perc.open(4, 3);
        perc.open(4, 2);
        perc.open(5, 2);
        perc.open(6, 2);
        perc.open(6, 3);
        perc.open(7, 3);
        perc.open(7, 4);
        perc.open(7, 5);
        perc.open(8, 5);
        perc.open(8, 6);
        perc.open(9, 6);
        perc.open(9, 5);
        perc.open(10, 5);


        for (int i = 0; i <= perc.size * perc.size - 1; i++) {
            System.out.print(perc.wqu.find(i));
            System.out.print("\t");
            if (i % perc.size == 9) System.out.println();
        }

        System.out.println();

        perc.printGrid();

        System.out.println("***** AFTER *****\nPERCOLATES? " + perc.percolates() + "\nOPEN SITES: " + perc.numberOfOpenSites() + "\n\n");

    }

}
