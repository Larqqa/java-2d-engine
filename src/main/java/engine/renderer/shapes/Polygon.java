package engine.renderer.shapes;

import engine.Program;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Comparator;

public class Polygon extends Shape {
    public static boolean[] plot(ArrayList<Point> pointArray, int lineWidth) {
        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (Point value : pointArray) {
            int x = value.getX();
            int y = value.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }

        ArrayList<Point> points = new ArrayList<>();
        for (Point point : pointArray) {
            int x = point.getX() - minX;
            int y = point.getY() - minY;
            points.add(new Point(x, y));
        }

        // Close the loop
        if (points.get(0) != points.get(points.size() - 1)) {
            points.add(points.get(0));
        }

        maxX += lineWidth;
        maxY += lineWidth;

        int width = maxX - minX;
        int height = maxY - minY;

        boolean[] pixels = new boolean[width * height];

        Point lastPoint = points.get(0);
        for (int i = 1; i < points.size(); i++) {
            Point thisPoint = points.get(i);

            minX = Math.min(lastPoint.getX(), thisPoint.getX());
            minY = Math.min(lastPoint.getY(), thisPoint.getY());
            int lineW = (Math.max(lastPoint.getX(), thisPoint.getX()) + lineWidth) - minX;
            combinePixels(minX, minY, Line.plot(lastPoint, thisPoint, lineWidth), lineW, pixels, width);

            lastPoint = thisPoint;
        }

        return pixels;
    }

    // sunshine2k.de/coding/java/Polygon/Filling/FillPolygon.htm
    // https://www.ques10.com/p/11008/explain-scan-line-fill-algorithm-with-an-example-1/
    // https://stackoverflow.com/questions/24469459/scanline-algorithm-how-to-calculate-intersection-points
    public static boolean[] fill(ArrayList<Point> pointArray) {
        boolean[] pixels = plot(pointArray, 1);

        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (Point item : pointArray) {
            int x = item.getX();
            int y = item.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }

        ArrayList<Point> points = new ArrayList<>();
        for (Point value : pointArray) {
            int x = value.getX() - minX;
            int y = value.getY() - minY;
            points.add(new Point(x, y));
        }

        maxX += 1;
        maxY += 1;

        int width = maxX - minX;
        int height = maxY - minY;

        for (Point point : points) {
            int x = point.getX();
            int y = point.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;
        }

        ArrayList<Point[]> edges = new ArrayList<>();
        for (int i = 0; i < points.size() - 1; i++) {
            Point[] pts = new Point[2];
            Point one = points.get(i);
            Point two = points.get(i + 1);

            if (one.getY() > two.getY()) {
                Point temp = one;
                one = two;
                two = temp;
            }

            pts[0] = one;
            pts[1] = two;
            edges.add(pts);
        }

        if (points.get(0) != points.get(points.size() - 1)){
            Point[] pts = new Point[2];
            Point one = points.get(points.size() - 1);
            Point two = points.get(0);

            if (one.getY() > two.getY()) {
                Point temp = one;
                one = two;
                two = temp;
            }

            pts[0] = one;
            pts[1] = two;
            edges.add(pts);
        }

        edges.sort(Comparator.comparingInt(edge -> edge[0].getY()));

        ArrayList<ArrayList<Point>> fillPairs = new ArrayList<>();
        for (int y = minY; y < maxY; y++) {
            int intersections = 1;
            Point first = new Point(0,0);
            ArrayList<Point> intersectionPoints = new ArrayList<>();

            for (Point[] edge: edges) {
                int x1 = edge[0].getX();
                int y1 = edge[0].getY();
                int x2 = edge[1].getX();
                int y2 = edge[1].getY();

                if (y < y1 || y > y2) continue;

                int deltaX = x2 - x1;
                int deltaY = y2 - y1;
                deltaX = deltaX == 0 ? 1 : deltaX;
                deltaY = deltaY == 0 ? 1 : deltaY;

                int x = (int)Math.floor(x1 + (double)deltaX / deltaY * (y - y1));

                if ((y1 <= y && y2 > y) || (y2 <= y && y1 > y)) {
                    if (intersections % 2 == 0 && intersections != 0) {
                        intersectionPoints.add(first);
                        intersectionPoints.add(new Point(x,y));
                    }
                    first = new Point(x,y);
                    intersections++;
                }
            }
            fillPairs.add(intersectionPoints);
        }

        for (int i = 0; i < fillPairs.size() - 1; i++) {
            ArrayList<Point> drawingPoints = fillPairs.get(i);
            drawingPoints.sort(Comparator.comparingInt(Point::getX));

            for (int j = 0; j < drawingPoints.size() - 1; j += 2) {
                Point firstPoint = drawingPoints.get(j);
                Point secondPoint = drawingPoints.get(j + 1);

                minX = Math.min(firstPoint.getX(), secondPoint.getX());
                minY = Math.min(firstPoint.getY(), secondPoint.getY());
                int lineW = (Math.max(firstPoint.getX(), secondPoint.getX()) + 1) - minX;
                boolean[] line = Line.plot(firstPoint, secondPoint, 1);
                combinePixels(minX, minY, line, lineW, pixels, width);
            }
        }

        return pixels;
    }
}
