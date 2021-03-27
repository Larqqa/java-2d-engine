package engine.visuals;

import engine.program.Engine;

import javax.swing.JFrame;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class Window implements WindowListener {

    private final JFrame frame;
    private final int scaledWidth;
    private final int scaledHeight;
    private final int width;
    private final int height;
    private final String title;
    private final double scale;
    public static int windowAmount = 0;

    public static class Builder {

        private String title = "LrqEngine";
        private int width = 160;
        private int height = 90;
        private double scale = 2.0;

        public Builder() {}

        public Builder setTitle(String title) {
            this.title = title;
            return this;
        }

        public Builder setWidth(int width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(int height) {
            this.height = height;
            return this;
        }

        public Builder setScale(double scale) {
            this.scale = scale;
            return this;
        }

        public Window build() {
            return new Window(title, width, height, scale);
        }
    }

    private Window(final String title, final int width, final int height, final double scale) {
        this.title = title;
        this.width = width;
        this.height = height;
        this.scale = scale;

        frame = new JFrame(title);
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);
        frame.requestFocus();
        frame.addWindowListener(this);
        windowAmount++;

        Insets insets = frame.getInsets();

        scaledWidth = insets.left + insets.right + (int)(width * scale);

        if (System.getProperty("os.name").equals("Windows 10")) {
            scaledHeight = insets.left + insets.right + ((int)(height * scale) + (insets.top - 8));
        } else {
            scaledHeight = insets.left + insets.right + ((int)(height * scale) + (insets.top - 1));
        }

        refresh();
    }

    public void refresh() {
        if (frame.getWidth() != scaledWidth || frame.getHeight() != scaledHeight) {
            frame.setSize(scaledWidth, frame.getHeight());
            frame.setSize(frame.getWidth(), scaledHeight);
            frame.setLocationRelativeTo(null);
        }
    }

    @Override
    public void windowClosing(WindowEvent e) {
        if ( windowAmount <= 1) {
            System.exit(0);
        }

        frame.dispose();
        windowAmount--;
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    public JFrame getFrame() {
        return frame;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getScale() {
        return scale;
    }
}