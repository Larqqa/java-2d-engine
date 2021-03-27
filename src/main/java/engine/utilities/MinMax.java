package engine.utilities;

import engine.program.Program;

import java.util.ArrayList;

public class MinMax {
    private int minX = Integer.MAX_VALUE;
    private int minY = Integer.MAX_VALUE;
    private int maxX = Integer.MIN_VALUE;
    private int maxY = Integer.MIN_VALUE;

    public MinMax(final ArrayList<Point> pointArray, int offSet) {
        for (Point point : pointArray) {
            int x = (int) point.getX();
            int y = (int) point.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x >= maxX) maxX = x;
            if (y >= maxY) maxY = y;
        }
        maxX += offSet;
        maxY += offSet;
    }

    public String toString() {
        return "{ minX: " + minX +", minY: "+ minY +", maxX: "+ maxX +", maxY: "+ maxY +"}";
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