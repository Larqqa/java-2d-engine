package program;

import engine.Program;
import engine.utilities.Color;
import engine.utilities.Point;

import java.util.ArrayList;

import static engine.renderer.Renderer.*;

public class PseudoProgram extends Program {
    private int angle = 0;

    public PseudoProgram() {
        Program.setTitle("yeet");
//        Program.setWidth(300);
        Program.setScale(4.0);
//        Program.setFrameCap(1.0 / 4);

        start();
    }

    @Override
    public void update() {
    }

    @Override
    public void render(){
        clear();
        test();

        int r = 40;
        double d = angle * Math.PI / 180;
        int x2 = (int)(r * Math.cos(d));
        int y2 = (int)(r * Math.sin(d));
        angle++;

//        line(new Point(100,100), new Point(100 + x2,100 + y2), 5, new Color(1.0,0.0,0.0));
//        circleFill(new Point(100,100), 50,new Color(1.0,1.0,0.0, 0.5));
//        circle(new Point(100,100), 50, 5, new Color(0.0,0.0,1.0, 0.5));
//        triangle(
//                new Point(50 + x2,100 - y2),
//                new Point(100,100 + y2),
//                new Point(150 - x2,150 + y2),
//                new Color(1.0,1.0,0.0));

        ArrayList<Point> points = new ArrayList<Point>();
        points.add(new Point(100,30));
        points.add(new Point(100,50));
        points.add(new Point(120,50));
        points.add(new Point(120,30));
        points.add(new Point(130,70));
        points.add(new Point(70,60));

        polygon(points, new Color(1.0,1.0,1.0));
    }
}