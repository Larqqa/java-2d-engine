package engine.window;

import engine.Program;
import lombok.Getter;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class PixelCanvas extends JPanel {
    @Getter private final BufferedImage image;
    @Getter private final int[] pixels;
    private final Insets insets;

    PixelCanvas(final Window window) {
        insets =  window.getFrame().getInsets();
        image = new BufferedImage(Program.getWidth(), Program.getHeight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
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
}