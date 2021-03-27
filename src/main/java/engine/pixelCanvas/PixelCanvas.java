package engine.pixelCanvas;

import engine.controls.Keyboard;
import engine.controls.Mouse;
import engine.program.Program;
import engine.window.Window;

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

    public PixelCanvas(final Window w) {
        window = w;
        insets =  window.getFrame().getInsets();
        image = new BufferedImage(Program.getWidth(), Program.getHeight(), BufferedImage.TYPE_INT_RGB);
        pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
        renderer = new Renderer(pixels);

        window.getFrame().add(this);
        this.createBufferStrategy(2);
    }

    public void paint() {
        window.refresh();
        Graphics g = null;

        try {
            g = this.getBufferStrategy().getDrawGraphics();

            g.drawImage(
                    image,
                    insets.left,
                    insets.right,
                    (int)(Program.getWidth() * Program.getScale()),
                    (int)(Program.getHeight() * Program.getScale()),
                    null
            );
        } finally {
            if (Objects.nonNull(g)) g.dispose();
        }

        this.getBufferStrategy().show();
    }
    public void addKeyboardListener() {
        Keyboard keyListener = Keyboard.getInstance();
        this.addKeyListener(keyListener);
    }

    public void addMouseListener() {
        Mouse mouseListener = Mouse.getInstance();
        this.addMouseListener(mouseListener);
        this.addMouseMotionListener(mouseListener);
        this.addMouseWheelListener(mouseListener);
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
}