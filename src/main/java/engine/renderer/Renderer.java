package engine.renderer;

import engine.renderer.shapes.*;
import engine.utilities.Color;
import engine.Program;
import engine.utilities.MinMax;
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
    public void clear(Color color) {
        Arrays.fill(pixels, color.colorToInt());
    }

    public void bezierLine(ArrayList<Point> points, int lineWidth, Color color) {
        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = BezierCurve.plot(points, lineWidth);
        Point offsetPoint = new Point(minMax.getMinX() - halfWidth, minMax.getMinY() - halfWidth);
        draw(offsetPoint, pixels, minMax.width(), color);
    }

    public void bezierLine(ArrayList<Point> points, Color color) {
        bezierLine(points, 1, color);
    }

    public void line(Point firstPoint, Point secondPoint, int lineWidth, Color color) {
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Line.plot(firstPoint, secondPoint, lineWidth);
        Point offsetPoint = new Point(minMax.getMinX() - halfWidth, minMax.getMinY() - halfWidth);
        draw(offsetPoint, pixels, minMax.getMaxX() - minMax.getMinX(), color);
    }

    public void line(Point firstPoint, Point secondPoint, Color color) {
        line(firstPoint, secondPoint, 1, color);
    }

    public void circle(Point point, int radius, int lineWidth, Color color) {
        int halfWidth = lineWidth / 2;
        boolean[] pixels = Circle.plot(radius, lineWidth);
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
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Ellipse.plot(xRadius, yRadius, lineWidth);

        Point offsetPoint = new Point(point.getX() - xRadius - halfWidth,point.getY() - yRadius - halfWidth);
        drawDebug(offsetPoint, pixels, xRadius * 2 + lineWidth, color);
    }

    public void ellipseRotate(Point point, int xRadius, int yRadius, int angle, int lineWidth, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Ellipse.plotRotation(xRadius, yRadius, angle, lineWidth);

        Point offsetPoint = new Point(point.getX() - xRadius - halfWidth,point.getY() - xRadius - halfWidth);
        draw(offsetPoint, pixels, xRadius * 2 + lineWidth, color);
    }

    public void ellipseRotate(Point point, int xRadius, int yRadius, int angle, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.fillRotation(xRadius, yRadius, angle, 1);

        Point offsetPoint = new Point(point.getX() - xRadius,point.getY() - xRadius);
        draw(offsetPoint, pixels, xRadius * 2 + 1, color);
    }

    public void ellipse(Point point, int xRadius, int yRadius, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.fill(xRadius, yRadius);
        Point offsetPoint = new Point(point.getX() - xRadius,point.getY() - yRadius);
        draw(offsetPoint, pixels, xRadius * 2 + 1, color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth, Color color) {
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        boolean[] pixels = Triangle.plot(firstPoint, secondPoint, thirdPoint, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minMax.getMinX() - halfWidth, minMax.getMinY() - halfWidth);
        draw(offsetPoint, pixels, minMax.width(), color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, Color color) {
        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, 1);
        boolean[] pixels = Triangle.fill(firstPoint, secondPoint, thirdPoint);

        Point offsetPoint = new Point(minMax.getMinX(), minMax.getMinY());
        draw(offsetPoint, pixels, minMax.width(), color);
    }

    public void polygon(ArrayList<Point> points, int lineWidth, Color color) {
        MinMax minMax = new MinMax(points, lineWidth);
        boolean[] pixels = Polygon.plot(points, lineWidth);

        int halfWidth = lineWidth / 2;
        Point offsetPoint = new Point(minMax.getMinX() - halfWidth, minMax.getMinY() - halfWidth);
        draw(offsetPoint, pixels, minMax.width(), color);
    }

    public void polygon(ArrayList<Point> points, Color color) {
        MinMax minMax = new MinMax(points, 1);
        boolean[] pixels = Polygon.fill(points);

        Point offsetPoint = new Point(minMax.getMinX(), minMax.getMinY());
        draw(offsetPoint, pixels, minMax.width(), color);
    }

    private Point calculateRectanglePoint(Point point, Point origin, double angle) {
        double length = point.length(origin);

        double deltaX = point.getX() - origin.getX();
        double deltaY = point.getY() - origin.getY();
        double newAngle = Math.atan2(deltaY, deltaX) + (angle * Math.PI / 180);

        double xa = Math.round(origin.getX() + length * Math.cos(newAngle));
        double ya = Math.round(origin.getY() + length * Math.sin(newAngle));

        return new Point(xa, ya);
    }

    public void rectangle(Point point, int width, int height, double angle, Color color) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        Point p1 = calculateRectanglePoint(new Point((point.getX() + halfWidth), (point.getY() + halfHeight)), point, angle);
        Point p2 = calculateRectanglePoint(new Point((point.getX() - halfWidth), (point.getY() + halfHeight)), point, angle);
        Point p3 = calculateRectanglePoint(new Point((point.getX() - halfWidth), (point.getY() - halfHeight)), point, angle);
        Point p4 = calculateRectanglePoint(new Point((point.getX() + halfWidth), (point.getY() - halfHeight)), point, angle);

        polygon(new ArrayList<>(Arrays.asList(p1, p2, p3, p4)), color);
    }

    public void rectangle(Point point, int width, int height, Color color) {
        rectangle(point,width,height,0,color);
    }

    public void drawImage(Point point, Image image) {
        for(int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int imageLoc = y * image.getWidth() + x;
                drawPixel(
                    new Point(
                        Math.round(x + point.getX() - (double) image.getWidth() / 2),
                        Math.round(y + point.getY() - (double) image.getHeight() / 2)),
                        new Color(image.getPixels()[imageLoc]));
            }
        }
    }

    public void drawSprite(Point p, Sprite sprite) {
        Image img = sprite.getSprites().get(sprite.getLoopCounter());
        drawImage(p, img);

        sprite.incrementLoopCounter();
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
            int offsetX = (int) Math.floor(point.getX()) + (i % width);
            int offsetY = (int) Math.floor(point.getY()) + (i / width);

            if (offsetX < 0 || offsetY < 0) continue;
            if (offsetX >= Program.getWidth() || offsetY >= Program.getHeight()) continue;

            int pixelLocation = offsetY * Program.getWidth() + offsetX;

            if (shapeArray[i]) {
                pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
            }
        }
    }

    public void drawDebug(Point point, boolean[] shapeArray, int width, Color color) {
        for (int i = 0; i < shapeArray.length; i++) {
            int offsetX = (int) Math.round(point.getX()) + (i % width);
            int offsetY = (int) Math.round(point.getY()) + (i / width);

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

        int pixelLocation = (int) (Math.round(point.getY()) * Program.getWidth() + Math.round(point.getX()));
        pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
        pixels[pixelLocation] = new Color(0.0,0.0,0.0,0.2).alphaBlend(pixels[pixelLocation]);
    }
}
