package engine.renderer.shapes;

import engine.utilities.Point;

public class Line extends Shape {
    // Brasenham's line algorithm
    // Draws squares of variable sizes to simulate bigger lines
    // https://www.youtube.com/watch?v=IDFB5CDpLDE&t=164s
    public static boolean[] plot(Point firstPoint, Point secondPoint, int lineWidth) {
        int minX = Math.min(firstPoint.getX(), secondPoint.getX());
        int minY = Math.min(firstPoint.getY(), secondPoint.getY());

        int firstX = firstPoint.getX() - minX;
        int firstY = firstPoint.getY() - minY;
        int secondX = secondPoint.getX() - minX;
        int secondY = secondPoint.getY() - minY;

        int width = Math.max(firstX, secondX) + lineWidth;
        int height = Math.max(firstY, secondY) + lineWidth;

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
        }else {
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