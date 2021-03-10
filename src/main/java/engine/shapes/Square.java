package engine.shapes;

public class Square {
    public Square() { }

    public boolean[] outline(int size, int weight) {
        boolean[] pixels = new boolean[size * size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (y < weight || y > size - (1 + weight) ||
                    x < weight || x > size - (1 + weight)) {
                    pixels[y * size + x] = true;
                }
            }
        }

        return pixels;
    }

    public boolean[] fill(int size) {
        boolean[] pixels = new boolean[size * size];

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                pixels[y * size + x] = true;
            }
        }

        return pixels;
    }
}
