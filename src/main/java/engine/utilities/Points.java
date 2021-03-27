package engine.utilities;

public class Points {
    public static Point add(Point p1, Point p2) {
        return new Point(
            p1.getX() + p2.getX(),
            p1.getY() + p2.getY()
        );
    }

    public static Point sub(Point p1, Point p2) {
        return new Point(
            p1.getX() - p2.getX(),
            p1.getY() - p2.getY()
        );
    }

    public static Point mul(Point p, int t) {
        return new Point(
            p.getX() * t,
            p.getY() * t
        );
    }

    public static Point mul(Point p1, Point p2) {
        return new Point(
            p1.getX() * p2.getX(),
            p1.getY() * p2.getY()
        );
    }

    public static Point div(Point p, int t) {
        return new Point(
            p.getX() / t,
            p.getY() / t
        );
    }

    public static Point div(Point p1, Point p2) {
        return new Point(
            p1.getX() / p2.getX(),
            p1.getY() / p2.getY()
        );
    }
}
