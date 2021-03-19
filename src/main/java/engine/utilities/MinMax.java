package engine.utilities;

import engine.Program;
import java.util.ArrayList;

public class MinMax {
    private int minX = Program.getWidth();
    private int minY = Program.getHeight();
    private int maxX = 0;
    private int maxY = 0;

    public MinMax(final ArrayList<Point> pointArray, int offSet) {
        for (Point point : pointArray) {
            int x = point.getX();
            int y = point.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x >= maxX) maxX = x + offSet;
            if (y >= maxY) maxY = y + offSet;
        }
    }

    public int width() {
        return maxX - minX;
    }

    public int height() {
        return maxY - minY;
    }

    public int getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public int getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public int getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public int getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}