/* *****************************************************************************
 *  Name:           Lucas Kamakura
 *  Date:           10/10/2021
 *  Description:    Collinear points
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

    private Point[] myPoints;
    private LineSegment[] lineSegments;
    private int segmentCount;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        checkNull(points);
        int numPoints = points.length;

        Point[] copyPoints = points.clone();
        Arrays.sort(copyPoints);

        checkDuplicate(copyPoints);

        ArrayList<LineSegment> segmentList = new ArrayList<LineSegment>();

        for (int i = 0; i < numPoints - 3; i++) {
            Point p1 = copyPoints[i];

            for (int j = i + 1; j < numPoints - 2; j++) {
                Point p2 = copyPoints[j];
                double slopeIJ = p1.slopeTo(p2);

                for (int k = j + 1; k < numPoints - 1; k++) {
                    Point p3 = copyPoints[k];
                    double slopeJK = p1.slopeTo(p3);
                    if (slopeIJ == slopeJK) {

                        for (int m = k + 1; m < numPoints; m++) {
                            Point p4 = copyPoints[m];
                            double slopeKM = p1.slopeTo(p4);
                            if (slopeIJ == slopeKM) {
                                segmentList.add(new LineSegment(p1, p4));
                            }
                        }
                    }

                }
            }
        }
        // We want to return an array of line segments, not a list.
        // So we convert segmentList to an array
        if (segmentList.size() > 0) {
            lineSegments = segmentList.toArray(new LineSegment[0]);
        }
        else {
            lineSegments = new LineSegment[0];
        }

    }

    // the number of line segments
    public int numberOfSegments() {
        return lineSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return lineSegments;
    }

    private void checkNull(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Point array to constructor cannot be null.");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Point at index " + i + " is null.");
            }
        }
    }

    private void checkDuplicate(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i].compareTo(points[i + 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found.");
            }
        }
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }

}


