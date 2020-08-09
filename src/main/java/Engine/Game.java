package Engine;

import Pacman.Pacman;

public class Game {
    private Renderer renderer;
    private Input input;

    public Game() {
        GameSettings settings = new GameSettings();
        Engine engine = new Engine(this, settings);
    }

    public void update() {}

    public void render() {
        renderer.draw2DRect(input.getMouseX(), input.getMouseY(),50,50,0xffffffff);
    }

    public void setInput(Input input) {
        this.input = input;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }
}
