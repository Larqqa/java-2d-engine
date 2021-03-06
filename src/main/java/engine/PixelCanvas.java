package engine;

import lombok.Getter;
import program.Settings;

import java.awt.*;
import javax.swing.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Arrays;

public class PixelCanvas extends JPanel {
    @Getter private BufferedImage image;
    @Getter private final int[] pixels;
    private int width;
    private int height;
    private double scale;
    private int clearColor;
    private Insets insets;

    PixelCanvas(Window window, Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
        scale = settings.getScale();
        clearColor = settings.getClearColor();

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