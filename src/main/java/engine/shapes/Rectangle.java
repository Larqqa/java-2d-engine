package engine.shapes;

import engine.Program;

public class Rectangle {
    Triangle triangle;
    Line line;

    public Rectangle() {
        triangle = new Triangle();
        line = new Line();
    }

    public boolean[] noFill(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4, int size) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + size;
        int maxY = Math.max(Math.max(Math.max(y1,y2),y3), y4) + size;
        int minXo = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minYo = Math.min(Math.min(Math.min(y1,y2), y3), y4);

        boolean[] pixels = new boolean[(maxX - minXo) * (maxY - minYo)];
        int wd = (maxX - minXo);

        plotOutline(x1,y1,x2,y2,size,minXo,minYo,pixels,wd);
        plotOutline(x2,y2,x3,y3,size,minXo,minYo,pixels,wd);
        plotOutline(x3,y3,x4,y4,size,minXo,minYo,pixels,wd);
        plotOutline(x4,y4,x1,y1,size,minXo,minYo,pixels,wd);

        return pixels;
    }

    public boolean[] fill(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        int maxX = Math.max(Math.max(Math.max(x1,x2),x3), x4) + 1;
        int maxY = Math.max(Math.max(Math.max(y1,y2),y3), y4) + 1;
        int minXo = Math.min(Math.min(Math.min(x1,x2),x3), x4);
        int minYo = Math.min(Math.min(Math.min(y1,y2), y3), y4);

        boolean[] pixels = new boolean[(maxX - minXo) * (maxY - minYo)];
        int wd = (maxX - minXo);

        plotFill(x1,y1,x2,y2,x3,y3,minXo,minYo,pixels,wd);
        plotFill(x1,y1,x3,y3,x4,y4,minXo,minYo,pixels,wd);

        return pixels;
    }

    private void plotOutline(int x1, int y1, int x2, int y2, int size, int minXo, int minYo, boolean[] pixels, int wd) {
        int maxX = Math.max(x1,x2) + size;
        int minX = Math.min(x1,x2);
        int minY = Math.min(y1,y2);
        boolean[] lineArray = line.draw(x1,y1,x2,y2,size);

        int x = minX - minXo;
        int y = minY - minYo;

        int width = maxX - minX;

        for (int i = 0; i < lineArray.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (lineArray[i]) {
                pixels[(cy + y) * wd + (cx + x)] = lineArray[i];
            }
        }
    }

    private void plotFill(int x1, int y1, int x2, int y2, int x3, int y3, int minXo, int minYo, boolean[] pixels, int wd) {
        int maxX = Math.max(Math.max(x1,x2),x3) + 1;
        int minX = Math.min(Math.min(x1,x2),x3);
        int minY = Math.min(Math.min(y1,y2),y3);

        int x = minX - minXo;
        int y = minY - minYo;

        boolean[] second = triangle.fill(x1,y1,x2,y2,x3,y3);
        int width = maxX - minX;
        for (int i = 0; i < second.length; i++) {
            int cx = i % width;
            int cy = i / width;
            if (second[i]) {
                pixels[(cy + y) * wd + (cx + x)] = second[i];
            }
        }
    }
}
