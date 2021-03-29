package engine.visuals;

import engine.visuals.renderable.image.Image;
import engine.visuals.renderable.image.Sprite;
import engine.visuals.renderable.shapes.BezierCurve;
import engine.visuals.renderable.shapes.Circle;
import engine.visuals.renderable.shapes.Ellipse;
import engine.visuals.renderable.shapes.Line;
import engine.visuals.renderable.shapes.Polygon;
import engine.visuals.renderable.shapes.Triangle;
import engine.utilities.Color;
import engine.utilities.MinMax;
import engine.utilities.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Renderer {

    private final int[] pixels;
    private final int width;
    private final int height;
    private int clearColor = 0xFF000000;

    public Renderer(final int[] pixels, final int width, final int height) {
        this.pixels = pixels;
        this.width = width;
        this.height = height;
    }

    public void clear() {
        Arrays.fill(pixels, clearColor);
    }
    public void clear(Color color) {
        Arrays.fill(pixels, color.colorToInt());
    }

    public void bezierLine(ArrayList<Point> points, int lineWidth, Color color) {
        ArrayList<Point> pEquals = new ArrayList<>();
        for (Point point : points) {
            if (pEquals.contains(point)) continue;
            pEquals.add(point);
        }
        points = pEquals;

        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = BezierCurve.plot(points, lineWidth);

        draw(new Point(
                minMax.getMinX() - halfWidth,
                minMax.getMinY() - halfWidth),
                pixels, (int) minMax.width(), color);
    }

    public void bezierLine(ArrayList<Point> points, Color color) {
        bezierLine(points, 1, color);
    }

    public void line(Point firstPoint, Point secondPoint, int lineWidth, Color color) {
        firstPoint = new Point(Math.round(firstPoint.getX()), Math.round(firstPoint.getY()));
        secondPoint = new Point(Math.round(secondPoint.getX()), Math.round(secondPoint.getY()));

        ArrayList<Point> points = new ArrayList<>(Arrays.asList(firstPoint, secondPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Line.plot(firstPoint, secondPoint, lineWidth);

        draw(new Point(
                minMax.getMinX() - halfWidth,
                minMax.getMinY() - halfWidth),
                pixels, (int) minMax.getMaxX() - (int) minMax.getMinX(), color);
    }

    public void line(Point firstPoint, Point secondPoint, Color color) {
        line(firstPoint, secondPoint, 1, color);
    }

    public void circle(Point point, int radius, int lineWidth, Color color) {
        int halfWidth = lineWidth / 2;
        boolean[] pixels = Circle.plot(radius, lineWidth);

        draw(new Point(
                point.getX() - radius - halfWidth,
                point.getY() - radius - halfWidth),
                pixels, radius * 2 + lineWidth, color);
    }

    public void circle(Point point, int radius, Color color) {
        boolean[] pixels = Circle.fill(radius);

        draw(new Point(
                point.getX() - radius,
                point.getY() - radius),
                pixels, radius * 2 + 1, color);
    }

    public void ellipse(Point point, int xRadius, int yRadius, int lineWidth, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Ellipse.plot(xRadius, yRadius, lineWidth);

        drawDebug(new Point(
                point.getX() - xRadius - halfWidth,
                point.getY() - yRadius - halfWidth),
                pixels, xRadius * 2 + lineWidth, color);
    }

    public void ellipseRotate(Point point, int xRadius, int yRadius, int angle, int lineWidth, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Ellipse.plotRotation(xRadius, yRadius, angle, lineWidth);

        draw(new Point(
                point.getX() - xRadius - halfWidth,
                point.getY() - xRadius - halfWidth),
                pixels, xRadius * 2 + lineWidth, color);
    }

    public void ellipseRotate(Point point, int xRadius, int yRadius, int angle, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.fillRotation(xRadius, yRadius, angle, 1);

        draw(new Point(
                point.getX() - xRadius,
                point.getY() - xRadius),
                pixels, xRadius * 2 + 1, color);
    }

    public void ellipse(Point point, int xRadius, int yRadius, Color color) {
        if (xRadius < 2) xRadius = 2;
        if (yRadius < 2) yRadius = 2;

        boolean[] pixels = Ellipse.fill(xRadius, yRadius);

        draw(new Point(
                point.getX() - xRadius,
                point.getY() - yRadius),
                pixels, xRadius * 2 + 1, color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, int lineWidth, Color color) {
        ArrayList<Point> points = new ArrayList<>(List.of(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Triangle.plot(firstPoint, secondPoint, thirdPoint, lineWidth);

        draw(new Point(
                minMax.getMinX() - halfWidth,
                minMax.getMinY() - halfWidth),
                pixels, (int) minMax.width(), color);
    }

    public void triangle(Point firstPoint, Point secondPoint, Point thirdPoint, Color color) {
        ArrayList<Point> points = new ArrayList<>(List.of(firstPoint, secondPoint, thirdPoint));
        MinMax minMax = new MinMax(points, 1);

        boolean[] pixels = Triangle.fill(firstPoint, secondPoint, thirdPoint);

        draw(new Point(
                minMax.getMinX(),
                minMax.getMinY()),
                pixels, (int) minMax.width(), color);
    }

    public void polygon(ArrayList<Point> points, int lineWidth, Color color) {
        MinMax minMax = new MinMax(points, lineWidth);
        int halfWidth = lineWidth / 2;

        boolean[] pixels = Polygon.plot(points, lineWidth);

        draw(new Point(
                minMax.getMinX() - halfWidth,
                minMax.getMinY() - halfWidth),
                pixels, (int) minMax.width(), color);
    }

    public void polygon(ArrayList<Point> points, Color color) {
        MinMax minMax = new MinMax(points, 1);

        boolean[] pixels = Polygon.fill(points);

        draw(new Point(
                minMax.getMinX(),
                minMax.getMinY()),
                pixels, (int) minMax.width(), color);
    }

    private Point calculateRectanglePoint(Point point, Point origin, double angle) {
        double length = point.length(origin);
        double newAngle = Math.atan2(
                point.getY() - origin.getY(),
                point.getX() - origin.getX())
                + (angle * Math.PI / 180);

        return new Point(
                Math.round(origin.getX() + length * Math.cos(newAngle)),
                Math.round(origin.getY() + length * Math.sin(newAngle)));
    }

    public void rectangle(Point point, int width, int height, double angle, Color color) {
        int halfWidth = width / 2;
        int halfHeight = height / 2;

        polygon(new ArrayList<>(List.of(
            calculateRectanglePoint(new Point((point.getX() + halfWidth), (point.getY() + halfHeight)), point, angle),
            calculateRectanglePoint(new Point((point.getX() - halfWidth), (point.getY() + halfHeight)), point, angle),
            calculateRectanglePoint(new Point((point.getX() - halfWidth), (point.getY() - halfHeight)), point, angle),
            calculateRectanglePoint(new Point((point.getX() + halfWidth), (point.getY() - halfHeight)), point, angle)
        )), color);
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
    }

    public void test(){
        for(int y = 0; y < this.height; y++) {
            for(int x = 0; x < this.width; x++) {
                Color color = new Color(
                    (double) x / this.width,
                    (double) (this.height - y) / this.height,
                    0.25
                );

                pixels[y * this.width + x] = color.colorToInt();
            }
        }
    }

    public void draw(Point point, boolean[] shapeArray, int width, Color color) {
        for (int i = 0; i < shapeArray.length; i++) {
            int offsetX = (int) Math.floor(point.getX()) + (i % width);
            int offsetY = (int) Math.floor(point.getY()) + (i / width);

            if (offsetX < 0 || offsetY < 0) continue;
            if (offsetX >= this.width || offsetY >= this.height) continue;

            int pixelLocation = offsetY * this.width + offsetX;

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
            if (offsetX >= this.width || offsetY >= this.height) continue;

            int pixelLocation = offsetY * this.width + offsetX;

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
        if (point.getX() >= this.width || point.getY() >= this.height) return;

        int pixelLocation = (int) (Math.round(point.getY()) * this.width + Math.round(point.getX()));
        pixels[pixelLocation] = color.alphaBlend(pixels[pixelLocation]);
    }

    public int getClearColor() {
        return clearColor;
    }

    public void setClearColor(Color color) {
        this.clearColor = color.colorToInt();
    }
}
