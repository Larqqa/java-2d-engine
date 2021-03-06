package engine;

import lombok.Getter;
import program.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.Arrays;

public class Window extends JFrame {
    @Getter final private JFrame frame;
    @Getter final private PixelCanvas canvas;
    private final int width;
    private final int height;
    private final double scale;
    private final Insets insets;
    private final int scaledWidth;
    private final int scaledHeight;

    Window(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
        scale = settings.getScale();

        frame = new JFrame(settings.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);

        canvas = new PixelCanvas(this, settings);
        frame.add(canvas);

        frame.setVisible(true);
        frame.requestFocus();

        insets = frame.getInsets();
        scaledWidth = insets.left + insets.right + (int)(width * scale);
        scaledHeight = insets.left + insets.right + ((int)(height * scale) + (insets.top - 1));
        refreshSize();
    }

    public void refresh() {
        refreshSize();
    }

    private void refreshSize() {
        if (frame.getWidth() != scaledWidth || frame.getHeight() != scaledHeight) {
            frame.setSize(scaledWidth, frame.getHeight());
            frame.setSize(frame.getWidth(), scaledHeight);
            frame.setLocationRelativeTo(null);
        }
    }
}