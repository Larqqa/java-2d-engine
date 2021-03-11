package engine;

import lombok.Getter;
import lombok.Setter;

public abstract class Program {
    @Getter @Setter private static String title = "Game Engine";
    @Getter @Setter private static int clearColor = 0xFF000000;
    @Getter @Setter private static int width = 200;
    @Getter @Setter private static int height = 200;
    @Getter @Setter private static double scale = 2.0;
    @Getter @Setter private static double frameCap = 1.0 / 60.0;

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
