package engine.renderer.shapes;

public abstract class Shape {
    public static void square(int x, int y, int size, boolean[] pixels, int wd) {
        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                pixels[(y + j) * wd + (x + i)] = true;
            }
        }
    }
}
