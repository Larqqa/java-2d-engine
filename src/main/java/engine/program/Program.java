package engine.program;

public class Program implements Programmable {

    private static String title = "Game Engine";
    private static int clearColor = 0xFF000000;
    private static int width = 400;
    private static int height = 200;
    private static double scale = 2.0;
    private static double frameCap = 1.0 / 60.0;
    private final Engine engine = Engine.getInstance(this);

    public void start() {
        engine.start();
    }

    public void stop() {
        engine.stop();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
    }

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
