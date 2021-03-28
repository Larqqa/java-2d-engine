package engine.visuals.renderable.image;

import engine.utilities.Color;
import engine.utilities.MinMax;
import engine.utilities.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
        double x = Math.ceil(deltaX * cosine - deltaY * sine);
        double y = Math.ceil(deltaX * sine   + deltaY * cosine);

        return new Point(x, y);
    }

    // https://stackoverflow.com/questions/29739809/java-rotation-of-pixel-array
    public Image rotate(double angle) {
        angle = angle * Math.PI / 180;
        double cosine = Math.cos(-angle);
        double sine = Math.sin(-angle);
        double originX = (double) width / 2;
        double originY = (double) height / 2;

        ArrayList<Point> corners = new ArrayList<>(Arrays.asList(
            getCorner(-originX, -originY, originX, originY, sine, cosine),
            getCorner(width - originX, -originY, originX, originY, sine, cosine),
            getCorner(-originX, height - originY, originX, originY, sine, cosine),
            getCorner(width - originX, height - originY, originX, originY, sine, cosine)
        ));

        MinMax minMax = new MinMax(corners, 0);

        int totalWidth = (int) minMax.width();
        int totalHeight = (int) minMax.height();

        int[] newPixels = new int[totalWidth * totalHeight];

        for(double y = 0; y < totalHeight; y++) {
            for (double x = 0; x < totalWidth; x++) {
                double deltaX = x - (Math.abs(minMax.getMinX()));
                double deltaY = y - (Math.abs(minMax.getMinY()));

                double newX = Math.floor((deltaX * cosine - deltaY * sine) + originX);
                double newY = Math.floor((deltaX * sine   + deltaY * cosine) + originY);

                if (newX < 0 || newX > width - 1) continue;
                if (newY < 0 || newY > height - 1) continue;

                newPixels[(int) (y * totalWidth + x)] = pixels[(int) (newY * width + newX)];
            }
        }

        return new Image(newPixels, totalWidth, totalHeight);
    }

    public Image xShear(double xShear) {
        if (xShear == 0) return this;

        int newWidth = (int) (width + Math.abs(xShear) * height);
        int[] newPixels = new int[newWidth * height];

        double xOffset = xShear < 0 ? Math.abs(xShear) * height : 0;

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newX = (int) (x + xShear * y);
                int pixel = (int) (y * newWidth + (newX + xOffset));
                int originalPixel = y * width + x;

                if (pixel > newPixels.length - 1) continue;
                if (originalPixel > pixels.length - 1) continue;

                newPixels[pixel] = pixels[originalPixel];
            }
        }

        return new Image(newPixels, newWidth, height);
    }

    public Image yShear(double yShear) {
        if (yShear == 0) return this;

        int newHeight = (int) (height + Math.abs(yShear) * width);
        int[] newPixels = new int[width * newHeight];

        double yOffset = yShear < 0 ? Math.abs(yShear) * width : 0;

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int newY = (int) (y + yShear * x);
                int pixel = (int) ((newY + yOffset) * width + x);
                int originalPixel = y * width + x;

                if (pixel < 0 || pixel > newPixels.length - 1) continue;
                if (originalPixel > pixels.length - 1) continue;

                newPixels[pixel] = pixels[originalPixel];
            }
        }

        return new Image(newPixels, width, newHeight);
    }

    public Image shear(double xShear, double yShear) {
        return this.xShear(xShear).yShear(yShear);
    }

        // https://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    private Image doScale(double xScale, double yScale) {
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

        return new Image(newPixels, scaledWidth, scaledHeight);
    }

    public Image scale(double xScale, double yScale) {
        if (xScale < 0) xScale = 0;
        if (yScale < 0) yScale = 0;

        return doScale(xScale, yScale);
    }
    public Image scale(double scale) {
        if (scale < 0) scale = 0;

        return doScale(scale, scale);
    }

    public Image tint(Color color) {
        int[] newPixels = new int[width * height];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color originalColor = new Color(pixels[i * width + j]);

                if (originalColor.getAlpha() > 0) {
                    newPixels[i * width + j] = originalColor.alphaBlend(color.colorToInt());
                }
            }
        }

        return new Image(newPixels, width, height);
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
