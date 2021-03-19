package engine;

import engine.controls.Keyboard;
import engine.controls.Mouse;
import engine.renderer.Renderer;
import engine.window.PixelCanvas;
import engine.window.Window;

import java.util.Objects;

public final class Engine {
    private boolean started = false;
    private static Engine instance;
    private final PixelCanvas canvas;
    private final Window window;
    private final Program program;

    private Engine(final Program program) {
        window = new Window();
        canvas = window.getCanvas();
        this.program = program;
    }

    public static Engine getInstance(final Program program) {
        if(Objects.isNull(instance)) {
            instance = new Engine(program);
        }

        return instance;
    }

    public static Engine getInstance() {
        if(Objects.isNull(instance)) return null;
        return instance;
    }

    public void start() {
        if (started) return;
        started = true;
        run();
    }

    public void stop() {
        if (!started) return;
        started = false;
    }

    private void run() {
        double previous = System.nanoTime() / 1e9d;
        double now = 0.0;
        double elapsedTime = 0.0;
        double unprocessedTime = 0;
        boolean shouldRender = false;

        double frameTime = 0.0;
        int frames = 0;
        int fps = 0;

        while(started) {
            now = System.nanoTime() / 1e9d;
            elapsedTime = now - previous;
            previous = now;
            unprocessedTime += elapsedTime;
            frameTime += elapsedTime;
            shouldRender = false;

            while(unprocessedTime >= Program.getFrameCap() && unprocessedTime != 0) {
                unprocessedTime -= Program.getFrameCap();
                shouldRender = true;

                Mouse.getInstance().decrementMouseWheel();
                program.update(Mouse.getInstance(), Keyboard.getInstance());
            }
            program.update(Mouse.getInstance(), Keyboard.getInstance());

            if (frameTime >= 1.0) {
                frameTime -= frameTime;

                fps = frames;
                frames = 0;

                System.out.println("fps: " + fps);
            }

            if (shouldRender) {
                window.refresh();
                program.render(Renderer.getInstance());
                canvas.repaint();

                frames++;
            }
        }
    }

}