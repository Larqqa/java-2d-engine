package engine.shapes;

import program.Settings;

public class Rectangle {
    int width;
    int height;
    Triangle triangle;

    public Rectangle(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();

        triangle = new Triangle(settings);
    }

    public boolean[] noFill(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size) {
        boolean[] first = triangle.noFill(x1,y1,x2,y2,x3,y3, size);
        boolean[] second = triangle.noFill(x1,y1,x3,y3,x4,y4, size);
        boolean[] pixels = new boolean[1];

        for (int i = 0; i < first.length; i++) {
            int width = Math.max(Math.max(x1,x2), x3) - Math.min(Math.min(x1,x2), x3);
            int x = i % width;
            int y = i / width;
            pixels[y * width + x] = first[i];
        }

        return pixels;
    }
}
