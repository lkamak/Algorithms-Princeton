/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

    // return list for segments
    private LineSegment[] segmentList;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        checkNull(points);
        int numPoints = points.length;

        Point[] sortedPoints = points.clone();
        Arrays.sort(sortedPoints);
        checkDuplicate(sortedPoints);

        ArrayList<LineSegment> lineSegmentArray = new ArrayList<LineSegment>();

        // We sort the sortedPoints array in respect to all points in sortedPoints
        // This way every point will "be at the origin" at least once
        // Then we iterate through each version of sortedPoints looking for
        // sequences of 3 or more points with the same slope
        for (int i = 0; i < numPoints; i++) {

            Point currPoint = sortedPoints[i]; // Set the origin point
            Point[] sortedBySlope = sortedPoints.clone(); // Create new array of points
            // Sort new array of points in respect to the origin point
            Arrays.sort(sortedBySlope, currPoint.slopeOrder());

            int x = 1; // We begin iterating from the point closest to the origin
            while (x < numPoints) {

                // Possible candidate points that might be collinear
                ArrayList<Point> canditatePoints = new ArrayList<Point>();

                // Keep tabs of slope from origin to point x
                double slopeToOrigin = currPoint.slopeTo(sortedBySlope[x]);

                // We know the slope from origin to x. So, we keep searching in the
                // sortedBySlope array for points with the same slope
                while (x < numPoints && currPoint.slopeTo(sortedBySlope[x]) == slopeToOrigin) {
                    // If slopes match, add them to candidates
                    canditatePoints.add(sortedBySlope[x++]);
                }

                // After exiting for loop above, we know the next slope didn't match
                // to the origin -> first point slope so we check to see if
                // candidates is of length 3 or higher. If it is, we add it to
                // the return segment list
                if (canditatePoints.size() >= 3
                        && currPoint.compareTo(canditatePoints.get(0)) < 0) {
                    Point min = currPoint;
                    Point max = canditatePoints.get(canditatePoints.size() - 1);
                    lineSegmentArray.add(new LineSegment(min, max));
                }

            }

        }
        segmentList = lineSegmentArray.toArray(new LineSegment[0]);
    }


    // the number of line segments
    public int numberOfSegments() {
        return segmentList.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return segmentList;
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
        /*
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(4, 4);
        Point p5 = new Point(5, 6);
        Point p6 = new Point(5, 7);

        Point[] points = { p1, p2, p3, p4, p5, p6 };
        FastCollinearPoints test = new FastCollinearPoints(points);
        StdOut.println(test.numberOfSegments());
        */
    }


}
