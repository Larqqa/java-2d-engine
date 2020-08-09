package Engine;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Window implements WindowListener{
    private final GameSettings settings;
    private final Frame frame;
    private BufferedImage image;
    private BufferStrategy bufferStrategy;
    private Graphics graphics;
    private Insets insets;
    private final float scale;
    private final int width;
    private final int height;

    public Window(GameSettings settings) {
        this.settings = settings;
        this.width = settings.getWidth();
        this.height = settings.getHeight();
        this.scale = settings.getScale();

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        frame = new Frame(settings.getTitle());
        insets = frame.getInsets();
        frame.setSize(width, height);
        frame.setResizable(false);
        frame.setVisible(true);

        frame.createBufferStrategy(2);
        bufferStrategy = frame.getBufferStrategy();
        graphics = bufferStrategy.getDrawGraphics();

        frame.addWindowListener(this);
        frame.requestFocus();
    }

    public void update() {
        insets = frame.getInsets();
        if(frame.getWidth() != insets.left + insets.right + (int)(image.getWidth() * scale)) {
            frame.setSize(insets.left + insets.right + (int)(image.getWidth() * scale), frame.getHeight());
            frame.setLocationRelativeTo(null);
        }
        if(frame.getHeight() != insets.top + insets.bottom + (int)(image.getHeight() * scale)) {
            frame.setSize(frame.getWidth(), insets.top + insets.bottom + (int) (image.getHeight() * scale));
            frame.setLocationRelativeTo(null);
        }
        do {
            do {
                bufferStrategy = frame.getBufferStrategy();
                if(bufferStrategy == null) {
                    frame.createBufferStrategy(2);
                    continue;
                }
                graphics = bufferStrategy.getDrawGraphics();
                graphics.drawImage(image, insets.left,
                        insets.top,
                        (int)(image.getWidth() * scale),
                        (int)(image.getHeight() * scale),
                        null);
                graphics.dispose();
            } while (bufferStrategy.contentsRestored());
            bufferStrategy.show();
        } while (bufferStrategy.contentsLost());
    }

    public void dispose() {
        frame.setVisible(false);
        frame.dispose();
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Insets getInsets() {
        return insets;
    }

    public Frame getFrame() {
        return frame;
    }

    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    @Override
    public void windowClosing(WindowEvent windowEvent) {
        Engine.stop();
    }

    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    @Override
    public void windowActivated(WindowEvent windowEvent) {

    }

    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}
