package engine.renderer.shapes;

import engine.utilities.Point;

public class Triangle extends Shape {
    public static boolean[] plot(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());

        firstPoint = new Point(firstPoint.getX() - minX, firstPoint.getY() - minY);
        secondPoint = new Point(secondPoint.getX() - minX, secondPoint.getY() - minY);
        thirdPoint = new Point(thirdPoint.getX() - minX, thirdPoint.getY() - minY);

        minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + lineWidth;
        int maxY = Math.max(Math.max(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY()) + lineWidth;

        int width = maxX - minX;
        int height = maxY - minY;
        boolean[] pixels = new boolean[width * height];

        minX = Math.min(firstPoint.getX(), secondPoint.getX());
        minY = Math.min(firstPoint.getY(), secondPoint.getY());
        int lineW = (Math.max(firstPoint.getX(), secondPoint.getX()) + lineWidth) - minX;
        combinePixels(minX, minY, Line.plot(firstPoint, secondPoint, lineWidth), lineW, pixels, width);

        minX = Math.min(secondPoint.getX(), thirdPoint.getX());
        minY = Math.min(secondPoint.getY(), thirdPoint.getY());
        lineW = (Math.max(secondPoint.getX(), thirdPoint.getX()) + lineWidth) - minX;
        combinePixels(minX, minY, Line.plot(secondPoint, thirdPoint, lineWidth), lineW, pixels, width);

        minX = Math.min(thirdPoint.getX(), firstPoint.getX());
        minY = Math.min(thirdPoint.getY(), firstPoint.getY());
        lineW = (Math.max(thirdPoint.getX(), firstPoint.getX()) + lineWidth) - minX;
        combinePixels(minX, minY, Line.plot(thirdPoint, firstPoint, lineWidth), lineW, pixels, width);

        return pixels;
    }

    public static boolean[] fill(Point firstPoint, Point secondPoint, Point thirdPoint) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());

        firstPoint = new Point(firstPoint.getX() - minX, firstPoint.getY() - minY);
        secondPoint = new Point(secondPoint.getX() - minX, secondPoint.getY() - minY);
        thirdPoint = new Point(thirdPoint.getX() - minX, thirdPoint.getY() - minY);

        minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + 1;
        int maxY = Math.max(Math.max(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY()) + 1;

        int width = maxX - minX;
        int height = maxY - minY;
        boolean[] pixels = new boolean[width * height];
        boolean[] outline = plot(firstPoint, secondPoint,thirdPoint, 1);
        combinePixels(0,0,outline, width, pixels, width);

        int vsx1 = secondPoint.getX() - firstPoint.getX();
        int vsy1 = secondPoint.getY() - firstPoint.getY();

        int vsx2 = thirdPoint.getX() - firstPoint.getX();
        int vsy2 = thirdPoint.getY() - firstPoint.getY();

        for (int y = minY; y < maxY; y++)  {
            for (int x = minX; x < maxX; x++) {
                int vsx3 = x - firstPoint.getX();
                int vsy3 = y - firstPoint.getY();

                float s = (float)(vsx3 * vsy2 - vsy3 * vsx2) / (float)(vsx1 * vsy2 - vsy1 * vsx2);
                float t = (float)(vsx1 * vsy3 - vsy1 * vsx3) / (float)(vsx1 * vsy2 - vsy1 * vsx2);

                if ((s >= 0) && (t >= 0) && (s + t <= 1)) {
                    pixels[(y - minY) * width + (x - minX)] = true;
                }
            }
        }

        return pixels;
    }
}
