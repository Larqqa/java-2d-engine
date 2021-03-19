package engine.window;

import engine.Program;
import engine.renderer.Renderer;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class PixelCanvas extends JPanel {

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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                image,
                insets.left,
                insets.right,
                (int)(Program.getWidth() * Program.getScale()),
                (int)(Program.getHeight() * Program.getScale()),
                this
        );

        g.dispose();
    }

    public int[] getPixels() {
        return pixels;
    }

    public BufferedImage getImage() {
        return image;
    }
}