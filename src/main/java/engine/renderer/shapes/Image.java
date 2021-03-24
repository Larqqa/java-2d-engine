package engine.renderer.shapes;

import engine.utilities.Color;
import engine.utilities.MinMax;
import engine.utilities.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Image {
    private int[] pixels;
    private int width;
    private int height;
    private int[] originalPixels;
    private int originalWidth;
    private int originalHeight;

    public Image(String path) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            BufferedImage image = ImageIO.read(Objects.requireNonNull(classloader.getResourceAsStream(path)));
            width = image.getWidth();
            height = image.getHeight();
            originalWidth = width;
            originalHeight = height;
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
            originalPixels = pixels;
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
        originalPixels = pixels;
        originalWidth = width;
        originalHeight = height;
    }

    private Point getCorner(double deltaX, double deltaY, double originX, double originY, double sine, double cosine) {
        double x = (deltaX * cosine - deltaY * sine);
        double y = (deltaX * sine   + deltaY * cosine);

        return new Point(x, y);
    }

    // https://stackoverflow.com/questions/29739809/java-rotation-of-pixel-array
    public Image rotate(double angle) {
        angle = angle * Math.PI / 180;
        double cosine = Math.cos(-angle);
        double sine = Math.sin(-angle);
        double originX = (double) originalWidth / 2;
        double originY = (double) originalHeight / 2;

        ArrayList<Point> corners = new ArrayList<>(Arrays.asList(
            getCorner(-originX, -originY, originX, originY, sine, cosine),
            getCorner(originalWidth - originX, -originY, originX, originY, sine, cosine),
            getCorner(-originX, originalHeight - originY, originX, originY, sine, cosine),
            getCorner(originalWidth - originX, originalHeight - originY, originX, originY, sine, cosine)
        ));

        MinMax minMax = new MinMax(corners, 0);

        int totalWidth = Math.round(minMax.getMaxX() + Math.abs(minMax.getMinX()));
        int totalHeight = Math.round(minMax.getMaxY() + Math.abs(minMax.getMinY()));

        int[] newPixels = new int[totalWidth * totalHeight];

        for(int y = 0; y < totalHeight; y++) {
            for (int x = 0; x < totalWidth; x++) {
                double deltaX = Math.round(x - (Math.abs(minMax.getMinX())));
                double deltaY = Math.round(y - (Math.abs(minMax.getMinY())));

                double xa = deltaX * cosine - deltaY * sine;
                double ya = deltaX * sine   + deltaY * cosine;

                int newX = (int)Math.floor(xa + originX);
                int newY = (int)Math.floor(ya + originY);

                if (newX < 0 || newX > originalWidth - 1) continue;
                if (newY < 0 || newY > originalHeight - 1) continue;

                newPixels[y * totalWidth + x] = originalPixels[newY * originalWidth + newX];
            }
        }

        pixels = newPixels;
        width = totalWidth;
        height = totalHeight;
        return this;
    }

    // https://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    public Image scale(double xScale, double yScale) {
        int scaledWidth = (int) Math.round(originalWidth * xScale);
        int scaledHeight = (int) Math.round(originalHeight * yScale);
        int[] newPixels = new int[scaledWidth * scaledHeight];

        double widthRatio = (double) originalWidth / scaledWidth;
        double heightRatio = (double) originalHeight / scaledHeight;

        int newPixel, oldPixel;
        for (int i = 0; i < scaledHeight; i++) {
            for (int j = 0; j < scaledWidth; j++) {
                newPixel = i * scaledWidth + j;
                oldPixel = (int) (Math.floor(i * heightRatio) * originalWidth + Math.floor(j * widthRatio));
                newPixels[newPixel] = originalPixels[oldPixel];
            }
        }

        pixels = newPixels;
        width = scaledWidth;
        height = scaledHeight;
        return this;
    }

    public Image scale(double scale) {
        return scale(scale, scale);
    }

    public Image tint(Color color) {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color originalColor = new Color(pixels[i * width + j]);

                if (originalColor.getAlpha() > 0) {
                    pixels[i * width + j] = originalColor.alphaBlend(color.colorToInt());
                }
            }
        }

        return this;
    }

    public Image reset() {
        pixels = originalPixels;
        width = originalWidth;
        height = originalHeight;
        return this;
    }

    public int[] getPixels() {
        return pixels;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
