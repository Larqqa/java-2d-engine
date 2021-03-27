package engine.visuals.renderable.shapes;

import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class BezierCurve extends Shape {

    private static Point getPointOnLineAtPercentile(Point firstPoint, Point secondPoint, double percent) {
        Point vector = new Point(secondPoint.getX() - firstPoint.getX(), secondPoint.getY() - firstPoint.getY());
        Point unitVector = vector.toUnitVector();

        double length = vector.length();
        double lengthPercentile = length * percent;

        double pointX = firstPoint.getX() + (unitVector.getX() * lengthPercentile);
        double pointY = firstPoint.getY() + (unitVector.getY() * lengthPercentile);

        return new Point(pointX, pointY);
    }

    private static Point plotBezierCurve(ArrayList<Point> points, double percent) {
        ArrayList<Point> newPoints = new ArrayList<>();
        for (int p = 0; p < points.size() - 1; p++) {
            Point firstPoint = points.get(p);
            Point secondPoint = points.get(p + 1);
            newPoints.add(getPointOnLineAtPercentile(firstPoint, secondPoint, percent));
        }

        if (points.size() == 2) {
            return getPointOnLineAtPercentile(points.get(0), points.get(1), percent);
        } else {
            return plotBezierCurve(newPoints, percent);
        }
    }

    // https://javascript.info/bezier-curve
    // https://en.wikipedia.org/wiki/De_Casteljau%27s_algorithm
    // https://math.stackexchange.com/questions/175896/finding-a-point-along-a-line-a-certain-distance-away-from-another-point
    public static boolean[] plot(ArrayList<Point> points, int lineWidth) {
        MinMax minMax = new MinMax(points, lineWidth);
        points = normalizePoints(points, minMax);
        minMax = new MinMax(points, lineWidth);

        int width = (int) minMax.width();
        int height = (int) minMax.height();
        boolean[] pixels = new boolean[width * height];

        double precision = 0.01;
        Point previous = plotBezierCurve(points, precision);
        for (double t = 0; t <= 1.0 + precision; t += precision) {
            Point current = plotBezierCurve(points, t);

            MinMax pointMinMax = new MinMax(new ArrayList<>(Arrays.asList(previous, current)), lineWidth);

            combinePixels(
                    (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                    Line.plot(previous, current, lineWidth), (int) pointMinMax.width(),
                    pixels, width);

            previous = current;
        }

        return pixels;
    }
}
