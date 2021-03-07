package engine.shapes;

import program.Settings;

public class Circle {
    private int width;
    private int height;

    public Circle(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
    }

    private void xLine(int x1, int x2, int y, boolean[] pixels, int rad) {
        while (x1 <= x2) {
            pixels[y * (rad * 2) + x1++] = true;
        }
    }

    private void yLine(int x, int y1, int y2, boolean[] pixels, int rad) {
        while (y1 <= y2) {
            pixels[y1++ * (rad * 2) + x] = true;
        }
    }

    public boolean[] noFill(int x1, int y1, int outerRadius, int innerRadius) {
        innerRadius = innerRadius <= 0 ? outerRadius : innerRadius - 1;
        innerRadius = outerRadius - innerRadius;

        int circleWidth = (outerRadius) * 2;
        boolean[] pixels = new boolean[circleWidth * circleWidth];

        int xo = outerRadius - 1;
        int xi = innerRadius - 1;
        int y = 0;
        int erro = 1 - xo;
        int erri = 1 - xi;

        int cx = outerRadius;
        int cy = outerRadius;

        while(xo >= y) {
            xLine(cx + xi, cx + xo, cy + y, pixels, outerRadius);
            yLine(cx + y, cy + xi, cy + xo, pixels, outerRadius);

            xLine(cx - xo, cx - xi, cy + y, pixels, outerRadius);
            yLine(cx - y, cy + xi, cy + xo, pixels, outerRadius);

            xLine(cx - xo, cx - xi, cy - y, pixels, outerRadius);
            yLine(cx - y, cy - xo, cy - xi, pixels, outerRadius);

            xLine(cx + xi, cx + xo, cy - y, pixels, outerRadius);
            yLine(cx + y, cy - xo, cy - xi, pixels, outerRadius);

            y++;

            if (erro < outerRadius) {
                erro += 2 * (y + 1);
            } else {
                xo--;
                erro += 2 * (y - xo + 1);
            }

            if (y > innerRadius) {
                xi = y;
            } else {
                if (erri < 0) {
                    erri += 2 * y + 1;
                } else {
                    xi--;
                    erri += 2 * (y - xi + 1);
                }
            }
        }

        return pixels;
    }
}
