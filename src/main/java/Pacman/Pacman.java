package Pacman;
import Engine.*;

public class Pacman extends Game {
    private Renderer renderer;
    private Input input;
    private int mx;
    private int my;

    public Pacman() {
        GameSettings settings = new GameSettings();
        settings.setWidth(500);
        settings.setTitle("Pacman");
        settings.setFpscap(1.0 / 100.0);

        Engine engine = new Engine(this, settings);
        engine.start();
    }

    @Override
    public void update() {
        mx = input.getMouseX();
        my = input.getMouseY();
    }

    @Override
    public void render() {
        renderer.draw2DCircle(mx,my,25,0xffffffff);
        // renderer.draw2DFillRect(50,50,50,50,0x66666666);
        // renderer.draw2DRect(mx, my,50,50,0xffffffff);
    }

    public static void main(String[] args) {
        new Pacman();
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
