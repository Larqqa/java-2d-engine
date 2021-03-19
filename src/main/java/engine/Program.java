package engine;

import engine.controls.Keyboard;
import engine.controls.Mouse;
import engine.renderer.Renderer;

public abstract class Program {
    private static String title = "Game Engine";
    private static int clearColor = 0xFF000000;
    private static int width = 200;
    private static int height = 200;
    private static double scale = 2.0;
    private static double frameCap = 1.0 / 60.0;

    private Engine engine;
    private boolean initialized = false;

    public void start() {
        if (!initialized) {
            engine = Engine.getInstance(this);
            initialized = true;
        }

        engine.start();
    }

    public void stop() {
        engine.stop();
        System.out.println("Stopped");
    }

    abstract public void update(Mouse mouse, Keyboard keyboard);
    abstract public void render(Renderer renderer);

    public static String getTitle() {
        return title;
    }

    public static void setTitle(String title) {
        Program.title = title;
    }

    public static int getClearColor() {
        return clearColor;
    }

    public static void setClearColor(int clearColor) {
        Program.clearColor = clearColor;
    }

    public static int getWidth() {
        return width;
    }

    public static void setWidth(int width) {
        Program.width = width;
    }

    public static int getHeight() {
        return height;
    }

    public static void setHeight(int height) {
        Program.height = height;
    }

    public static double getScale() {
        return scale;
    }

    public static void setScale(double scale) {
        Program.scale = scale;
    }

    public static double getFrameCap() {
        return frameCap;
    }

    public static void setFrameCap(double frameCap) {
        Program.frameCap = frameCap;
    }
}
