import java.util.ArrayList;

public class Board {
    private final int[][] grid;
    private final int N;
    private static final int BLACK = 0;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        N = tiles.length;
        grid = tiles;
    }

    // string representation of this board
    public String toString() {
       StringBuilder res = new StringBuilder();
       res.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                res.append(String.format("%2d ", grid[i][j]));
            res.append("\n");
        }
        return res.toString();
    }

    // board dimension n
    public int dimension() {
        return N;
    }

    // number of tiles out of place
    public int hamming() {
        int res = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (grid[i][j] != BLACK && grid[i][j] != getValue(i, j))
                    res++;
        return res;
    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {

        int res = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (grid[i][j] != BLACK && grid[i][j] != getValue(i, j))
                    res += Math.abs(getRow(grid[i][j]) - i) + Math.abs(getCol(grid[i][j]) - j);
        return res;
    }

    private int getCol(int val) {
        return (val - 1) % N;
    }

    private int getRow(int val) {
        return (val - 1) / N;
    }

    private int getValue(int i, int j) {
        return i * N + j + 1;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null)
            return false;
        if (y == this)
            return true;
        if (y.getClass().isInstance(this)) {
            Board yb = (Board) y;
            if (yb.N != this.N)
                return false;
            else {
                for (int i = 0; i < N; i++)
                    for (int j = 0; j < N; j++)
                        if (yb.grid[i][j] != this.grid[i][j])
                            return false;
                return true;
            }
        } else return false;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<Board>();
        for (int i = 0; i < N; i++){
            for (int j = 0; j < N; j++) {
                if (grid[i][j] == BLACK) {
                    if (i > 0) {
                        Board neibTop = new Board(grid);
                        neibTop.swap(i, j, i - 1, j);
                        neighbors.add(neibTop);
                    }

                    if (i < N - 1) {
                        Board neibBot = new Board(grid);
                        neibBot.swap(i, j, i + 1, j);
                        neighbors.add(neibBot);
                    }

                    if (j > 0) {
                        Board neibLeft = new Board(grid);
                        neibLeft.swap(i, j, i, j - 1);
                        neighbors.add(neibLeft);
                    }

                    if (j < N - 1) {
                        Board neibRight = new Board(grid);
                        neibRight.swap(i, j, i, j + 1);
                        neighbors.add(neibRight);
                    }
                }
            }
        }
        return neighbors;
    }



    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        Board twin = new Board(grid);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (grid[i][j] != BLACK && grid[i][j] != grid[0][0]){
                    twin.swap(0, 0, i, j);
                    return twin;
                }
        return twin;
    }

    private void swap(int vRow, int vCol, int wRow, int wCol) {
         int t = grid[vRow][vCol];
         grid[vRow][vCol] = grid[wRow][wCol];
         grid[wRow][wCol] = t;
     }

    // unit testing (not graded)
    public static void main(String[] args) {

    }

}