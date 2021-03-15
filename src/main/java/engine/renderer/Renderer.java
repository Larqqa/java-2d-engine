package engine.renderer;

import engine.renderer.shapes.*;
import engine.utilities.Color;
import engine.Program;
import engine.utilities.Point;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

public class Renderer {
    @Setter static private int[] pixels;

    public static void clear() {
        Arrays.fill(pixels, Program.getClearColor());
    }

    public static void line(Point firstPoint, Point secondPoint, int lineWidth, Color color) {
        int minX = Math.min(firstPoint.getX(), secondPoint.getX());
        int minY = Math.min(firstPoint.getY(), secondPoint.getY());
        int maxX = Math.max(firstPoint.getX(), secondPoint.getX()) + lineWidth;
        boolean[] pixels = Line.plot(firstPoint, secondPoint, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minX - halfWidth, minY - halfWidth);

        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public static void line(Point firstPoint, Point secondPoint, Color color) {
        line(firstPoint, secondPoint, 1, color);
    }

    public static void circle(Point point, int radius, int lineWidth, Color color) {
        boolean[] pixels = Circle.plot(radius, lineWidth);
        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(point.getX() - radius - halfWidth,point.getY() - radius - halfWidth);
        draw(offsetPoint, pixels, radius * 2 + lineWidth, color);
    }

    public static void circle(Point point, int radius, Color color) {
        circle(point, radius, 1, color);
    }

    public static void circleFill(Point point, int radius, Color color) {
        boolean[] pixels = Circle.fill(radius);
        Point offsetPoint = new Point(point.getX() - radius,point.getY() - radius);
        draw(offsetPoint, pixels, radius * 2 + 1, color);
    }

    public static void ellipse(Point point, int xRadius, int yRadius, int lineWidth, Color color) {
        boolean[] pixels = Ellipse.plot(xRadius, yRadius, lineWidth);

        int halfWidth = lineWidth / 2;
        int widthDiameter = xRadius * 2 + lineWidth;

        Point offsetPoint = new Point(point.getX() - xRadius - halfWidth,point.getY() - yRadius - halfWidth);
//        Point offsetPoint = new Point(0,0);
        drawbg(offsetPoint, pixels, widthDiameter, color);
    }

    public static void ellipse(Point point, int xRadius, int yRadius, Color color) {
        boolean[] pixels = Ellipse.fill(xRadius, yRadius);

        int widthDiameter = xRadius * 2 + 1;

        Point offsetPoint = new Point(point.getX() - xRadius,point.getY() - yRadius);
        draw(offsetPoint, pixels, widthDiameter, color);
    }

    public static void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth, Color color) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + lineWidth;
        boolean[] pixels = Triangle.plot(firstPoint, secondPoint, thirdPoint, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minX - halfWidth, minY - halfWidth);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public static void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, Color color) {
        triangle(firstPoint, secondPoint, thirdPoint, 1, color);
    }

    public static void triangleFill(Point firstPoint, Point secondPoint, Point thirdPoint, Color color) {
        int minX = Math.min(Math.min(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX());
        int minY = Math.min(Math.min(firstPoint.getY(), secondPoint.getY()), thirdPoint.getY());
        int maxX = Math.max(Math.max(firstPoint.getX(),secondPoint.getX()), thirdPoint.getX()) + 1;
        boolean[] pixels = Triangle.fill(firstPoint, secondPoint, thirdPoint);

        Point offsetPoint = new Point(minX, minY);
        draw(offsetPoint, pixels, maxX - minX, color);
    }

    public static void polygon(ArrayList<Point> points, int lineWidth, Color color) {
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

    public static void polygon(ArrayList<Point> points, Color color) {
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

    public static void test(){
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

    public static void draw(Point point, boolean[] shapeArray, int width, Color color) {
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

    public static void drawbg(Point point, boolean[] shapeArray, int width, Color color) {
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
}
