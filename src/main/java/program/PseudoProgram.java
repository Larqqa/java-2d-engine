package program;

import engine.visuals.PixelCanvas;
import engine.visuals.renderable.image.Image;
import engine.visuals.renderable.image.Sprite;
import engine.program.Program;
import engine.utilities.Color;
import engine.utilities.Colors;
import engine.utilities.Point;
import engine.visuals.Window;

import java.util.ArrayList;
import java.util.Objects;

public class PseudoProgram extends Program {

    private final Window w;
    private final PixelCanvas p;

    private final Point mouse;
    private final Point keyboard;

    private Sprite s;
    private final Image img;

    private double shearingx = 0;
    private double shearingy = 0;

    public PseudoProgram() {
        w = new Window.Builder()
                .setWidth(300)
                .setHeight(300)
                .setScale(2)
                .setTitle("New window")
                .build();

        p = new PixelCanvas(w);
        p.getRenderer().setClearColor(new Color(0.0,0.4,0.5));

        mouse = new Point(150, 150);
        keyboard = new Point(150, 150);

        s = new Sprite("sprite.png", 32,32, 5);

        img = new Image("square.png").scale(2);

        setFrameCap(60);
        start();
    }

    @Override
    public void update() {
        mouse.setX(p.getMouse().getMouseX());
        mouse.setY(p.getMouse().getMouseY());

        if (p.getKeyboard().isKeyDown(87)) {
            keyboard.setY(keyboard.getY() + 1);
            shearingx += .1;
        }
        if (p.getKeyboard().isKeyDown(83)) {
            keyboard.setY(keyboard.getY() - 1);
            shearingx -= .1;
        }

        if (p.getKeyboard().isKeyDown(65)) {
            keyboard.setX(keyboard.getX() - 1);
            shearingy += 1;
        }
        if (p.getKeyboard().isKeyDown(68)) {
            keyboard.setX(keyboard.getX() + 1);
            shearingy -= 1;
        }
    }

    @Override
    public void render(){
        p.getRenderer().test();
//        p.getRenderer().clear();
//        p.getRenderer().circle(mouse, 30, Colors.blue());
//        p.getRenderer().circle(keyboard, 30, Colors.green());

//        p.getRenderer().drawImage(new Point(150,150), img.rotate(10).scale(2));
//        p.getRenderer().drawSprite(keyboard, s.shear(shearingx, shearingy));
//        s.incrementLoopCounter();

//        p.getRenderer().rectangle(mouse, 20, 20,Colors.blue());

        ArrayList<Point> pl = new ArrayList<>();
        pl.add(new Point(50,50));
        pl.add(new Point(100,70));
        pl.add(new Point(100,70));
        pl.add(new Point(150,50));

        p.getRenderer().bezierLine(pl, Colors.blue());

        p.paint();
//        stop();
    }
}