package engine;

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

    PixelCanvas(Window window) {
        insets =  window.getFrame().getInsets();
        image = new BufferedImage(Program.width, Program.height, BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    }

    public void clear() {
        Arrays.fill(pixels, Program.clearColor);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(
                image,
                insets.left,
                insets.right,
                (int)(Program.width * Program.scale),
                (int)(Program.height * Program.scale),
                this
        );

        g.dispose();
    }
}