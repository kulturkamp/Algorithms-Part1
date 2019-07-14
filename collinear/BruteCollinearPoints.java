import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.IllegalArgumentException;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentsList;

    public BruteCollinearPoints(Point[] points){
        validate(points);
        Point[] copy = Arrays.copyOf(points, points.length);
        Arrays.sort(copy);
        segmentsList = new ArrayList<>();
        for (int p = 0; p < copy.length; p++) {
            for (int q = p + 1; q < copy.length; q++) {
                for (int r = q + 1; r < copy.length; r++) {
                    for (int s = r + 1; s < copy.length; s++) {
                        if (copy[p].slopeTo(copy[q]) == copy[p].slopeTo(copy[r]) &&
                                copy[p].slopeTo(copy[q]) == copy[p].slopeTo(copy[s])) {
                            segmentsList.add(new LineSegment(copy[p], copy[s]));
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        return segmentsList.size();
    }

    public LineSegment[] segments() {
        return segmentsList.toArray(new LineSegment[segmentsList.size()]);
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
