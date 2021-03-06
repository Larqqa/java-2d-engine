package program;

import lombok.Getter;
import engine.Renderer;
import engine.PixelCanvas;

public class Program {
    private final int width;
    private final int height;
    private final Renderer renderer;
    private int f = 0;
    private int rad = 0;

    public Program(Settings settings, PixelCanvas canvas) {
        width = settings.getWidth();
        height = settings.getHeight();
        renderer = new Renderer(settings, canvas);
    }

    public void update(int f) {
        this.f = f;
    }

    public void render(){
        renderer.clear();
        renderer.test();

        int x = 100;
        int y = 100;

//        renderer.squareFill(100,100, 100, 255, 255, 255, 255);
//        renderer.squareFill(50, 50, 50,  255, 0, 0, 100);
//        renderer.squareFill(80, 40, 50, 0, 255, 0, 100);
//        renderer.square(65, 65, 50, 5, 0, 0, 255, 100);
//
//        int r = 50;
//        double d = rad * Math.PI / 180;
//        double x2 = x + (r * Math.cos(d));
//        double y2 = y + (r * Math.sin(d));
//        renderer.line(x, y, (int)x2, (int)y2, 3, 255,255,255,100);
//        rad--;
//
//        renderer.line(10,10, width - 10, 30, 5, 255,0,255,100);
//
////        renderer.circle(x, y, 50, 1, 255,255,0,150);
//        renderer.circle(x, y, 40, 20, 0,255,255,130);
//        renderer.circle(150, 150, 30,0,150,255,255);

        renderer.triangle(70,50,150,100,50,100,3,255,255,255,255);

    }
}