package engine.renderer.shapes;

public abstract class Shape {
    public static void square(int x, int y, int size, boolean[] pixels, int wd) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                pixels[(y + j) * wd + (x + i)] = true;
            }
        }
    }

    public static void combinePixels(int offsetX, int offsetY, boolean[] shape, int shapeWidth, boolean[] target, int targetWidth) {
        for (int i = 0; i < shape.length; i++) {
            int x = (i % shapeWidth);
            int y = (i / shapeWidth);
            if (shape[i]) {
                target[(offsetY + y) * targetWidth + (offsetX + x)] = true;
            }
        }
    }
}
