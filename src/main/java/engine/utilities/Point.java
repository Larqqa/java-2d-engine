package engine.utilities;

public class Point {

    private int x;
    private int y;

    public Point(final int x, final int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Point point) {
        this.x += point.getX();
        this.y += point.getY();
        return this;
    }

    public Point sub(Point point) {
        this.x -= point.getX();
        this.y -= point.getY();
        return this;
    }

    public Point mul(int t) {
        this.x *= t;
        this.y *= t;
        return this;
    }

    public Point mulP(Point point) {
        this.x *= point.getX();
        this.y *= point.getY();
        return this;
    }

    public Point div(int t) {
        this.x /= t;
        this.y /= t;
        return this;
    }

    public Point divP(Point point) {
        this.x /= point.getX();
        this.y /= point.getY();
        return this;
    }

    public Point toUnitVector() {
        double length = length();
        return new Point((int)(x / length), (int)(y / length));
    }

    public int dot(Point point) {
        return x * point.getX()
             + y * point.getY();
    }

    public Point cross(Point point) {
        return new Point(
            x * point.getY() - y * point.getX(),
            y * point.getX() - x * point.getY()
        );
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double length(Point point) {
        return Math.sqrt(lengthSquared(point));
    }

    public double lengthSquared() {
        return (double) x * x + y * y;
    }

    public double lengthSquared(Point point) {
        double distanceX = point.getX() - x;
        double distanceY = point.getY() - y;
        return distanceX * distanceX + distanceY * distanceY;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
