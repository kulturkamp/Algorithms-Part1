import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private final int _trials;
    private final int gridSize;
    private final double[] thresholds;
	private static final double CONFIDENCE_95 = 1.96;
    public PercolationStats(int n, int trials) {    // perform trials independent experiments on an n-by-n grid
    if (n <= 0 || trials <= 0) throw new IllegalArgumentException();
    _trials = trials;
    gridSize = n;
    thresholds = new double[trials];
    for (int i = 0; i < trials; ++i)
        thresholds[i] = findThreshold();
    }

    public double mean() {                          // sample mean of percolation threshold
    return StdStats.mean(thresholds);
    }

    public double stddev() {                      // sample standard deviation of percolation threshold
    return StdStats.stddev(thresholds);
    }
    public double confidenceLo() {                // low  endpoint of 95% confidence interval
    return mean() - CONFIDENCE_95*stddev()/Math.sqrt(_trials);
    }
    public double confidenceHi() {                 // high endpoint of 95% confidence interval
    return mean() + CONFIDENCE_95*stddev()/Math.sqrt(_trials);
    }
    private double findThreshold() {
    Percolation p = new Percolation(gridSize);
    int count = 0;
    int i, j;
    while (!p.percolates()) {
        do{
             i = StdRandom.uniform(gridSize) + 1;
             j = StdRandom.uniform(gridSize) + 1;
        }  while(p.isOpen(i, j));
        count++;
        p.open(i, j);
    }
    return count/Math.pow(gridSize,2);
    }


    public static void main(String[] args) {     // test client (described below)
        int n = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(n, T);
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = "+ ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
    }
}

