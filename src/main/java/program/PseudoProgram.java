package program;

import engine.Program;
import engine.controls.Keyboard;
import engine.controls.Mouse;
import engine.renderer.Renderer;
import engine.renderer.shapes.Image;
import engine.utilities.Color;
import engine.utilities.Point;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;

public class PseudoProgram extends Program {

    private int angle = 0;
    private int x = 100;
    private int y = 100;

    public PseudoProgram() {
        Program.setTitle("title");
        Program.setWidth(600);
        Program.setHeight(600);

        Program.setScale(1.0);
//        Program.setFrameCap(1.0 / 4);

        start();
    }

    @Override
    public void update(Mouse mouse, Keyboard keyboard) {
//        int wheel = mouse.getWheelDirection();

//        HashMap<Integer, String> keys = Mouse.getPressedButtons();
//        System.out.println(keys.toString());
//
        x = mouse.getMouseX();
        y = mouse.getMouseY();
//
//        if (mouse.isButtonDown(1)) {
//            Renderer.getInstance().circle(new Point(x, y), 5, new Color(0.0,0.0,1.0));
//        }
//
//        if (mouse.isButtonDown(2)) {
//            Renderer.getInstance().circle(new Point(x, y), 5, new Color(0.0,1.0,0.0));
//        }
//
//        if (mouse.isButtonDown(3)) {
//            Renderer.getInstance().circle(new Point(x, y), 5, new Color(1.0,0.0,0.0));
//        }

//        HashMap<Integer, String> keys = Keyboard.getPressedKeys();
//        System.out.println(keys.toString());
////        System.out.println(keys.containsKey(65));
//
//        if (keys.containsKey(65)) {
//            x -= 1;
//        } else if (keys.containsKey(68)) {
//            x += 1;
//        }
//
//        if (keys.containsKey(83)) {
//            y += 1;
//        } else if (keys.containsKey(87)) {
//            y -= 1;
//        }
    }

    @Override
    public void render(Renderer r){
//        r.clear();
        r.clear(new Color(0.5,0.5,1.0));
//        r.test();

//        r.circleFill(new Point(x,y), 50,new Color(1.0,1.0,0.0, 0.5));
//
        int rad = 40;
        double d = angle * Math.PI / 180;
        int x2 = (int)(rad * Math.cos(d));
        int y2 = (int)(rad * Math.sin(d));
        angle++;

//        r.line(new Point(100,100), new Point(100 + x2,100 + y2), 5, new Color(1.0,0.0,0.0));
//        r.line(new Point(100,100), new Point(100 + x2,100 + y2), new Color(0.0,1.0,0.0));

//        r.circle(new Point(100,100), 50,new Color(1.0,1.0,0.0, 0.5));
//        r.circle(new Point(100,100), 50, 1, new Color(0.0,0.0,1.0, 0.5));

//        r.triangle(
//                new Point(50 + x2,100 - y2),
//                new Point(100,100 + y2),
//                new Point(150 - x2,150 + y2),
//                new Color(1.0,1.0,0.0));
//        r.triangle(
//                new Point(50 + x2,100 - y2),
//                new Point(100,100 + y2),
//                new Point(150 - x2,150 + y2),
//                1, new Color(0.0,1.0,0.0));

//        ArrayList<Point> points = new ArrayList<Point>();
//        points.add(new Point(100,30));
//        points.add(new Point(100 + x2, 50 + y2));
//        points.add(new Point(120 - x2,50 - y2));
//        points.add(new Point(120,30));
//        points.add(new Point(130 + x2,70));
//        points.add(new Point(70,60 + y2));
//        r.polygon(points, new Color(1.0,0.0,1.0, 0.5));
//        r.polygon(points, 1, new Color(0.0,1.0,1.0, 1.0));

//        ArrayList<Point> points = new ArrayList<Point>();
//        points.add(new Point(100,30));
//        points.add(new Point(120,30));
//        points.add(new Point(120,50));
//        points.add(new Point(100,50));
//
//        r.polygon(points, new Color(0.0,1.0,1.0, 0.5));

//        r.ellipse(new Point(100,100),50, 30, 1, new Color(1.0,1.0,1.0));

//        ArrayList<Point> points = new ArrayList<Point>();
//        points.add(new Point(50,50));
//        points.add(new Point(60,80));
//        points.add(new Point(100,20));
//        points.add(new Point(130,100));
//        r.bezierLine(points, 1, new Color(1.0,1.0,1.0));

//        r.rectangle(new Point(100, 100), 50, 50, 0 + x2, new Color(1.0,1.0,0.0));

//        r.ellipseRotate(new Point(100,100),50, 30, x2, new Color(1.0,1.0,1.0));
//        r.ellipseRotate(new Point(100,100),50, 20, x2, 2, new Color(1.0,1.0,0.0));


        r.drawImage(new Point(x, y), new Image("./bearly.png"));

//        stop();
    }
}