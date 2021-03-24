package engine.window;

import engine.Program;
import engine.renderer.Renderer;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;
import java.util.Objects;

public class PixelCanvas extends Canvas {

    private final BufferedImage image;
    private final int[] pixels;
    private final Insets insets;

    PixelCanvas(final Window window) {
        insets =  window.getFrame().getInsets();
        image = new BufferedImage(Program.getWidth(), Program.getHeight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        Renderer.getInstance(pixels);
    }

    public void clear() {
        Arrays.fill(pixels, Program.getClearColor());
    }

    public void repaint() {
        Graphics g = null;
        try {
            g = this.getBufferStrategy().getDrawGraphics();

            g.drawImage(
                    image,
                    insets.left,
                    insets.right,
                    (int)(Program.getWidth() * Program.getScale()),
                    (int)(Program.getHeight() * Program.getScale()),
                    null
            );
        } finally {
            if (Objects.nonNull(g)) g.dispose();
        }

        this.getBufferStrategy().show();
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }
}