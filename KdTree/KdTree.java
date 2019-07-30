import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.StdDraw;

import java.util.HashSet;

public class KdTree {
    private static final double XMIN = 0.0;
    private static final double YMIN = 0.0;
    private static final double XMAX = 1.0;
    private static final double YMAX = 1.0;

    private class Node {
        private Point2D p;
        private Node left;
        private Node right;
        private RectHV rect;

        public Node (Point2D _p, RectHV _rect ) {
            p = _p;
            left = null;
            right = null;
            rect = _rect;
        }
    }

    private int size;
    private Node root;

    public KdTree() {
        size = 0;
        root = null;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public void insert (Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        insert (root, p, XMIN, YMIN, XMAX, YMAX, 0);
    }

    private Node insert(Node n, Point2D p, double xmin, double ymin, double xmax, double ymax, int level) {
        if (n == null) {
            size++;
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax));
        }

        int cmp = compareTo(p, n.p, level);

        if (cmp < 0) {
            if (level % 2 == 0)
                n.left = insert(n.left, p, xmin, ymin, n.p.x(), ymax, level + 1);
             else
                n.left = insert(n.left, p, xmin, ymin, xmax, n.p.y(), level + 1);
        } else if (cmp > 0) {
            if (level % 2 == 0)
                n.right = insert(n.right, p, n.p.x(), ymin, xmax, ymax, level + 1);
             else
                n.right = insert(n.right, p, xmin, n.p.y(), xmax, ymax, level + 1);
        }
        return n;
    }

    private int compareTo(Point2D a, Point2D b, int level) {
        if (level % 2 == 0) {
            int cmpResult = Double.compare(a.x(), b.x());

            if (cmpResult == 0) {
                return Double.compare(a.y(), b.y());
            } else
                return cmpResult;

        } else {
            int cmpResult = Double.compare(a.y(), b.y());

            if (cmpResult == 0) {
                return Double.compare(a.x(), b.x());
            } else
                return cmpResult;
        }
    }

    public boolean contains (Point2D p) {
        return contains(root, p, 0) != null;
    }

    private Point2D contains(Node n, Point2D p, int level) {
        while (n != null) {
            int cmp = compareTo(p, n.p, level);

            if (cmp < 0)
                return contains(n.left, p, level + 1);
            else if (cmp > 0)
                return contains(n.right, p, level + 1);
            else
                return n.p;
        }
        return null;
    }

    public void draw() {
        drawLine(root, 0);
    }

    private void drawLine(Node x, int level) {
        if (x != null) {
            drawLine(x.left, level + 1);

            StdDraw.setPenRadius();
            if (level % 2 == 0) {
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(), x.rect.ymin(), x.p.x(), x.rect.ymax());
            } else {
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(), x.p.y(), x.rect.xmax(), x.p.y());
            }

            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(.01);
            x.p.draw();
            drawLine(x.right, level + 1);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        HashSet<Point2D> hs = new HashSet<>();
        rangeAdd(root, rect, hs);
        return hs;
    }

    private void rangeAdd(Node x, RectHV rect, HashSet<Point2D> hs) {
        if (x != null && rect.intersects(x.rect)) {
            rangeAdd(x.left, rect, hs);
            rangeAdd(x.right, rect, hs);
        }
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new java.lang.IllegalArgumentException();
        Point2D res = null;
        res = nearest(root, p, res);
        return res;
    }

    private Point2D nearest(Node x, Point2D point, Point2D min) {
        if (x != null) {
            if (min == null) {
                min = x.p;
            }

            if (min.distanceTo(point) >= x.rect.distanceTo(point)) {
                if (x.p.distanceTo(point) < min.distanceTo(point)) {
                    min = x.p;
                }

                if (x.right != null && x.right.rect.contains(point)) {
                    min = nearest(x.right, point, min);
                    min = nearest(x.left, point, min);
                } else {
                    min = nearest(x.left, point, min);
                    min = nearest(x.right, point, min);
                }
            }
        }
        return min;
    }
}
