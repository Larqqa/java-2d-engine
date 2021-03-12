package engine.renderer;

import engine.renderer.shapes.Line;
import engine.renderer.shapes.Circle;
import engine.utilities.Color;
import engine.Program;
import engine.utilities.Point;
import lombok.Setter;

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
