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

    public void draw2DCircle(int x, int y, int r, int color) {
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
