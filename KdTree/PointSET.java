import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import java.util.HashSet;
import java.util.TreeSet;

public class PointSET {

    private TreeSet<Point2D> tset;

    public PointSET() {
        tset = new TreeSet<Point2D>();
    }

    public boolean isEmpty() {
        return tset.isEmpty();
    }

    public int size() {
        return tset.size();
    }

    public void insert(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        tset.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        return tset.contains(p);
    }

    public void draw() {
        for (Point2D p : tset) {
            p.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        HashSet<Point2D> hs = new HashSet<>();
        for (Point2D p : tset) {
            if (rect.contains(p))
                hs.add(p);
        }
        return hs;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        double min = Double.MAX_VALUE;
        Point2D res = null;
        for (Point2D q : tset) {
            if (q.distanceTo(p) < min) {
                min = q.distanceTo(p);
                res = q;
            }
        }
        return res;
    }

    public static void main(String[] args) {

    }
}