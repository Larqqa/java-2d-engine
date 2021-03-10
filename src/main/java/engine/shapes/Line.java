package engine.shapes;

public class Line {
    public Line() { }

    // Brasenham's line algorithm
    // Draws squares of variable sizes to simulate bigger lines
    // https://www.youtube.com/watch?v=IDFB5CDpLDE&t=164s
    public boolean[] draw(int x1, int y1, int x2, int y2, int width) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        x1 -= minX;
        y1 -= minY;
        x2 -= minX;
        y2 -= minY;

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

    private void square(int x, int y, int size, boolean[] pixels, int wd) {
        if (size <= 1) {
            pixels[y * wd + x] = true;
            return;
        }

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                pixels[(y + j) * wd + (x + i)] = true;
            }
        }
    }
}
