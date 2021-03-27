package engine.program;

public abstract class Program {
    private final Engine engine = Engine.getInstance(this);

    public void start() {
        engine.start();
    }

    public void stop() {
        engine.stop();
        System.out.println("Engine stopped.");
    }

    abstract public void update();

    abstract public void render();

    public void setFrameCap(double cap) {
        engine.setFrameCap(1.0 / cap);
    }
}
