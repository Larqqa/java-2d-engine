package engine;

import engine.shapes.*;
import lombok.Setter;

import java.util.Arrays;

public class Renderer {
    @Setter private int[] pixels;
    private final Square square;
    private final Line line;
    private final Circle circle;
    private final Triangle triangle;
    private final Rectangle rectangle;
    private final Pixel pixel;

    public Renderer() {
        square = new Square();
        line = new Line();
        circle = new Circle();
        triangle = new Triangle();
        rectangle = new Rectangle();
        pixel = new Pixel(pixels);
    }

    public void square(int x, int y, int size, int weight, int r, int g, int b, int a) {
        int halfSize = size / 2;
        int halfWeight = weight / 2;
        int newSize = size + weight;

        x -= halfSize + halfWeight;
        y -= halfSize + halfWeight;

        boolean[] pixels = square.outline(newSize, weight);
        draw(x, y, pixels, newSize, r,g,b,a);
    }

    public void square(int x, int y, int size, int r, int g, int b, int a) {
        int half = size / 2;
        x -= half;
        y -= half;

        boolean[] pixels = square.fill(size);
        draw(x, y, pixels, size, r,g,b,a);
    }

    public void line(int x1, int y1, int x2, int y2, int r, int g, int b, int a) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int maxX = Math.max(x1, x2) + 1;

        boolean[] pixels = line.draw(x1, y1, x2, y2, 1);
        draw(minX, minY, pixels, maxX - minX, r,g,b,a);
    }

    public void line(int x1, int y1, int x2, int y2, int width, int r, int g, int b, int a) {
        int minX = Math.min(x1, x2);
        int minY = Math.min(y1, y2);
        int maxX = Math.max(x1, x2) + width;

        boolean[] pixels = line.draw(x1, y1, x2, y2, width);
        draw(minX - width / 2, minY - 1, pixels, maxX - minX, r,g,b,a);
    }

    public void circle(int x, int y, int outerRadius, int innerRadius, int r, int g, int b, int a) {
        x -= outerRadius;
        y -= outerRadius;

        boolean[] pixels = circle.draw(outerRadius, innerRadius);
        draw(x, y, pixels, outerRadius * 2, r,g,b,a);
    }

    public void circle(int x, int y, int radius, int r, int g, int b, int a) {
        x -= radius;
        y -= radius;

        boolean[] pixels = circle.draw(radius, radius);
        draw(x, y, pixels, radius * 2, r,g,b,a);
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3, int width, int r, int g, int b, int a) {
        int maxX = Math.max(Math.max(x1,x2), x3) + width;
        int minX = Math.min(Math.min(x1,x2), x3);
        int minY = Math.min(Math.min(y1,y2), y3);
        int wd = maxX - minX;

        boolean[] pixels = triangle.noFill(x1, y1, x2, y2, x3, y3, width);
        draw(minX - width / 2, minY - width / 2, pixels, wd, r,g,b,a);
    }

    public void triangle(int x1, int y1, int x2, int y2, int x3, int y3, int r, int g, int b, int a) {
        int maxX = Math.max(Math.max(x1,x2), x3) + 1;
        int minX = Math.min(Math.min(x1,x2), x3);
        int minY = Math.min(Math.min(y1,y2), y3);
        int wd = maxX - minX;

        boolean[] pixels = triangle.fill(x1, y1, x2, y2, x3, y3);
        draw(minX, minY, pixels, wd, r,g,b,a);
    }

    public void rectangle(int x1, int y1, int x2, int y2, int x3, int y3,int x4, int y4, int r, int g, int b, int a) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + 1;
        int minX = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minY = Math.min(Math.min(Math.min(y1,y2), y3), y4);
        int wd = maxX - minX;

        boolean[] pixels = rectangle.fill(x1,y1, x2,y2, x3,y3, x4,y4);
        draw(minX, minY, pixels, wd, r,g,b,a);
    }

    public void rectangle(int x1, int y1, int x2, int y2, int x3, int y3,int x4, int y4, int size, int r, int g, int b, int a) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + size;
        int minX = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minY = Math.min(Math.min(Math.min(y1,y2), y3), y4);
        int wd = maxX - minX;

        boolean[] pixels = rectangle.noFill(x1,y1, x2,y2, x3,y3, x4,y4, size);
        drawbg(minX - size / 2, minY - size / 2, pixels, wd, r,g,b,a);
    }

    public void draw( int x, int y, boolean[] pixels, int width, int r,int g, int b, int a) {
        for (int i = 0; i < pixels.length; i++) {
            int cx = x + (i % width);
            int cy = y + (i / width);

            if (pixels[i]) {
                pixel.colorPixel(cx, cy, r, g, b, a);
            }
        }
    }

    public void drawbg( int x, int y, boolean[] pixels, int width, int r,int g, int b, int a) {
        for (int i = 0; i < pixels.length; i++) {
            int cx = x + (i % width);
            int cy = y + (i / width);

            pixel.colorPixel(cx, cy, 0,0,0,150);

            if (pixels[i]) {
                pixel.colorPixel(cx, cy, r, g, b, a);
            }
        }
    }

    public void clear() {
        Arrays.fill(pixels, Program.clearColor);
    }

    public void test(){
        for(int y = 0; y < Program.height; y++) {
            for(int x = 0; x < Program.width; x++) {
                int r = (int)(255 * (double)x / Program.width);
                int g = (int)(255 * (double)(Program.height - y) / Program.height);
                int b = (int)(255 * 0.25);

                pixel.colorPixel(x, y, r, g, b);
            }
        }
    }

    public void colorPixel(int x, int y, int r, int g, int b) {
        pixel.colorPixel(x,y,r,g,b);
    }

    public void colorPixel(int x, int y, int r, int g, int b, int a) {
        pixel.colorPixel(x,y,r,g,b,a);
    }
}