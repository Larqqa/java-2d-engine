package engine.visuals.renderable.shapes;

import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.List;

public class Line extends Shape {
    // Brasenham's line algorithm
    // Draws squares of variable sizes to simulate bigger lines
    // https://www.youtube.com/watch?v=IDFB5CDpLDE&t=164s
    public static boolean[] plot(Point firstPoint, Point secondPoint, int lineWidth) {
        ArrayList<Point> points = new ArrayList<>(List.of(firstPoint, secondPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        points = normalizePoints(points, minMax);
        minMax = new MinMax(points, 0);

        int firstX = (int) points.get(0).getX();
        int firstY = (int) points.get(0).getY();
        int secondX = (int) points.get(1).getX();
        int secondY = (int) points.get(1).getY();

        int width = (int) minMax.getMaxX() + lineWidth;
        int height = (int) minMax.getMaxY() + lineWidth;

        boolean[] pixels = new boolean[width * height];

        int run = secondX - firstX;
        int rise = secondY - firstY;

        if (run == 0) {
            if (secondY < firstY) {
                int temp = firstY;
                firstY = secondY;
                secondY = temp;
            }

            for (int y = firstY; y <= secondY; y++) {
                square(firstX, y, lineWidth, pixels, width);
            }
        } else {
            float slope = (float) rise / run;
            int adjust = slope >= 0 ? 1 : -1;
            float offset = 0;
            double threshold = 0.5;

            if (slope <= 1 && slope >= -1) {
                float delta = Math.abs(slope);
                int y = firstY;

                if (secondX < firstX) {
                    int temp = firstX;
                    firstX = secondX;
                    secondX = temp;
                    y = secondY;
                }

                for (int x = firstX; x <= secondX; x++) {
                    square(x, y, lineWidth, pixels, width);
                    offset += delta;

                    if (offset >= threshold) {
                        y += adjust;
                        threshold += 1;
                    }
                }
            } else {
                float delta = Math.abs((float) run / rise);
                int x = firstX;

                if (secondY < firstY) {
                    int temp = firstY;
                    firstY = secondY;
                    secondY = temp;

                    x = secondX;
                }

                for (int y = firstY; y <= secondY; y++) {
                    square(x, y, lineWidth, pixels, width);
                    offset += delta;

                    if (offset >= threshold) {
                        x += adjust;
                        threshold += 1;
                    }
                }
            }
        }

        return pixels;
    }
}