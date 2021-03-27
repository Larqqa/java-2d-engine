package engine.visuals.renderable.shapes;

import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Arrays;

public class Triangle extends Shape {
    public static boolean[] plot(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth) {
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        points = normalizePoints(points, minMax);
        minMax = new MinMax(points, lineWidth);

        firstPoint = points.get(0);
        secondPoint = points.get(1);
        thirdPoint = points.get(2);

        int width = (int) minMax.width();
        int height = (int) minMax.height();
        boolean[] pixels = new boolean[width * height];

        MinMax pointMinMax = new MinMax(new ArrayList<>(Arrays.asList(firstPoint, secondPoint)), lineWidth);
        combinePixels(
                (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                Line.plot(firstPoint, secondPoint, lineWidth), (int) pointMinMax.width(),
                pixels, width);

        pointMinMax = new MinMax(new ArrayList<>(Arrays.asList(secondPoint, thirdPoint)), lineWidth);
        combinePixels(
                (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                Line.plot(secondPoint, thirdPoint, lineWidth), (int) pointMinMax.width(),
                pixels, width);

        pointMinMax = new MinMax(new ArrayList<>(Arrays.asList(thirdPoint, firstPoint)), lineWidth);
        combinePixels(
                (int) pointMinMax.getMinX(), (int) pointMinMax.getMinY(),
                Line.plot(thirdPoint, firstPoint, lineWidth), (int) pointMinMax.width(),
                pixels, width);

        return pixels;
    }

    // http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
    public static boolean[] fill(Point firstPoint, Point secondPoint, Point thirdPoint) {
        int lineWidth = 1;
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        points = normalizePoints(points, minMax);
        minMax = new MinMax(points, lineWidth);

        firstPoint = points.get(0);
        secondPoint = points.get(1);
        thirdPoint = points.get(2);

        int width = (int) minMax.width();
        int height = (int) minMax.height();
        boolean[] pixels = new boolean[width * height];

        combinePixels(
                0, 0,
                plot(firstPoint, secondPoint, thirdPoint, lineWidth), width,
                pixels, width);

        Point firstVertice = new Point(
                secondPoint.getX() - firstPoint.getX(),
                secondPoint.getY() - firstPoint.getY());

        Point secondVertice = new Point(
                thirdPoint.getX() - firstPoint.getX(),
                thirdPoint.getY() - firstPoint.getY());


        for (int y = (int) minMax.getMinY(); y < minMax.getMaxY(); y++)  {
            for (int x = (int) minMax.getMinX(); x < minMax.getMaxX(); x++) {
                Point thirdVertice = new Point(
                        x - firstPoint.getX(),
                        y - firstPoint.getY());

                float n = (float)(firstVertice.getX() * secondVertice.getY() - firstVertice.getY() * secondVertice.getX());
                float s = (float)(thirdVertice.getX() * secondVertice.getY() - thirdVertice.getY() * secondVertice.getX()) / n;
                float t = (float)(firstVertice.getX() * thirdVertice.getY()  - firstVertice.getY() * thirdVertice.getX())  / n;

                if ((s >= 0) && (t >= 0) && (s + t <= 1)) {
                    pixels[(y - (int) minMax.getMinY()) * width + (x - (int) minMax.getMinX())] = true;
                }
            }
        }

        return pixels;
    }
}
