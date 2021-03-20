package engine.renderer.shapes;

import engine.utilities.Point;

import java.util.ArrayList;

public class Circle extends Shape {

    public static boolean[] fill(int radius) {
        int diameter = radius * 2 + 1;
        boolean[] pixels = new boolean[diameter * diameter];

        ArrayList<Point[]> scanLines = plotPoints(radius);
        for (Point[] line : scanLines) {
            for (int yLine = (int) line[0].getY(); yLine <= line[1].getY(); yLine++) {
                for (int xLine = (int) line[0].getX(); xLine <= line[1].getX(); xLine++) {
                    pixels[yLine * diameter + xLine] = true;
                }
            }
        }

        return pixels;
    }

    public static boolean[] plot(int radius, int lineWidth) {
        int diameter = radius * 2 + lineWidth;
        boolean[] pixels = new boolean[diameter * diameter];

        ArrayList<Point[]> scanLines = plotPoints(radius);
        for (Point[] line : scanLines) {
            square((int) line[0].getX(), (int) line[0].getY(), lineWidth, pixels, diameter);
            square((int) line[1].getX(), (int) line[1].getY(), lineWidth, pixels, diameter);
        }

        return pixels;
    }

    private static ArrayList<Point[]> plotPoints(int radius) {
        int x = radius;
        int error = x;
        int y = 0;

        ArrayList<Point[]> scanlines = new ArrayList<>();
        while(x >= y) {
            Point[] firstPoints = new Point[2];
            firstPoints[0] = new Point(radius - x, radius + y);
            firstPoints[1] = new Point(radius + x, radius + y);
            scanlines.add(firstPoints);

            Point[] secondPoints= new Point[2];
            secondPoints[0] = new Point(radius - x, radius - y);
            secondPoints[1] = new Point(radius + x, radius - y);
            scanlines.add(secondPoints);

            Point[] thirdPoints = new Point[2];
            thirdPoints[0] = new Point(radius - y, radius + x);
            thirdPoints[1] = new Point(radius + y, radius + x);
            scanlines.add(thirdPoints);

            Point[] fourthPoints = new Point[2];
            fourthPoints[0] = new Point(radius - y, radius - x);
            fourthPoints[1] = new Point(radius + y, radius - x);
            scanlines.add(fourthPoints);

            y++;

            if (error <= radius) {
                error += 2 * (y + 1);
            } else {
                x--;
                error += 2 * (y - x + 1);
            }
        }

        return scanlines;
    }
}
