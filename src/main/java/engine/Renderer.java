package engine;

import program.Settings;
import java.util.Arrays;

public class Renderer {
    private int width;
    private int height;
    private int[] pixels;
    private int clearColor;

    public Renderer(Settings settings, PixelCanvas canvas) {
        width = settings.getWidth();
        height = settings.getHeight();
        clearColor = settings.getClearColor();
        pixels = canvas.getPixels();
    }

    public void square(int x, int y, int size, int weight, int r, int g, int b, int a) {
        int half = size / 2;
        x = x - half;
        y = y - half;

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (y + j >= height || y + j < 0) continue;
                if (x + i >= width || x + i < 0) continue;

                if (j < weight || j > size - (1 + weight) || i < weight || i > size - (1 + weight)) {
                    colorPixel(x + i, y + j, r, g, b, a);
                }
            }
        }
    }

    public void squareFill(int x, int y, int size, int r, int g, int b, int a) {
        int half = size / 2;
        x = x - half;
        y = y - half;

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (y + j >= height || y + j < 0) continue;
                if (x + i >= width || x + i < 0) continue;

                colorPixel(x + i, y + j, r, g, b, a);
            }
        }
    }


    // Brasenham's line
    // https://www.youtube.com/watch?v=IDFB5CDpLDE&t=164s
    public void line(int x1, int y1, int x2, int y2, int r, int g, int b, int a) {
        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                colorPixel(x1, y, r, g, b, a);
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
                colorPixel(x, y, r, g, b, a);
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
            colorPixel(x, y, r, g, b, a);
            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
    }

    // Bresenham with width, by squares instead of pixels
    public void line(int x1, int y1, int x2, int y2, int width, int r, int g, int b, int a) {
        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                squareFill(x1, y, width, r,g,b,a);
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
                squareFill(x, y, width, r,g,b,a);
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
            squareFill(x, y, width, r,g,b,a);
            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
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

    // https://stackoverflow.com/questions/27755514/circle-with-thickness-drawing-algorithm
    public void circle(int x1, int y1, int outerRadius, int innerRadius, int r, int g, int b, int a) {
        innerRadius = innerRadius <= 0 ? outerRadius : innerRadius - 1;
        innerRadius = outerRadius - innerRadius;

        x1 -= outerRadius;
        y1 -= outerRadius;

        int circleWidth = (outerRadius) * 2;
        boolean[] circlePixels = new boolean[circleWidth * circleWidth];

        int xo = outerRadius - 1;
        int xi = innerRadius - 1;
        int y = 0;
        int erro = 1 - xo;
        int erri = 1 - xi;

        int cx = outerRadius;
        int cy = outerRadius;

        while(xo >= y) {
            xLine(cx + xi, cx + xo, cy + y, circlePixels, outerRadius);
            yLine(cx + y, cy + xi, cy + xo, circlePixels, outerRadius);

            xLine(cx - xo, cx - xi, cy + y, circlePixels, outerRadius);
            yLine(cx - y, cy + xi, cy + xo, circlePixels, outerRadius);

            xLine(cx - xo, cx - xi, cy - y, circlePixels, outerRadius);
            yLine(cx - y, cy - xo, cy - xi, circlePixels, outerRadius);

            xLine(cx + xi, cx + xo, cy - y, circlePixels, outerRadius);
            yLine(cx + y, cy - xo, cy - xi, circlePixels, outerRadius);

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

        for (int i = 0; i < circlePixels.length; i++) {
            if (circlePixels[i]) {
                cx = x1 + (i % circleWidth);
                cy = y1 + (i / circleWidth);
                colorPixel(cx, cy, r, g, b, a);
            }
        }
    }

    public void circle(int x1, int y1, int radius, int r, int g, int b, int a) {
        circle(x1, y1, radius, radius, r, g, b, a);
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3, int width, int r, int g, int b, int a) {
        line(x1,y1,x2,y2,width, r,g,b,a);
        line(x2,y2,x3,y3,width,r,g,b,a);
        line(x3,y3,x1,y1,width,r,g,b,a);
    }

    // Lines, but drawn in to a boolean array
    private void triLines(int x1, int y1, int x2, int y2, boolean[] triPixels, int wd) {
        int run = x2 - x1;
        int rise = y2 - y1;

        if (run == 0) {
            if (y2 < y1) {
                int tempy = y1;
                y1 = y2;
                y2 = tempy;
            }

            for (int y = y1; y < y2; y++) {
                triPixels[y * wd + x1] = true;
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
                triPixels[y * wd + x] = true;

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
            triPixels[y * wd + x] = true;
            offset += delta;

            if (offset >= threshold) {
                x += adjust;
                threshold += 1;
            }
        }
    }

    // http://www.sunshine2k.de/coding/java/TriangleRasterization/TriangleRasterization.html
    public void triangleFill(int x1, int y1, int x2, int y2, int x3, int y3, int r, int g, int b, int a) {
        int maxX = Math.max(Math.max(x1,x2), x3) + 1;
        int minX = Math.min(Math.min(x1,x2), x3);

        int maxY = Math.max(Math.max(y1,y2), y3)  + 1;
        int minY = Math.min(Math.min(y1,y2), y3);

        int wd = maxX - minX;
        int hg = maxY - minY;

        boolean[] triPix = new boolean[wd * hg];
        triLines(x1 - minX,y1 - minY,x2 - minX,y2 - minY,triPix,wd);
        triLines(x2 - minX,y2 - minY,x3 - minX,y3 - minY,triPix,wd);
        triLines(x3 - minX,y3 - minY,x1 - minX,y1 - minY,triPix,wd);

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

        for (int i = 0; i < triPix.length; i++) {
            int cx = (i % wd);
            int cy = (i / wd);
            if (triPix[cy * wd + cx]) {
                colorPixel(cx + minX, cy + minY, r, g, b, a);
            }
        }
    }

    public void clear() {
        Arrays.fill(pixels, clearColor);
    }

    public void test(){
        for(int y = 0; y < height; y++) {
            for(int x = 0; x < width; x++) {
                int r = (int)(255 * (double)x / width);
                int g = (int)(255 * (double)(height - y) / height);
                int b = (int)(255 * 0.25);

                colorPixel(x, y, r, g, b);
            }
        }
    }

    public void colorPixel(int x, int y, int r, int g, int b) {
        if (x < 0 || y < 0) return;
        if (x >= width || y >= height) return;

        pixels[y * width + x] =
                        0xFF000000 |
            (r << 16) & 0x00FF0000 |
            (g << 8)  & 0x0000FF00 |
             b        & 0x000000FF;
    }

    // https://en.wikipedia.org/wiki/Alpha_compositing#Alpha_blending
    public void colorPixel(int x, int y, int r, int g, int b, int a) {
        if (x < 0 || y < 0) return;
        if (x >= width || y >= height) return;

        double aa = a / 255.0;
        double ra = r / 255.0;
        double ga = g / 255.0;
        double ba = b / 255.0;

        int pixel = pixels[y * width + x];
        double ab = ((pixel >> 24) & 0xFF) / 255.0;
        double rb = ((pixel >> 16) & 0xFF) / 255.0;
        double gb = ((pixel >> 8)  & 0xFF) / 255.0;
        double bb = ((pixel)       & 0xFF) / 255.0;

        double ac = (ab * (1 - aa));
        double ao = aa + ac;

        double ro = (ra * aa + rb * ac) / ao;
        double go = (ga * aa + gb * ac) / ao;
        double bo = (ba * aa + bb * ac) / ao;

        pixels[y * width + x] =
                ((int) (255 * ao) << 24) & 0xFF000000 |
                ((int) (255 * ro) << 16) & 0x00FF0000 |
                ((int) (255 * go) << 8)  & 0x0000FF00 |
                 (int) (255 * bo)        & 0x000000FF;
    }
}