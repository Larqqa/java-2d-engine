package engine.visuals.renderable.shapes;

import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;

public abstract class Shape {
    public static void square(int x, int y, int size, boolean[] pixels, int wd) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                int p = (y + j) * wd + (x + i);
                if (p < 0 || p > pixels.length - 1) continue;
                pixels[p] = true;
            }
        }
    }

    public static void combinePixels(int offsetX, int offsetY, boolean[] shape, int shapeWidth, boolean[] target, int targetWidth) {
        for (int i = 0; i < shape.length; i++) {
            int x = (i % shapeWidth);
            int y = (i / shapeWidth);
            if (shape[i]) {
                int p = (offsetY + y) * targetWidth + (offsetX + x);
                if (p < 0 || p > target.length - 1) continue;
                target[p] = true;
            }
        }
    }

    public static ArrayList<Point> normalizePoints(ArrayList<Point> pointArray, MinMax minMax) {
        ArrayList<Point> points = new ArrayList<>();
        for (Point point : pointArray) {
            int x = (int) (point.getX() - minMax.getMinX());
            int y = (int) (point.getY() - minMax.getMinY());
            points.add(new Point(x, y));
        }
        return points;
    }
}
