package engine;

import lombok.Getter;
import program.Settings;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PixelCanvas extends JPanel {
    @Getter private final BufferedImage image;
    @Getter private final int[] pixels;
    private final int width;
    private final int height;
    private final double scale;
    private final Insets insets;

    PixelCanvas(Window window, Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
        scale = settings.getScale();
        insets =  window.getFrame().getInsets();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                image,
                insets.left,
                insets.right,
                (int)(width * scale),
                (int)(height * scale),
                this
        );

        g.dispose();
    }
}