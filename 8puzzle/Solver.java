import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private class SearchNode implements Comparable<SearchNode>{
        public Board board;
        public SearchNode prevNode;
        public int moves;
        public int priority;

        public SearchNode(Board _board, SearchNode prev) {
            board = _board;
            prevNode = prev;
            if (prev == null)
                moves = 0;
            else
                moves = prev.moves + 1;
            priority = moves + board.manhattan();
        }

        @Override
        public int compareTo(SearchNode o) {
            return Integer.compare(this.priority, o.priority);
        }
      }


    private Stack<Board> solution;
    private SearchNode current;
    private SearchNode currentTwin;


    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null)
            throw new IllegalArgumentException("Initial node is null");
        current = new SearchNode(initial, null);
        currentTwin = new SearchNode(initial.twin(), null);
        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        MinPQ<SearchNode> twinPq = new MinPQ<SearchNode>();
        pq.insert(current);
        twinPq.insert(currentTwin);
        while (true) {
            current = pq.delMin();
            if (current.board.isGoal())
                break;
            putNeib(current, pq);
            currentTwin = twinPq.delMin();
            if (currentTwin.board.isGoal())
                break;
            putNeib(currentTwin, twinPq);
        }
    }

    private void putNeib(SearchNode searchNode, MinPQ<SearchNode> pq){
        Iterable<Board> neighbors = searchNode.board.neighbors();
        for(Board neighbor : neighbors){
            if(searchNode.prevNode == null || !neighbor.equals(searchNode.prevNode.board))
                pq.insert(new SearchNode(neighbor,searchNode));
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return current.board.isGoal();

    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable())
            return moves();
        else
            return -1;

    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if(current.board.isGoal()){
            solution = new Stack<Board>();
            SearchNode node = current;
            while(node != null){
                solution.push(node.board);
                 node = node.prevNode;
            }
            return solution;

        } else
            return null;
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In("8puzzle/123.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }

}