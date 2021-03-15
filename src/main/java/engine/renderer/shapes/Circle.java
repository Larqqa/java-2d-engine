package engine.renderer.shapes;

import engine.utilities.Point;

import java.util.ArrayList;

public class Circle extends Shape {
    public static boolean[] plot(int radius, int lineWidth) {
        int diameter = radius * 2 + lineWidth;
        boolean[] pixels = new boolean[diameter * diameter];

        int x = radius;
        int errorOuter = x;
        int y = 0;

        while(x >= y) {
            square(radius + x, radius + y, lineWidth, pixels, diameter);
            square(radius - x, radius + y, lineWidth, pixels, diameter);
            square(radius + x, radius - y, lineWidth, pixels, diameter);
            square(radius - x, radius - y, lineWidth, pixels, diameter);
            square(radius + y, radius + x, lineWidth, pixels, diameter);
            square(radius - y, radius + x, lineWidth, pixels, diameter);
            square(radius + y, radius - x, lineWidth, pixels, diameter);
            square(radius - y, radius - x, lineWidth, pixels, diameter);

            y++;

            if (errorOuter <= radius) {
                errorOuter += 2 * (y + 1);
            } else {
                x--;
                errorOuter += 2 * (y - x + 1);
            }
        }

        return pixels;
    }

    public static boolean[] fill(int radius) {
        int diameter = radius * 2 + 1;
        boolean[] pixels = new boolean[diameter * diameter];

        int x = radius;
        int errorOuter = x;
        int y = 0;

        ArrayList<Point[]> scanlines = new ArrayList<>();
        while(x >= y) {
            Point[] points1 = new Point[2];
            points1[0] = new Point(radius + x, radius + y);
            points1[1] = new Point(radius - x, radius + y);
            scanlines.add(points1);
            Point[] points2 = new Point[2];
            points2[0] = new Point(radius + x, radius - y);
            points2[1] = new Point(radius - x, radius - y);
            scanlines.add(points2);
            Point[] points3 = new Point[2];
            points3[0] = new Point(radius + y, radius + x);
            points3[1] = new Point(radius - y, radius + x);
            scanlines.add(points3);
            Point[] points4 = new Point[2];
            points4[0] = new Point(radius + y, radius - x);
            points4[1] = new Point(radius - y, radius - x);
            scanlines.add(points4);

            y++;

            if (errorOuter <= radius) {
                errorOuter += 2 * (y + 1);
            } else {
                x--;
                errorOuter += 2 * (y - x + 1);
            }
        }

        for (Point[] line : scanlines) {
            int x1 = line[0].getX();
            int y1 = line[0].getY();
            int x2 = line[1].getX();
            int y2 = line[1].getY();

            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }

            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }

            for (int yLine = y1; yLine <= y2; yLine++) {
                for (int xLine = x1; xLine <= x2; xLine++) {
                    pixels[yLine * diameter + xLine] = true;
                }
            }
        }

        return pixels;
    }

    private static void xLine(int firstX, int secondX, int y, boolean[] pixels, int diameter) {
        while (firstX <= secondX) {
            pixels[y * diameter + firstX] = true;
            firstX++;
        }
    }

    private static void yLine(int x, int firstY, int secondY, boolean[] pixels, int diameter) {
        while (firstY <= secondY) {
            pixels[firstY * diameter + x] = true;
            firstY++;
        }
    }
}
