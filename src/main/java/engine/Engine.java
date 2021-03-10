package engine;

public class Engine {
    private boolean started = false;
    private final PixelCanvas canvas;
    private final Window window;
    private final Program program;

    public Engine(Program program) {
        window = new Window();
        canvas = window.getCanvas();

        this.program = program;
        program.renderer.setPixels(canvas.getPixels());
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

            while(unprocessedTime >= Program.frameCap && unprocessedTime != 0) {
                unprocessedTime -= Program.frameCap;
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
                window.refresh();
                program.render();
                canvas.repaint();

                frames++;
            }
        }
    }
}