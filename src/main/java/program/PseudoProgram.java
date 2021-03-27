package program;

import engine.controls.Keyboard;
import engine.controls.Mouse;
import engine.pixelCanvas.PixelCanvas;
import engine.pixelCanvas.renderable.image.Sprite;
import engine.program.Program;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.window.Window;

public class PseudoProgram extends Program {

    private final Window w;
    private final PixelCanvas p;
    private final Mouse m;
    private final Keyboard k;

    private final Point mouse = new Point(0,0);
    private final Point keyboard = new Point(200,200);

    private final Sprite s = new Sprite("sprite.png", 32,32, 5);

    public PseudoProgram() {
        Program.setWidth(16);
        Program.setHeight(8);
        Program.setScale(120.0);

        w = new Window();
        p = new PixelCanvas(w);
        p.addMouseListener();
        p.addKeyboardListener();
        k = Keyboard.getInstance();
        m = Mouse.getInstance();

        start();
    }

    @Override
    public void update() {
        mouse.setX(m.getMouseX());
        mouse.setY(m.getMouseY());

        if (k.isKeyDown(87)) {
            keyboard.setY(keyboard.getY() + 1);
        }
        if (k.isKeyDown(83)) {
            keyboard.setY(keyboard.getY() - 1);
        }

        if (k.isKeyDown(65)) {
            keyboard.setX(keyboard.getX() - 1);
        }
        if (k.isKeyDown(68)) {
            keyboard.setX(keyboard.getX() + 1);
        }
    }

    @Override
    public void render(){
        p.getRenderer().test();
//        p.getRenderer().circle(mouse, 30, Colors.blue());
//        p.getRenderer().circle(keyboard, 30, Colors.green());
//        p.getRenderer().drawSprite(keyboard, s);
        p.paint();
    }
}