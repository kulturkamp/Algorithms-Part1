import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class FastCollinearPoints {
    private ArrayList<LineSegment> segments = new ArrayList<LineSegment>();
    private Point[] copy;

    public FastCollinearPoints(Point[] points){
        validate(points);
        copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);

        for (int i = 0; i < copy.length - 1; i++){
            Point origin = copy[i];
            double[] slopes = new double[copy.length - i - 1];
            Point[] other = new Point[copy.length - i - 1];

            for (int j = 0; j < copy.length - i - 1; j++){
                other[j] = copy[j + i + 1];
            }
            for (int k = 0; k < other.length; k++){
                slopes[k] = origin.slopeTo(other[k]);
            }

            Arrays.sort(other, origin.slopeOrder());
            Arrays.sort(slopes);
            for (int count = 0, j = 0; j < slopes.length - 1; j++) {
                if (slopes[j] == slopes[j+1]) {
                    count++;
                }
                if (count >= 2) {
                    segments.add(new LineSegment(origin, other[j + 1]));
                    break;
                }
            }
        }
    }

    public int numberOfSegments() {
        return segments.size();
    }

    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
    }

    private void validate(Point[] points) {
        if (points == null){
            throw new IllegalArgumentException("Input is empty");
        }
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null){
                throw new IllegalArgumentException("There is an empty point");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("There are duplicate points");
                }
            }
        }
    }
}
