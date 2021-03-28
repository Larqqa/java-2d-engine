package engine.utilities;

public class Point {

    private double x;
    private double y;

    public Point(final double x, final double y) {
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
        return new Point(x / length, y / length);
    }

    public double dot(Point point) {
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
        return x * x + y * y;
    }

    public double lengthSquared(Point point) {
        double distanceX = point.getX() - x;
        double distanceY = point.getY() - y;
        return distanceX * distanceX + distanceY * distanceY;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Point)) {
            return false;
        }

        Point c = (Point) o;

        return Double.compare(x, c.getX()) == 0 && Double.compare(y, c.getY()) == 0;
    }

    @Override
    public String toString() {
        return "{x: " + x + ", y: " + y + "}";
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
