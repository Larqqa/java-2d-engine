# WIP: A Java 2D Engine

### This is a simple 2D pixel drawing engine.

To use the engine:
* Extend the abstract Program class.
* Modify the settings with:
    * `setTitle(String)`
    * `setClearColor(uInt)`
    * `setWidth(int)`
    * `setHeight(int)`
    * `setScale(double)`
    * `setFrameCap(1.0 / double)`
* `start()` the engine
* Implement the abstract methods:
    * `abstract public void update(Mouse mouse, Keyboard keyboard);`
        * Update is run in "realtime"
    * `abstract public void render(Renderer renderer);`
        * Render is run "frameCap" times per second
        
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
* Mouse controls:
    * XY location
    * Scroll wheel
    * Pressed buttons hashmap<buttonNumber, "">
* Keyboard controls:
    * Pressed key hashmap<keyCode, value>
* The window is a Swing JFrame
* The pixel canvas is a Swing JPanel
    * The drawing happens through a bufferedImage from the canvas