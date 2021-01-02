import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {

    private final int gridSize;
    private final int numTrials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {

        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("grid size and number of trials must be greater than zero.");

        this.gridSize = n;
        this.numTrials = trials;
        thresholds = new double[trials];

    }

    private void runSim() {

        for (int i = 0; i < numTrials; i++) {
            System.out.println("SIM #" + (i + 1));
            // do trial
            Percolation grid = new Percolation(gridSize);
            int iterationCount = 0;

            while (!grid.percolates()) {
//                grid.printGrid();
//                System.out.println("\n==========\n");
                int row = (int) Math.floor(StdRandom.uniform(10) + 1);
                int col = (int) Math.floor(StdRandom.uniform(10) + 1);
//                System.out.println("ROW: " + row + ", COL: " + col);
                if (!grid.isOpen(row, col)) {
                    grid.open(row, col);
                    iterationCount++;
                }
            }

            // store threshold in thresholds array
            thresholds[i] = (double) iterationCount / (gridSize * gridSize);
        }
    }

    // sample mean of percolation threshold
    public double mean() {

        double thresholdSum = 0;

        for (double threshold : thresholds) {
            thresholdSum += threshold;
        }

        return thresholdSum / numTrials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {

        double stdDev = 0;

        for (double threshold : thresholds) {
            stdDev += ((threshold - mean()) * (threshold - mean()));

        }
        stdDev /= numTrials;
        return Math.sqrt(stdDev);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {

        return (mean() - (1.96 * stddev() / Math.sqrt(numTrials)));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {

        return (mean() + (1.96 * stddev() / Math.sqrt(numTrials)));
    }

    // test client (see below)
    public static void main(String[] args) {

//        Percolation grid = new Percolation(5);
        PercolationStats stats = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));

        stats.runSim();
        for (double threshold : stats.thresholds) System.out.println(threshold);

        System.out.println("AVG: " + stats.mean());

    }

}
