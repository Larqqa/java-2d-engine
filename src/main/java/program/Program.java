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

//        renderer.circle(x, y, 100, 0,255,250,100);
//        renderer.square(x, y, 150, 150,150, 150, 100);
//
//        int r = 50;
//        double d = rad * Math.PI / 180;
//        double x2 = (r * Math.cos(d));
//        double y2 = (r * Math.sin(d));
//        renderer.line(x, y, x +(int)x2, y + (int)y2, 3, 255,255,0,100);
//        renderer.triangle(80 - (int)x2,100 + (int)y2,150 + (int)x2,70 - (int)y2,100 - (int)x2,150 - (int)y2, 1,255,255,150,100);
//        renderer.triangle(80 - (int)x2,100 + (int)y2,150 + (int)x2,70 - (int)y2,100 - (int)x2,150 - (int)y2, 255,255,150,100);
//        rad++;

//        renderer.rectangle(50,50,100,50,100,100,50,100,2,255,255,255,255);
    }
}