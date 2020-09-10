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
        settings.setFpscap(1.0 / 10.0);

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
        //renderer.draw2DCircle(mx,my,50,0xffffffff);
        //renderer.draw2DFillCircle(300,100,25,0xffffffff);
        //renderer.draw2DFillRect(50,50,50,70,0x66666666);
        //renderer.draw2DRect(mx, my,50,50,0xffffffff);

        int[] one = {10, 10};
        int[] two = {100, 20};
        int[] three = {50, 50};

        // renderer.draw2DFillTriangle(one, two, three, 0xffffffff);
        renderer.draw2DTriangle(one, two, three, 0xffffffff);
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
