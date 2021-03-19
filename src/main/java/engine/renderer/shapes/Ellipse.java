package engine.renderer.shapes;

import engine.Program;
import engine.utilities.Point;

import java.util.ArrayList;

public class Ellipse extends Shape {
    private static void plotPoints(int x, int width, int y, int height, ArrayList<Point[]> scanlines) {
        Point[] firstPoints = new Point[2];
        firstPoints[0] = new Point(-x + width, y + height);
        firstPoints[1] = new Point(x + width, y + height);
        scanlines.add(firstPoints);

        Point[] secondPoints = new Point[2];
        secondPoints[0] = new Point(-x + width, -y + height);
        secondPoints[1] = new Point(x + width, -y + height);
        scanlines.add(secondPoints);
    }

    private static ArrayList<Point[]> calculatePoints(int width, int height) {
        int widthSquared = width * width;
        int heightSquared = height * height;
        int twoWidthSquared = 2 * widthSquared;
        int twoHeightSquared = 2 * heightSquared;

        ArrayList<Point[]> scanlines = new ArrayList<>();

        int x = 0;
        int y = height;
        int px = 0;
        int py = twoWidthSquared * y;

        plotPoints(x, width, y, height, scanlines);

        int p1 = (int)(heightSquared - (widthSquared * height) + (0.25 + widthSquared));
        while (px < py) {
            x++;
            px += twoHeightSquared;

            if (p1 < 0) {
                p1 += heightSquared + px;
            } else {
                y--;
                py -= twoWidthSquared;
                p1 += heightSquared + px - py;
            }

            plotPoints(x, width, y, height, scanlines);
        }

        double xOff = x + 0.5;
        int yOff = y - 1;
        int p2 = (int)(heightSquared * xOff * xOff + widthSquared * yOff * yOff - widthSquared * heightSquared);
        while (y > 0) {
            y--;
            py -= twoWidthSquared;

            if (p2 > 0) {
                p2 += widthSquared - py;
            } else {
                x++;
                px += twoHeightSquared;
                p2 += widthSquared + px - py;
            }
            plotPoints(x, width, y, height, scanlines);
        }

        return scanlines;
    }

    // https://www.geeksforgeeks.org/midpoint-ellipse-drawing-algorithm/
    // https://problem4me.wordpress.com/2007/07/28/midpoint-ellipse-java-code/
    public static boolean[] plot(int width, int height, int lineWidth) {
        int widthDiameter = width * 2 + lineWidth;
        int heightDiameter = height * 2 + lineWidth;

        boolean[] pixels = new boolean[widthDiameter * heightDiameter];

        ArrayList<Point[]> scanLines = calculatePoints(width, height);
        for (Point[] line : scanLines) {
            square(line[0].getX(), line[0].getY(), lineWidth, pixels, widthDiameter);
            square(line[1].getX(), line[1].getY(), lineWidth, pixels, widthDiameter);
        }

        return pixels;
    }


    public static boolean[] fill(int width, int height) {
        int widthDiameter = width * 2 + 1;
        int heightDiameter = height * 2 + 1;

        boolean[] pixels = new boolean[widthDiameter * heightDiameter];

        ArrayList<Point[]> scanLines = calculatePoints(width, height);
        for (Point[] line : scanLines) {
            for (int yLine = line[0].getY(); yLine <= line[1].getY(); yLine++) {
                for (int xLine = line[0].getX(); xLine <= line[1].getX(); xLine++) {
                    pixels[yLine * widthDiameter + xLine] = true;
                }
            }
        }

        return pixels;
    }
}
