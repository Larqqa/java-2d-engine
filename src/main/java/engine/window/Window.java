package engine.window;

import engine.Program;
import engine.controls.Keyboard;
import engine.controls.Mouse;

import javax.swing.JFrame;
import java.awt.Insets;

public class Window extends JFrame {

    private final JFrame frame;
    private final PixelCanvas canvas;
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
        canvas.createBufferStrategy(2);

        Insets insets = frame.getInsets();

        Keyboard keyListener = Keyboard.getInstance();
        canvas.addKeyListener(keyListener);

        Mouse mouseListener = Mouse.getInstance(insets);
        canvas.addMouseListener(mouseListener);
        canvas.addMouseMotionListener(mouseListener);
        canvas.addMouseWheelListener(mouseListener);

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

    public JFrame getFrame() {
        return frame;
    }

    public PixelCanvas getCanvas() {
        return canvas;
    }
}