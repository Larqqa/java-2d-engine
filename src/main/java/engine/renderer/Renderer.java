package engine.renderer;

import engine.renderer.shapes.*;
import engine.utilities.Color;
import engine.Program;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public final class Renderer {

    private final int[] pixels;
    private static Renderer instance;

    private Renderer(final int[] pixels) {
        this.pixels = pixels;
    }

    public static Renderer getInstance(int[] pixels) {
        if(Objects.isNull(instance)) {
            instance = new Renderer(pixels);
        }

        return instance;
    }

    public static Renderer getInstance() {
        if(Objects.isNull(instance)) return null;
        return instance;
    }

    public void clear() {
        Arrays.fill(pixels, Program.getClearColor());
    }

    public void line(Point firstPoint, Point secondPoint, int lineWidth, Color color) {
        int minX = Math.min(firstPoint.getX(), secondPoint.getX());
        int minY = Math.min(firstPoint.getY(), secondPoint.getY());
        int maxX = Math.max(firstPoint.getX(), secondPoint.getX()) + lineWidth;
        boolean[] pixels = Line.plot(firstPoint, secondPoint, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minX - halfWidth, minY - halfWidth);

        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public void line(Point firstPoint, Point secondPoint, Color color) {
        line(firstPoint, secondPoint, 1, color);
    }

    public void circle(Point point, int radius, int lineWidth, Color color) {
        boolean[] pixels = Circle.plot(radius, lineWidth);
        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(point.getX() - radius - halfWidth,point.getY() - radius - halfWidth);
        draw(offsetPoint, pixels, radius * 2 + lineWidth, color);
    }

    public void circle(Point point, int radius, Color color) {
        boolean[] pixels = Circle.fill(radius);
        Point offsetPoint = new Point(point.getX() - radius,point.getY() - radius);
        draw(offsetPoint, pixels, radius * 2 + 1, color);
    }

    public void ellipse(Point point, int xRadius, int yRadius, int lineWidth, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.plot(xRadius, yRadius, lineWidth);

        int halfWidth = lineWidth / 2;
        int widthDiameter = xRadius * 2 + lineWidth;

        Point offsetPoint = new Point(point.getX() - xRadius - halfWidth,point.getY() - yRadius - halfWidth);
        draw(offsetPoint, pixels, widthDiameter, color);
    }

    public void ellipse(Point point, int xRadius, int yRadius, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.fill(xRadius, yRadius);

        int widthDiameter = xRadius * 2 + 1;

        Point offsetPoint = new Point(point.getX() - xRadius,point.getY() - yRadius);
        draw(offsetPoint, pixels, widthDiameter, color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth, Color color) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + lineWidth;
        boolean[] pixels = Triangle.plot(firstPoint, secondPoint, thirdPoint, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minX - halfWidth, minY - halfWidth);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, Color color) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + 1;
        boolean[] pixels = Triangle.fill(firstPoint, secondPoint, thirdPoint);

        Point offsetPoint = new Point(minX, minY);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public void polygon(ArrayList<Point> points, int lineWidth, Color color) {
        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (Point point : points) {
            int x = point.getX();
            int y = point.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
        maxX += lineWidth;

        boolean[] pixels = Polygon.plot(points, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minX - halfWidth, minY - halfWidth);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public void polygon(ArrayList<Point> points, Color color) {
        int minX = Program.getWidth();
        int minY = Program.getHeight();
        int maxX = 0;
        int maxY = 0;

        for (Point point : points) {
            int x = point.getX();
            int y = point.getY();

            if (x < minX) minX = x;
            if (y < minY) minY = y;

            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }
        maxX += 1;

        boolean[] pixels = Polygon.fill(points);

        Point offsetPoint = new Point(minX, minY);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public void test(){
        for(int y = 0; y < Program.getHeight(); y++) {
            for(int x = 0; x < Program.getWidth(); x++) {
                Color color = new Color(
                    (double) x / Program.getWidth(),
                    (double) (Program.getHeight() - y) / Program.getHeight(),
                    0.25
                );

                pixels[y * Program.getWidth() + x] = color.colorToInt();
            }
        }
    }

    public void draw(Point point, boolean[] shapeArray, int width, Color color) {
        for (int i = 0; i < shapeArray.length; i++) {
            int offsetX = point.getX() + (i % width);
            int offsetY = point.getY() + (i / width);

            if (offsetX < 0 || offsetY < 0) continue;
            if (offsetX >= Program.getWidth() || offsetY >= Program.getHeight()) continue;

            int pixelLocation = offsetY * Program.getWidth() + offsetX;

            if (shapeArray[i]) {
                pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
            }
        }
    }

    public void drawbg(Point point, boolean[] shapeArray, int width, Color color) {
        for (int i = 0; i < shapeArray.length; i++) {
            int offsetX = point.getX() + (i % width);
            int offsetY = point.getY() + (i / width);

            if (offsetX < 0 || offsetY < 0) continue;
            if (offsetX >= Program.getWidth() || offsetY >= Program.getHeight()) continue;

            int pixelLocation = offsetY * Program.getWidth() + offsetX;

            if (shapeArray[i]) {
                pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
            } else {
                Color backgroundColor = new Color(0.0,0.0,0.0,0.5);
                pixels[pixelLocation] = backgroundColor.alphaBlend(pixels[pixelLocation]);
            }
        }
    }

    public void drawPixel(Point point, Color color) {
        if (point.getX() < 0 || point.getY() < 0) return;
        if (point.getX() >= Program.getWidth() || point.getY() >= Program.getHeight()) return;

        int pixelLocation = point.getY() * Program.getWidth() + point.getX();
        pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
    }
}
