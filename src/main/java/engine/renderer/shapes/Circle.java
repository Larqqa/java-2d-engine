package engine.renderer.shapes;

public class Circle extends Shape {
    public static boolean[] plot(int radius, int lineWidth) {
        int diameter = radius * 2 + lineWidth;
        boolean[] pixels = new boolean[diameter * diameter];

        int x = radius;
        int errorOuter = x;
        int y = 0;

        while(x >= y) {
            square(radius + x, radius + y, lineWidth, pixels, diameter);
            square(radius - x, radius + y, lineWidth, pixels, diameter);
            square(radius + x, radius - y, lineWidth, pixels, diameter);
            square(radius - x, radius - y, lineWidth, pixels, diameter);
            square(radius + y, radius + x, lineWidth, pixels, diameter);
            square(radius - y, radius + x, lineWidth, pixels, diameter);
            square(radius + y, radius - x, lineWidth, pixels, diameter);
            square(radius - y, radius - x, lineWidth, pixels, diameter);

            y++;

            if (errorOuter <= radius) {
                errorOuter += 2 * (y + 1);
            } else {
                x--;
                errorOuter += 2 * (y - x + 1);
            }
        }

        return pixels;
    }

    public static boolean[] fill(int radius) {
        int diameter = radius * 2 + 1;
        boolean[] pixels = new boolean[diameter * diameter];

        int xOuter = radius;
        int xInner = 0;
        int errorOuter = xOuter;
        int errorInner = xInner;
        int y = 0;

        while(xOuter >= y) {
            xLine(radius + xInner, radius + xOuter, radius + y, pixels, diameter);
            yLine(radius + y, radius + xInner, radius + xOuter, pixels, diameter);
            xLine(radius - xOuter, radius - xInner, radius + y, pixels, diameter);
            yLine(radius - y, radius + xInner, radius + xOuter, pixels, diameter);
            xLine(radius - xOuter, radius - xInner, radius - y, pixels, diameter);
            yLine(radius - y, radius - xOuter, radius - xInner, pixels, diameter);
            xLine(radius + xInner, radius + xOuter, radius - y, pixels, diameter);
            yLine(radius + y, radius - xOuter, radius - xInner, pixels, diameter);

            y++;

            if (errorOuter <= radius) {
                errorOuter += 2 * (y + 1);
            } else {
                xOuter--;
                errorOuter += 2 * (y - xOuter + 1);
            }

            if (y > 0) {
                xInner = y;
            } else {
                if (errorInner < 0) {
                    errorInner += 2 * (y + 1);
                } else {
                    xInner--;
                    errorInner += 2 * (y - xInner + 1);
                }
            }
        }

        return pixels;
    }

    private static void xLine(int firstX, int secondX, int y, boolean[] pixels, int diameter) {
        while (firstX <= secondX) {
            pixels[y * diameter + firstX] = true;
            firstX++;
        }
    }

    private static void yLine(int x, int firstY, int secondY, boolean[] pixels, int diameter) {
        while (firstY <= secondY) {
            pixels[firstY * diameter + x] = true;
            firstY++;
        }
    }
}
