package engine.shapes;

import engine.Program;

public class Pixel {
    private final int[] pixels;

    public Pixel(int[] pixels) {
        this.pixels = pixels;
    }

    public void colorPixel(int x, int y, int r, int g, int b) {
        if (x < 0 || y < 0) return;
        if (x >= Program.width || y >= Program.height) return;

        pixels[y * Program.width + x] =
                            0xFF000000 |
                (r << 16) & 0x00FF0000 |
                (g << 8)  & 0x0000FF00 |
                 b        & 0x000000FF;
    }

    // https://en.wikipedia.org/wiki/Alpha_compositing#Alpha_blending
    public void colorPixel(int x, int y, int r, int g, int b, int a) {
        if (x < 0 || y < 0 || x >= Program.width || y >= Program.height) return;

        double aa = a / 255.0;
        double ra = r / 255.0;
        double ga = g / 255.0;
        double ba = b / 255.0;

        int pixel = pixels[y * Program.width + x];
        double ab = ((pixel >> 24) & 0xFF) / 255.0;
        double rb = ((pixel >> 16) & 0xFF) / 255.0;
        double gb = ((pixel >> 8)  & 0xFF) / 255.0;
        double bb = ((pixel)       & 0xFF) / 255.0;

        double ac = (ab * (1 - aa));
        double ao = aa + ac;

        double ro = (ra * aa + rb * ac) / ao;
        double go = (ga * aa + gb * ac) / ao;
        double bo = (ba * aa + bb * ac) / ao;

        pixels[y * Program.width + x] =
                ((int) (255 * ao) << 24) & 0xFF000000 |
                ((int) (255 * ro) << 16) & 0x00FF0000 |
                ((int) (255 * go) << 8)  & 0x0000FF00 |
                 (int) (255 * bo)        & 0x000000FF;
    }
}
