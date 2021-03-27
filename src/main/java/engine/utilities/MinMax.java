package engine.utilities;

import java.util.ArrayList;

public class MinMax {
    private double minX = Double.MAX_VALUE;
    private double minY = Double.MAX_VALUE;
    private double maxX = Double.MIN_VALUE;
    private double maxY = Double.MIN_VALUE;

    public MinMax(final ArrayList<Point> pointArray, double offSet) {
        for (Point point : pointArray) {
            double x = point.getX();
            double y = point.getY();

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

    public double width() {
        return maxX - minX;
    }

    public double height() {
        return maxY - minY;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(int minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(int minY) {
        this.minY = minY;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(int maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(int maxY) {
        this.maxY = maxY;
    }
}