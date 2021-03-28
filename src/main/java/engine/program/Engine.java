package engine.program;

import java.util.Objects;

public final class Engine {
    private boolean started = false;
    private static Engine instance;
    private final Program program;
    private double frameCap = 1.0 / 60.0;

    private Engine(final Program program) {
        this.program = program;
    }

    public static Engine getInstance(final Program program) {
        return Objects.isNull(instance) ? new Engine(program): instance;
    }

    public static Engine getInstance() {
        return Objects.isNull(instance) ? null : instance;
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

            while(unprocessedTime >= frameCap && unprocessedTime != 0) {
                unprocessedTime -= frameCap;
                shouldRender = true;
                program.update();
            }

            if (frameTime >= 1.0) {
                frameTime -= frameTime;

                fps = frames;
                frames = 0;

                System.out.println("fps: " + fps);
            }

            if (shouldRender) {
                program.render();
                frames++;
            }
        }
    }

    public double getFrameCap() {
        return frameCap;
    }

    public void setFrameCap(double frameCap) {
        this.frameCap = frameCap;
    }
}