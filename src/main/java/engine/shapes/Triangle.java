package engine.shapes;

import program.Settings;

public class Triangle {
    private int width;
    private int height;
    private Line line;


    public Triangle(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();

        line = new Line(settings);
    }

    public boolean[] noFill(int x1, int y1, int x2, int y2, int x3, int y3, int width) {
        int minY = Math.min(Math.min(y1,y2), y3);
        int minX = Math.min(Math.min(x1,x2), x3);

        int maxX = Math.max(Math.max(x1,x2), x3) + width;
        int maxY = Math.max(Math.max(y1,y2), y3) + width;

        int wd = maxX - minX;
        int hg = maxY - minY;
        boolean[] triPix = new boolean[wd * hg];

        triLines(x1,y1,x2,y2, triPix, wd, width);
        triLines(x2,y2,x3,y3, triPix, wd, width);
        triLines(x3,y3,x1,y1, triPix, wd, width);

        return triPix;
    }

    // http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
    public boolean[] fill(int x1, int y1, int x2, int y2, int x3, int y3) {
        int minY = Math.min(Math.min(y1,y2), y3);
        int minX = Math.min(Math.min(x1,x2), x3);

        int maxX = Math.max(Math.max(x1,x2), x3) + 1;
        int maxY = Math.max(Math.max(y1,y2), y3) + 1;

        int wd = maxX - minX;
        int hg = maxY - minY;
        boolean[] triPix = new boolean[wd * hg];

        triLines(x1,y1,x2,y2, triPix, wd, 1);
        triLines(x2,y2,x3,y3, triPix, wd, 1);
        triLines(x3,y3,x1,y1, triPix, wd, 1);

        int vsx1 = x2 - x1;
        int vsy1 = y2 - y1;

        int vsx2 = x3 - x1;
        int vsy2 = y3 - y1;

        for (int y = minY; y < maxY; y++)  {
            for (int x = minX; x < maxX; x++) {
                int vsx3 = x - x1;
                int vsy3 = y - y1;

                float s = (float)(vsx3 * vsy2 - vsy3 * vsx2) / (float)(vsx1 * vsy2 - vsy1 * vsx2);
                float t = (float)(vsx1 * vsy3 - vsy1 * vsx3) / (float)(vsx1 * vsy2 - vsy1 * vsx2);

                if ((s >= 0) && (t >= 0) && (s + t <= 1)) {
                    triPix[(y - minY) * wd + (x - minX)] = true;
                }
            }
        }

        return triPix;
    }

    private void square(int x, int y, int size, boolean[] pixels, int wd) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                pixels[(y + j) * wd + (x + i)] = true;
            }
        }
    }

    // Bresenhams lines
    private void triLines(int x1, int y1, int x2, int y2, boolean[] triPixels, int wd, int size) {
        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                square(x1, y, size, triPixels, wd);
            }
            return;
        }

        float m = (float) rise / run;
        int adjust = m >= 0 ? 1 : -1;
        float offset = 0;
        double threshold = 0.5;
        if (m <= 1 && m >= -1) {
            float delta = Math.abs(m);
            int y = y1;

            if (x2 < x1) {
                int tempx = x1;
                x1 = x2;
                x2 = tempx;
                y = y2;
            }

            for (int x = x1; x < x2; x++) {
                square(x, y, size, triPixels, wd);

                offset += delta;

                if (offset >= threshold) {
                    y += adjust;
                    threshold += 1;
                }
            }

            return;
        }

        float delta = Math.abs((float) run / rise);
        int x = x1;

        if (y2 < y1) {
            int tempy = y1;
            y1 = y2;
            y2 = tempy;
            x = x2;
        }

        for (int y = y1; y < y2; y++) {
            square(x, y, size, triPixels, wd);
            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
    }
}
