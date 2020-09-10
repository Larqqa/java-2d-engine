package Engine;

import java.awt.image.DataBufferInt;

public class Renderer {
    private final int[] pixels;
    private final int[] depth;
    private final int pWidth, pHeight;
    private final int clearColor = 0x00000000;

    public Renderer(Window window) {
        this.pWidth = window.getImage().getWidth();
        this.pHeight = window.getImage().getHeight();
        this.pixels = ((DataBufferInt)window.getImage().getRaster().getDataBuffer()).getData();
        this.depth = new int[pixels.length];
    }

    public void clear() {
        for (int i = 0; i < pixels.length; i++) {
            pixels[i] = clearColor;
        }
    }

    public void setPixel(int x, int y, int color) {
        if(x < 0 || x >= pWidth || y < 0 || y >= pHeight)
            return;
        pixels[x + y * pWidth] = color;
    }

    public void draw2DRect(int x, int y, int width, int height, int color) {
        for(int i = x; i < x + width; i++) {
            setPixel(i, y, color);
            setPixel(i, y + height - 1, color);
        }

        for(int i = y; i < y + height; i++) {
            setPixel(x, i, color);
            setPixel(x + width - 1, i, color);
        }
    }

    public void draw2DFillRect(int x, int y, int width, int height, int color) {
        for(int j = y ; j < y + height; j++) {
            for(int i = x; i < x + width; i++) {
                setPixel(i, j, color);
            }
        }
    }

    private float sign (int [] one, int [] two, int [] three)
    {
        return (one[0] - three[0]) * (two[1] - three[1]) - (two[0] - three[0]) * (one[1] - three[1]);
    }

    public void draw2DFillTriangle(int [] one, int [] two, int [] three, int color) {
        for (int y = 0; y < getPHeight(); y++)  {
            for (int x = 0; x < getPWidth(); x++) {
                float d1, d2, d3;
                boolean has_neg, has_pos;

                int [] point = {x, y};

                d1 = sign(point, one, two);
                d2 = sign(point, two, three);
                d3 = sign(point, three, one);

                has_neg = (d1 < 0) || (d2 < 0) || (d3 < 0);
                has_pos = (d1 > 0) || (d2 > 0) || (d3 > 0);

                if (!(has_neg && has_pos)) {
                    setPixel(point[0], point[1], color);
                }
            }
        }
    }


    private boolean pointOnLine(int [] a, int [] b, int [] p) {
        int ab = (int) Math.sqrt(Math.pow(b[0] - a[0], 2) + Math.pow(b[1] - a[1], 2));
        int ap = (int) Math.sqrt(Math.pow(p[0] - a[0], 2) + Math.pow(p[1] - a[1], 2));
        int pb = (int) Math.sqrt(Math.pow(b[0] - p[0], 2) + Math.pow(b[1] - p[1], 2));

        return (ap + pb == ab);
    }

    public void draw2DTriangle(int [] one, int [] two, int [] three, int color) {
        int [] point = {0, 0};
        for (int y = 0; y < getPHeight(); y++)  {
            for (int x = 0; x < getPWidth(); x++) {
                point[0] = x;
                point[1] = y;

                if (pointOnLine(one, two, point)) setPixel(x, y, color);
                if (pointOnLine(two, three, point)) setPixel(x, y, color);
                if (pointOnLine(three, one, point)) setPixel(x, y, color);
            }
        }
    }

    public void draw2DCircle(int x, int y, int r, int color) {
        int r2 = r * r;

        // iterate through all x-coordinates
        for (int j = y - r; j <= y + r; j++) {
            int y2 = (int)Math.pow(j - y, 2);

            // iterate through all y-coordinates
            for (int i = x - r; i <= x + r; i++) {
                int x2 = (int)Math.pow(i - x, 2);

                // System.out.println(r2);

                // test if in-circle
                if ( (x2 + y2 <= r2) && (x2 + y2 >= r2 - (r * 2)) ) {
                    setPixel(i, j, color);
                }
            }
        }
    }

    public void draw2DFillCircle(int x, int y, int r, int color) {
        int r2 = r * r;

        // iterate through all x-coordinates
        for (int j = y - r; j <= y + r; j++) {
            int y2 = (int)Math.pow(j - y, 2);

            // iterate through all y-coordinates
            for (int i = x - r; i <= x + r; i++) {
                int x2 = (int)Math.pow(i - x, 2);

                // test if in-circle
                if (x2 + y2 <= r2) {
                    setPixel(i, j, color);
                }
            }
        }
    }

    public void draw2DImage(int x, int y, GetImage GetImage) {
        for(int j = y; j < y + GetImage.getHeight(); j++) {
            for (int i = x; i < x + GetImage.getWidth(); i++) {
                setPixel(i, j, GetImage.getPixels()[(i - x) + (j - y) * GetImage.getWidth()]);
            }
        }
    }

    public int getPWidth() {
        return pWidth;
    }

    public int getPHeight() {
        return pHeight;
    }
}
