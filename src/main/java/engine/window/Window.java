package engine.window;

import engine.program.Program;

import javax.swing.JFrame;
import java.awt.Insets;

public class Window extends JFrame {

    private final JFrame frame;
    private final int scaledWidth;
    private final int scaledHeight;
    private final int width;
    private final int height;
    private final String title;
    private final double scale;

    public Window() {
        title = Program.getTitle();
        width = Program.getWidth();
        height = Program.getHeight();
        scale = Program.getScale();

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocus();

        Insets insets = frame.getInsets();

        scaledWidth = insets.left + insets.right + (int)(width * scale);
        scaledHeight = insets.left + insets.right + ((int)(height * scale) + (insets.top - 1));
        refresh();
    }

    public void refresh() {
        if (frame.getWidth() != scaledWidth || frame.getHeight() != scaledHeight) {
            frame.setSize(scaledWidth, frame.getHeight());
            frame.setSize(frame.getWidth(), scaledHeight);
            frame.setLocationRelativeTo(null);
        }
    }

    public JFrame getFrame() {
        return frame;
    }
}