package engine.visuals;

import engine.controls.Keyboard;
import engine.controls.Mouse;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class PixelCanvas extends Canvas {

    private final BufferedImage image;
    private final int[] pixels;
    private final Renderer renderer;
    private final Window window;
    private final JFrame frame;
    Mouse mouseListener;
    Keyboard keyListener = new Keyboard();

    public PixelCanvas(final Window w) {
        window = w;
        frame = w.getFrame();
        image = new BufferedImage(window.getWidth(), window.getHeight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        renderer = new Renderer(pixels, window.getWidth(), window.getHeight());

        window.getFrame().add(this);
        this.createBufferStrategy(2);

        mouseListener = new Mouse(window.getScale());
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addMouseWheelListener(mouseListener);

        this.addKeyListener(keyListener);
    }

    public void paint() {
        window.refresh();
        mouseListener.decrementMouseWheel();
        Graphics g = null;

        g = this.getBufferStrategy().getDrawGraphics();
        g.drawImage(image,0,0,
            (int)(window.getWidth() * window.getScale()),
            (int)(window.getHeight() * window.getScale()),
            null);
        g.dispose();

        this.getBufferStrategy().show();
    }

    public int[] getPixels() {
        return pixels;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public BufferedImage getImage() {
        return image;
    }

    public Mouse getMouse() {
        return mouseListener;
    }

    public Keyboard getKeyboard() {
        return keyListener;
    }
}