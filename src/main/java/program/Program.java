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

//        renderer.line(10,10, width - 10, 30, 5, 255,0,255,100);
//
//        renderer.circle(x, y, 50, 1, 255,255,0,150);
//        renderer.circle(x, y, 40, 20, 0,255,255,130);
//        renderer.circle(150, 150, 30,0,150,255,255);

//        renderer.triangleFill(80 - (int)x2,100 + (int)y2,150 + (int)x2,70 - (int)y2,100 - (int)x2,150 - (int)y2,255,255,255,150);

        renderer.triangleFill(100,0,0,200,200,200,255,155,0,100);
        renderer.circle(x, y, 100, 0,255,250,100);
        renderer.squareFill(x, y, 150, 150,150, 150, 100);

        int r = 50;
        double d = rad * Math.PI / 180;
        double x2 = (r * Math.cos(d));
        double y2 = (r * Math.sin(d));
        renderer.line(x, y, x +(int)x2, y + (int)y2, 3, 255,255,255,100);
        rad++;

    }
}