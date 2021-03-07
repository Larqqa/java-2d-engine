package engine.shapes;

import program.Settings;

public class Rectangle {
    int width;
    int height;
    Triangle triangle;
    Line line;

    public Rectangle(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();

        triangle = new Triangle(settings);
        line = new Line(settings);
    }

    public boolean[] noFill(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + size;
        int maxY = Math.max(Math.max(Math.max(y1,y2),y3), y4) + size;
        int minXo = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minYo = Math.min(Math.min(Math.min(y1,y2), y3), y4);

        boolean[] pixels = new boolean[(maxX - minXo) * (maxY - minYo)];

        int wd = (maxX - minXo);

        maxX = Math.max(x1,x2) + size;
        int minX = Math.min(x1,x2);
        int minY = Math.min(y1,y2);
        boolean[] first = line.withWeight(x1 - minX,y1- minY,x2- minX,y2- minY,size);

        int x = minX - minXo;
        int y = minY - minYo;

        int width = maxX - minX;

        for (int i = 0; i < first.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (first[i]) {
                pixels[(cy + y) * wd + (cx + x)] = first[i];
            }
        }

        maxX = Math.max(x2,x3) + size;
        minX = Math.min(x2,x3);
        minY = Math.min(y2,y3);
        boolean[] second = line.withWeight(x2 - minX,y2- minY,x3- minX,y3- minY,size);

        x = minX - minXo;
        y = minY - minYo;

        width = maxX - minX;

        for (int i = 0; i < second.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (second[i]) {
                pixels[(cy + y) * wd + (cx + x)] = second[i];
            }
        }

        maxX = Math.max(x3,x4) + size;
        minX = Math.min(x3,x4);
        minY = Math.min(y3,y4);
        boolean[] third = line.withWeight(x3 - minX,y3- minY,x4 - minX,y4- minY,size);

        x = minX - minXo;
        y = minY - minYo;

        width = maxX - minX;

        for (int i = 0; i < third.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (third[i]) {
                pixels[(cy + y) * wd + (cx + x)] = third[i];
            }
        }

        maxX = Math.max(x4,x1) + size;
        minX = Math.min(x4,x1);
        minY = Math.min(y4,y1);
        boolean[] fourth = line.withWeight(x4 - minX,y4- minY,x1- minX,y1- minY,size);

        x = minX - minXo;
        y = minY - minYo;

        width = maxX - minX;

        for (int i = 0; i < fourth.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (fourth[i]) {
                pixels[(cy + y) * wd + (cx + x)] = fourth[i];
            }
        }

        return pixels;
    }

    public boolean[] fill(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + 1;
        int maxY = Math.max(Math.max(Math.max(y1,y2),y3), y4) + 1;
        int minXo = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minYo = Math.min(Math.min(Math.min(y1,y2), y3), y4);

        boolean[] pixels = new boolean[(maxX - minXo) * (maxY - minYo)];

        int wd = (maxX - minXo);

        maxX = Math.max(Math.max(x1,x2),x3) + 1;
        int minX = Math.min(Math.min(x1,x2),x3);
        int minY = Math.min(Math.min(y1,y2), y3);
        boolean[] first = triangle.fill(x1 - minX,y1- minY,x2- minX,y2- minY,x3- minX,y3- minY);

        int x = minX - minXo;
        int y = minY - minYo;

        int width = maxX - minX;

        for (int i = 0; i < first.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (first[i]) {
                pixels[(cy + y) * wd + (cx + x)] = first[i];
            }
        }


        maxX = Math.max(Math.max(x1,x3),x4) + 1;
        minX = Math.min(Math.min(x1,x3),x4);
        minY = Math.min(Math.min(y1,y3), y4);

        x = minX - minXo;
        y = minY - minYo;

        boolean[] second = triangle.fill(x1 - minX,y1- minY,x3- minX,y3- minY,x4- minX,y4- minY);
        width = maxX - minX;
        for (int i = 0; i < second.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (second[i]) {
                pixels[(cy + y) * wd + (cx + x)] = second[i];
            }
        }

        return pixels;
    }
}
