package engine.renderer.shapes;

import engine.Program;
import engine.utilities.Point;

import java.util.ArrayList;

public class Ellipse extends Shape {
    // https://www.geeksforgeeks.org/midpoint-ellipse-drawing-algorithm/
    public static boolean[] plot(int width, int height, int lineWidth) {
        width += lineWidth;
        height += lineWidth;
        int widthDiameter = width * 2 + lineWidth;
        int heightDiameter = height * 2 + lineWidth;

        boolean[] pixels = new boolean[widthDiameter * heightDiameter];

        System.out.println(widthDiameter);

        int x = 0;
        int y = height;
        double d1 = (height * height)
                - (width * width * width)
                + (0.25 * width * width);
        int dx = 2 * height * height * x;
        int dy = 2 * width * width * y;

        while(dx < dy) {
            System.out.println(x +" "+ (x + width));
            square(x + width, y + height, lineWidth, pixels, widthDiameter);
            square(-x + width, y + height, lineWidth, pixels, widthDiameter);
            square(x + width, -y + height, lineWidth, pixels, widthDiameter);
            square(-x + width, -y + height, lineWidth, pixels, widthDiameter);

            if (d1 < 0) {
                x++;
                dx = dx + (2 * height * height);
                d1 = d1 + dx + (height * height);
            } else {
                x++;
                y--;
                dx = dx + (2 * height * height);
                dy = dy - (2 * width * width);
                d1 = d1 + dx - dy + (height * height);
            }
        }

        double d2 = ((height * height) * ((x + 0.5) * (x + 0.5)))
                + ((width * width) * ((y - 1) * (y - 1)))
                - (width * width * height * height);

        while(y >= 0) {
            square(x + width, y + height, lineWidth, pixels, widthDiameter);
            square(-x + width, y + height, lineWidth, pixels, widthDiameter);
            square(x + width, -y + height, lineWidth, pixels, widthDiameter);
            square(-x + width, -y + height, lineWidth, pixels, widthDiameter);

            if (d2 > 0) {
                y--;
                dy = dy - (2 * width * width);
                d2 = d2 + (width * width) - dy;
            } else {
                x++;
                y--;
                dx = dx + (2 * height * height);
                dy = dy - (2 * width * width);
                d2 = d2 + dx - dy + (width * width);
            }
        }

        return pixels;
    }

    public static boolean[] fill(int width, int height) {
        int widthDiameter = width * 2 + 1;
        int heightDiameter = height * 2 + 1;

        boolean[] pixels = new boolean[widthDiameter * heightDiameter];

        int x = 0;
        int y = height;
        double d1 = (height * height) - (width * width * width) + (0.25 * width * width);
        int dx = 2 * height * height * x;
        int dy = 2 * width * width * y;

        ArrayList<Point[]> scanlines = new ArrayList<>();
        while(dx < dy) {
            Point[] positives = new Point[2];
            positives[0] = new Point(x + width, y + height);
            positives[1] = new Point(-x + width, y + height);
            scanlines.add(positives);

            Point[] negatives = new Point[2];
            negatives[0] = new Point(x + width, -y + height);
            negatives[1] = new Point(-x + width, -y + height);
            scanlines.add(negatives);

            if (d1 < 0) {
                x++;
                dx = dx + (2 * height * height);
                d1 = d1 + dx + (height * height);
            } else {
                x++;
                y--;
                dx = dx + (2 * height * height);
                dy = dy - (2 * width * width);
                d1 = d1 + dx - dy + (height * height);
            }
        }

        double d2 = ((height * height) * ((x + 0.5) * (x + 0.5)))
                + ((width * width) * ((y - 1) * (y - 1)))
                - (width * width * height * height);

        while(y >= 0) {
            Point[] positives = new Point[2];
            positives[0] = new Point(x + width, y + height);
            positives[1] = new Point(-x + width, y + height);
            scanlines.add(positives);

            Point[] negatives = new Point[2];
            negatives[0] = new Point(x + width, -y + height);
            negatives[1] = new Point(-x + width, -y + height);
            scanlines.add(negatives);

            if (d2 > 0) {
                y--;
                dy = dy - (2 * width * width);
                d2 = d2 + (width * width) - dy;
            } else {
                x++;
                y--;
                dx = dx + (2 * height * height);
                dy = dy - (2 * width * width);
                d2 = d2 + dx - dy + (width * width);
            }
        }

        for (Point[] line : scanlines) {
            int x1 = line[0].getX();
            int y1 = line[0].getY();
            int x2 = line[1].getX();
            int y2 = line[1].getY();

            if (x1 > x2) {
                int temp = x1;
                x1 = x2;
                x2 = temp;
            }

            if (y1 > y2) {
                int temp = y1;
                y1 = y2;
                y2 = temp;
            }

            for (int yLine = y1; yLine <= y2; yLine++) {
                for (int xLine = x1; xLine <= x2; xLine++) {
                    pixels[yLine * widthDiameter + xLine] = true;
                }
            }
        }

        return pixels;
    }
}
