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
        System.out.println(toplx +" "+ toply);

        deltaX = width - originX;
        deltaY = -originY;
        double toprx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double topry = Math.round(deltaX * sine   + deltaY * cosine) + originY;
        System.out.println(toprx +" "+ topry);

        deltaX = -originX;
        deltaY = height - originY;
        double btlx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double btly = Math.round(deltaX * sine   + deltaY * cosine) + originY;
        System.out.println(btlx +" "+ btly);

        deltaX = width - originX;
        deltaY = height - originY;
        double btrx = Math.round(deltaX * cosine - deltaY * sine) + originX;
        double btry = Math.round(deltaX * sine   + deltaY * cosine) + originY;
        System.out.println(btrx +" "+ btry);

        int minX = (int) Math.min(Math.min(Math.min(toplx, toprx), btlx), btrx);
        int maxX = (int) Math.max(Math.max(Math.max(toplx, toprx), btlx), btrx);
        int minY = (int) Math.min(Math.min(Math.min(toply, topry), btly), btry);
        int maxY = (int) Math.max(Math.max(Math.max(toply, topry), btly), btry);

        System.out.println(minX +" "+ maxX);
        System.out.println(minX +" "+ maxY);

        int offX = Math.abs(maxX) + Math.abs(minX);
        int offY = Math.abs(maxY) + Math.abs(minY);

        int[] newPixels = new int[offX * offY];

        for(int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                deltaX = x - (originX + Math.abs(minX));
                deltaY = y - (originY + Math.abs(minY));

                double xa = Math.round(deltaX * cosine - deltaY * sine);
                double ya = Math.round(deltaX * sine   + deltaY * cosine);

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
