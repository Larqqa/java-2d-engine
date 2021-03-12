package program;

import engine.Program;
import engine.utilities.Color;
import engine.utilities.Point;

import static engine.renderer.Renderer.*;

public class PseudoProgram extends Program {
    private int angle = 0;

    public PseudoProgram() {
        Program.setTitle("yeet");
//        Program.setWidth(300);
        Program.setScale(4.0);

        start();
    }

    @Override
    public void update() {
    }

    @Override
    public void render(){
        clear();
        test();

        int r = 20;
        double d = angle * Math.PI / 180;
        int x2 = (int)(r * Math.cos(d));
        int y2 = (int)(r * Math.sin(d));
        angle++;

        line(new Point(100,100), new Point(100 + x2,100 + y2), 5, new Color(1.0,0.0,0.0));
//        circleFill(new Point(100,100), 20,new Color(1.0,1.0,0.0));
//        circle(new Point(100,100), 50, 20, new Color(0.0,0.0,1.0, 0.5));

    }
}