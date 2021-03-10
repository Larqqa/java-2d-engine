package engine;

import lombok.Setter;

public abstract class Program {
    @Setter static String title = "Game Engine";
    @Setter static int clearColor = 0xFF000000;
    @Setter public static int width = 200;
    @Setter public static int height = 200;
    @Setter static double scale = 2.0;
    @Setter static double frameCap = 1.0 / 60.0;
    public Renderer renderer = new Renderer();
    private Engine engine;
    private boolean initialized = false;

    public void start() {
        if (!initialized) {
            engine = new Engine(this);
            initialized = true;
        }

        engine.start();
    }

    public void stop() {
        engine.stop();
    }

    abstract public void update();
    abstract public void render();
}
