package engine.renderer.shapes;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Image {
    private int[] pixels;
    private int width;
    private int height;

    public Image(String path) {

        try {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            BufferedImage image = ImageIO.read(classloader.getResourceAsStream(path));
            width = image.getWidth();
            height = image.getHeight();
            pixels = image.getRGB(0, 0, width, height, null, 0, width);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(1);
        }
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
