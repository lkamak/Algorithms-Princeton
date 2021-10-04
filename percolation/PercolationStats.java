/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double globalTrials;
    private final double[] trialsResults;
    private double globalMean;
    private double globalStdev;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (trials <= 0 || n <= 0) {
            throw new IllegalArgumentException("n and T must be larger than 0");
        }

        Percolation percolationTool;
        double gridSize = n * n;
        int rowToOpen;
        int colToOpen;
        globalTrials = trials;
        trialsResults = new double[trials];
        double openSites;

        for (int i = 0; i < trials; i++) {
            percolationTool = new Percolation(n);
            openSites = 0;

            while (!percolationTool.percolates()) {
                rowToOpen = StdRandom.uniform(n) + 1;
                colToOpen = StdRandom.uniform(n) + 1;

                if (!percolationTool.isOpen(rowToOpen, colToOpen)) {
                    percolationTool.open(rowToOpen, colToOpen);
                }
            }

            openSites = percolationTool.numberOfOpenSites();
            trialsResults[i] = openSites / gridSize;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        globalMean = StdStats.mean(trialsResults);
        return globalMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        globalStdev = StdStats.stddev(trialsResults);
        return globalStdev;
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double confidence = mean() - (1.96 * stddev()) / (Math.sqrt(globalTrials));
        return confidence;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double confidence = mean() + (1.96 * stddev()) / (Math.sqrt(globalTrials));
        return confidence;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        StdOut.println("mean                    = " + percStats.mean());
        StdOut.println("stddev                  = " + percStats.stddev());
        StdOut.println(
                "95% confidence interval = [" + percStats.confidenceLo() + ", " + percStats
                        .confidenceHi() + "]");
    }
}
