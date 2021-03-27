package engine.visuals;

import engine.controls.Keyboard;
import engine.controls.Mouse;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Objects;

public class PixelCanvas extends Canvas {

    private final BufferedImage image;
    private final int[] pixels;
    private final Insets insets;
    private final Renderer renderer;
    private final Window window;
    Mouse mouseListener;
    Keyboard keyListener = new Keyboard();

    public PixelCanvas(final Window w) {
        window = w;
        insets =  window.getFrame().getInsets();
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
        mouseListener.decrementMouseWheel();
        Graphics g = null;

        try {
            g = this.getBufferStrategy().getDrawGraphics();

            g.drawImage(
                    image,
//                    insets.left,
//                    insets.right,
                    0,0,
                    (int)(window.getWidth() * window.getScale()),
                    (int)(window.getHeight() * window.getScale()),
                    null
            );
        } finally {
            if (Objects.nonNull(g)) g.dispose();
        }

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