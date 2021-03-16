package engine.utilities;

import engine.Program;
import lombok.Getter;

import java.util.ArrayList;

public class MinMax {
    @Getter private int minX = Program.getWidth();
    @Getter private int minY = Program.getHeight();
    @Getter private int maxX = 0;
    @Getter private int maxY = 0;

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
}