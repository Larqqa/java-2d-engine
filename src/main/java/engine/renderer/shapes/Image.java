package engine.renderer.shapes;

import engine.utilities.Color;
import engine.utilities.Point;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class Image {
    private int[] pixels;
    private int width;
    private int height;

    public Image(String path) {
        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            BufferedImage image = ImageIO.read(Objects.requireNonNull(classloader.getResourceAsStream(path)));
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
    }

    public Image(int[] pixels, int width, int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    // https://stackoverflow.com/questions/29739809/java-rotation-of-pixel-array
    public Image rotate(double angle) {
        angle = angle * Math.PI / 180;
        double cosine = Math.cos(-angle);
        double sine = Math.sin(-angle);
        int originX = width / 2;
        int originY = height / 2;

        double deltaX = -originX;
        double deltaY = -originY;
        double toplx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double toply = Math.round(deltaX * sine   + deltaY * cosine) + originY;
//        System.out.println(toplx +" "+ toply);

        deltaX = width - originX;
        deltaY = -originY;
        double toprx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double topry = Math.round(deltaX * sine   + deltaY * cosine) + originY;
//        System.out.println(toprx +" "+ topry);

        deltaX = -originX;
        deltaY = height - originY;
        double btlx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double btly = Math.round(deltaX * sine   + deltaY * cosine) + originY;
//        System.out.println(btlx +" "+ btly);

        deltaX = width - originX;
        deltaY = height - originY;
        double btrx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double btry = Math.round(deltaX * sine   + deltaY * cosine) + originY;
//        System.out.println(btrx +" "+ btry);

        int minX = (int) Math.min(Math.min(Math.min(toplx, toprx), btlx), btrx);
        int maxX = (int) Math.max(Math.max(Math.max(toplx, toprx), btlx), btrx);
        int minY = (int) Math.min(Math.min(Math.min(toply, topry), btly), btry);
        int maxY = (int) Math.max(Math.max(Math.max(toply, topry), btly), btry);

//        System.out.println(minX +" "+ maxX);
//        System.out.println(minX +" "+ maxY);

        int offX = Math.abs(maxX) + Math.abs(minX);
        int offY = Math.abs(maxY) + Math.abs(minY);

        int[] newPixels = new int[offX * offY];

        for(int y = 0; y < maxY + Math.abs(minY); y++) {
            for (int x = 0; x < maxX + Math.abs(minX); x++) {
                deltaX = x - (originX + Math.abs(minX));
                deltaY = y - (originY + Math.abs(minY));

                double xa = (deltaX * cosine - deltaY * sine);
                double ya = (deltaX * sine   + deltaY * cosine);

                int newX = (int) (xa + originY);
                int newY = (int) (ya + originX);

                if (newX < 0 || newX > width - 1) continue;
                if (newY < 0 || newY > height - 1) continue;

                int imageLoc = (newY) * width + (newX);
                if (imageLoc < 0 || imageLoc > pixels.length - 1) continue;

                int pixelLoc = y * offX + x;
                if (pixelLoc < 0 || pixelLoc > newPixels.length - 1) continue;

                newPixels[pixelLoc] = pixels[imageLoc];
            }
        }

        pixels = newPixels;
        width = offX;
        height = offY;
        return this;
    }

    // https://tech-algorithm.com/articles/nearest-neighbor-image-scaling/
    public Image scale(double xScale, double yScale) {
        int scaledWidth = (int) Math.round(width * xScale);
        int scaledHeight = (int) Math.round(height * yScale);
        int[] newPixels = new int[scaledWidth * scaledHeight];

        double widthRatio = (double) width / scaledWidth;
        double heightRatio = (double) height / scaledHeight;

        for (int i = 0; i < scaledHeight; i++) {
            for (int j = 0; j < scaledWidth; j++) {
                newPixels[i * scaledWidth + j] = pixels[(int) (Math.floor(i * heightRatio) * width + Math.floor(j * widthRatio))];
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
