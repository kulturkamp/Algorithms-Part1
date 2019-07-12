import edu.princeton.cs.algs4.WeightedQuickUnionUF;


public class Percolation {
    private boolean[][] sites;
    private final WeightedQuickUnionUF ufGrid;
    private final int gridSize;
    private final int siteSize;
    public Percolation(int n){   // create n-by-n grid, with all sites blocked
        if (n <= 0)
			throw new IllegalArgumentException("illegal grid size");
		siteSize = n;
        gridSize = n*n +2;
        sites = new boolean[siteSize][siteSize];
        ufGrid = new WeightedQuickUnionUF(gridSize);  // n*n grid plus imaginary top and bottom nodes
    }
        // by convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site
    private boolean get(int i, int j) { return sites[i - 1][j - 1];}
    private void set(int i, int j, boolean v) {sites[i - 1][j - 1] = v;}

    public void open(int row, int col) {  // open site (row, col) if it is not open already
        validate(row, col);
        if(!isOpen(row, col))
        {
            set(row, col, true);
            connectToAdjacent(row, col);
        }
    }
    public boolean isOpen(int row, int col) { // is site (row, col) open?
        validate(row, col);
        return get(row, col);
    }
    public boolean isFull(int row, int col) { // is site (row, col) full?
        validate(row, col);
        return isOpen(row, col) && isConnectedTop(row, col);
    }
    public int numberOfOpenSites() {  // number of open sites
        int count = 0;
        for (int i = 0; i < siteSize; ++i)
            for (int j = 0; j < siteSize; ++j)
                if (sites[i][j] == true)
                    ++count;
        return count;
    }
    public boolean percolates() { // does the system percolate?
        return ufGrid.connected(0, gridSize - 1);
    }
    private void validate(int i, int j) { // validate site`s coordinates
        if (i > siteSize || j > siteSize || i < 1 || j < 1)
            throw new IllegalArgumentException("site index is out of grid");
    }
    private void connectToAdjacent(int i, int j) {
        int _this = get1DIndex(i, j);
        int top = get1DIndex(i - 1, j);
        int bot = get1DIndex(i + 1, j);
        int left = get1DIndex(i, j - 1);
        int right = get1DIndex(i, j + 1);
        if (i == 1)
            ufGrid.union(_this, 0); // if (i, j) is on a first row connect it to the imaginary top node
        if (i == siteSize)
            ufGrid.union(_this, gridSize - 1);  // connect to imaginary bottom node
        if (i != 1 && get(i - 1, j))
            ufGrid.union(_this, top);
        if (i != siteSize && get(i + 1, j))
            ufGrid.union(_this, bot);
        if (j != 1 && get(i, j - 1))
            ufGrid.union(_this, left);
        if (j != siteSize && get(i, j + 1))
            ufGrid.union(_this, right);

    }
    private int get1DIndex(int i, int j)
    {
        return siteSize * (i - 1) + j - 1;  // (1, 1) -> 0
    }
    private boolean isConnectedTop(int i, int j)
    {
        return ufGrid.connected(0, get1DIndex(i, j));
    }
    public static void main(String[] args) {  // test client (optional)
        Percolation p = new Percolation(3);
        p.open(1, 2);
        p.open(2, 2);
        p.open(2, 3);
        p.open(3, 3);       
        boolean bb = p.percolates();
        // System.out.println(p.numberOfOpenSites());
        System.out.println(bb);
    }
}
