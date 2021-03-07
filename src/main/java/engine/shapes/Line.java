package engine.shapes;

import program.Settings;

public class Line {
    private int width;
    private int height;

    public Line(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
    }

    // Brasenham's line algorithm
    // https://www.youtube.com/watch?v=IDFB5CDpLDE&t=164s
    public boolean[] noWeight(int x1, int y1, int x2, int y2) {
        int maxX = Math.max(x1, x2) + 1;
        int maxY = Math.max(y1, y2) + 1;
        boolean[] pixels = new boolean[maxX * maxY];

        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                pixels[y * maxX + x1] = true;
            }
            return pixels;
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
                pixels[y * maxX + x] = true;

                offset += delta;

                if (offset >= threshold) {
                    y += adjust;
                    threshold += 1;
                }
            }

            return pixels;
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
            pixels[y * maxX + x] = true;

            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
        return pixels;
    }

    private void square(int x, int y, int size, boolean[] pixels, int wd) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                pixels[(y + j) * wd + (x + i)] = true;
            }
        }
    }

    // Bresenham with width, by drawing squares instead of pixels
    public boolean[] withWeight(int x1, int y1, int x2, int y2, int width) {
        int maxX = Math.max(x1, x2) + width;
        int maxY = Math.max(y1, y2) + width;
        boolean[] pixels = new boolean[maxX * maxY];

        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                square(x1, y, width, pixels, maxX);
            }
            return pixels;
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
                square(x, y, width, pixels, maxX);


                offset += delta;

                if (offset >= threshold) {
                    y += adjust;
                    threshold += 1;
                }
            }

            return pixels;
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
            square(x, y, width, pixels, maxX);

            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
        return pixels;
    }
}
