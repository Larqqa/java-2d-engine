package engine.renderer.shapes;

import engine.Program;
import engine.utilities.Point;

import java.lang.reflect.Array;
import java.sql.SQLOutput;
import java.util.ArrayList;

import static engine.renderer.shapes.Shape.combinePixels;

public class Polygon {
    public static boolean[] plot(ArrayList<Point> points, int lineWidth) {
        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).getX();
            int y = points.get(i).getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).getX() - minX;
            int y = points.get(i).getY() - minY;
            points.set(i, new Point(x, y));
        }

        points.add(points.get(0));

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

    public static boolean[] fill(ArrayList<Point> points) {
        boolean[] pixels = plot(points, 1);

        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (int i = 0; i < points.size(); i++) {
            int x = points.get(i).getX();
            int y = points.get(i).getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
        maxX += 1;
        maxY += 1;

        int width = maxX - minX;
        int height = maxY - minY;

        ArrayList<int[]> fillPoints = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            int pixelCount = 0;
            Integer first = 0;

            for (int x = 0; x < width; x++) {
                int location = y * width + x;

                if (pixels[location]) {
                    pixelCount++;

                    if (pixelCount % 2 == 0) {
                        int[] linePoints = new int[2];
                        linePoints[0] = first;
                        linePoints[1] = location;
                        fillPoints.add(linePoints);

                        pixelCount = 0;
                    } else {
                        first = location;
                    }
                }
            }

        }

        for (int i = 0; i < fillPoints.size(); i++) {
            int[] fillers = fillPoints.get(i);

            if (fillers[0] == 0) continue;
            for (int j = fillers[0]; j < fillers[1]; j++) {
                pixels[j] = true;
            }
        }

        return pixels;
    }
}
