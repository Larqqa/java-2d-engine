package engine.visuals.renderable.shapes;

import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class Polygon extends Shape {
    public static boolean[] plot(ArrayList<Point> pointArray, int lineWidth) {
        MinMax minMax = new MinMax(pointArray, lineWidth);
        ArrayList<Point> points = normalizePoints(pointArray, minMax);

        // Close the loop
        if (points.get(0) != points.get(points.size() - 1)) {
            points.add(points.get(0));
        }

        int width = (int) minMax.width();
        int height = (int) minMax.height();

        boolean[] pixels = new boolean[width * height];

        Point previousPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);

            MinMax pointMinMax = new MinMax(new ArrayList<>(
                    Arrays.asList(previousPoint, thisPoint)), lineWidth);

            int minX = (int) Math.min(previousPoint.getX(), thisPoint.getX());
            int minY = (int) Math.min(previousPoint.getY(), thisPoint.getY());
            int lineW = (int) (Math.max(previousPoint.getX(), thisPoint.getX()) + lineWidth) - minX;
            combinePixels(minX, minY, Line.plot(previousPoint, thisPoint, lineWidth), lineW, pixels, width);

            combinePixels(
                    (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                    Line.plot(previousPoint, thisPoint, lineWidth), (int) pointMinMax.width(),
                    pixels, width);

            previousPoint = thisPoint;
        }

        return pixels;
    }

    // sunshine2k.de/coding/java/Polygon/Filling/FillPolygon.htm
    // https://www.ques10.com/p/11008/explain-scan-line-fill-algorithm-with-an-example-1/
    // https://stackoverflow.com/questions/24469459/scanline-algorithm-how-to-calculate-intersection-points
    public static boolean[] fill(ArrayList<Point> pointArray) {
        int lineWidth = 1;
        boolean[] pixels = plot(pointArray, lineWidth);

        MinMax minMax = new MinMax(pointArray, lineWidth);
        ArrayList<Point> points = normalizePoints(pointArray, minMax);
        minMax = new MinMax(points, lineWidth);
        int width = (int) minMax.width();

        // Close the loop
        if (points.get(0) != points.get(points.size() - 1)) {
            points.add(points.get(0));
        }

        ArrayList<Point[]> edges = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Point[] edgePoints = new Point[2];
            Point firstPoint = points.get(i);
            Point secondPoint = points.get(i + 1);

            if (firstPoint.getY() > secondPoint.getY()) {
                Point temp = firstPoint;
                firstPoint = secondPoint;
                secondPoint= temp;
            }

            edgePoints[0] = firstPoint;
            edgePoints[1] = secondPoint;
            edges.add(edgePoints);
        }

        edges.sort(Comparator.comparingInt(edge -> (int) edge[0].getY()));

        ArrayList<ArrayList<Point>> scanlines = new ArrayList<>();
        for (int y = (int) minMax.getMinY(); y < minMax.getMaxY(); y++) {
            Point lastPoint = new Point(0,0);
            ArrayList<Point> intersectionPoints = new ArrayList<>();
            int intersections = 1;

            for (Point[] edge: edges) {
                int firstX = (int) edge[0].getX();
                int firstY = (int) edge[0].getY();
                int secondX = (int) edge[1].getX();
                int secondY = (int) edge[1].getY();

                if (y < firstY || y > secondY) continue;

                int deltaX = secondX - firstX;
                int deltaY = secondY - firstY;

                // Avoid division by 0
                deltaX = deltaX == 0 ? 1 : deltaX;
                deltaY = deltaY == 0 ? 1 : deltaY;

                int x = (int) Math.round(firstX + (double) deltaX / deltaY * (y - firstY));

                if (secondY > y) {
                    if (intersections % 2 == 0 && intersections != 0) {
                        intersectionPoints.add(lastPoint);
                        intersectionPoints.add(new Point(x,y));
                    }
                    lastPoint = new Point(x,y);
                    intersections++;
                }
            }
            scanlines.add(intersectionPoints);
        }

        for (int i = 0; i < scanlines.size() - 1; i++) {
            ArrayList<Point> drawingPoints = scanlines.get(i);
            drawingPoints.sort(Comparator.comparingDouble(Point::getX));

            for (int j = 0; j < drawingPoints.size() - 1; j += 2) {
                Point firstPoint = drawingPoints.get(j);
                Point secondPoint = drawingPoints.get(j + 1);

                MinMax pointMinMax = new MinMax(new ArrayList<>(
                        Arrays.asList(firstPoint, secondPoint)), lineWidth);

                boolean[] line = Line.plot(firstPoint, secondPoint, lineWidth);
                combinePixels(
                        (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                        line, (int) pointMinMax.width(),
                        pixels, width);
            }
        }

        return pixels;
    }
}
