# LRQ Engine

## This is a simple 2D pixel rendering engine.

Example Class on how to use the engine and draw a line on a canvas:
```java
public class Main extends Program {

    private Window w;
    private PixelCanvas p;
    private Renderer r;

    public Main() {
        w = new Window.Builder()
                .setWidth(300)
                .setHeight(300)
                .setScale(2)
                .setTitle("Asteroids")
                .build();

        p = new PixelCanvas(w);
        r = p.getRenderer();

        setFrameCap(30);
        start();
    }

    @Override
    public void update() {
    }

    @Override
    public void render() {
        r.clear();
        r.line(new Point(10,10), new Point(100,150), Colors.red());
        p.paint();
    }

    public static void main(String[] args) {
        new Main();
    }
}
```

Currently implemented:
* 2D Point:
    * XY location as double
    * Basic vector arithmetic
        * Addition, substraction, multiplication, division
        * Conversion to unit vector
        * length
        * Cross product
        * Dot product
* RGBA Color
    * Conversion from doubles to uInt
    * Alpha Blending
* Drawing capabilities
    * Lines
        * Variable width lines
    * Circles
        * Variable width outline
        * Fill
    * Ellipses
        * Variable width outline
        * Fill
        * Variable angles
    * Triangle
        * Variable width outline
        * Fill
    * N point Polygon
        * Variable width outline
        * Fill
    * Rectangle
        * Variable angles
        * Drawn using a Polygon
    * N point Bezier curves
        * Variable width lines
    * Images
        * Images at an XY position
        * Transformations are done from original image
        * Rotation
        * Scaling
        * Tinting
        * Reset to original
     * Sprites
        * Basic sprite loop
        * Frame based delay
        * Sprites are handled as an image array
        * Scaling
        * Tinting
        * Rotation
        * Reset to original
* Mouse controls:
    * XY location
    * Scroll wheel
    * Pressed buttons hashmap<buttonNumber, "">
* Keyboard controls:
    * Pressed key hashmap<keyCode, value>
* The window is a Swing JFrame
* The pixel canvas is a Swing Canvas
    * The drawing happens through a bufferedImage from the canvas