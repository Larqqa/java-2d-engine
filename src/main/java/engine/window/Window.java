package engine.window;

import engine.Program;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class Window extends JFrame {
    @Getter final private JFrame frame;
    @Getter final private PixelCanvas canvas;
    private final int scaledWidth;
    private final int scaledHeight;

    public Window() {
        frame = new JFrame(Program.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(Program.getWidth(), Program.getHeight());
        frame.setResizable(false);

        canvas = new PixelCanvas(this);
        frame.add(canvas);

        frame.setVisible(true);
        frame.requestFocus();

        Insets insets = frame.getInsets();
        scaledWidth = insets.left + insets.right + (int)(Program.getWidth() * Program.getScale());
        scaledHeight = insets.left + insets.right + ((int)(Program.getHeight() * Program.getScale()) + (insets.top - 1));
        refresh();
    }

    public void refresh() {
        if (frame.getWidth() != scaledWidth || frame.getHeight() != scaledHeight) {
            frame.setSize(scaledWidth, frame.getHeight());
            frame.setSize(frame.getWidth(), scaledHeight);
            frame.setLocationRelativeTo(null);
        }
    }
}