package engine.shapes;

import program.Settings;

public class Square {
    private int width;
    private int height;

    public Square(Settings settings) {
        width = settings.getWidth();
        height = settings.getHeight();
    }

    public boolean[] outline(int x, int y, int size, int weight) {
        boolean[] pixels = new boolean[size * size];

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (y + j >= height || y + j < 0) continue;
                if (x + i >= width || x + i < 0) continue;

                if (j < weight || j > size - (1 + weight) || i < weight || i > size - (1 + weight)) {
                    pixels[j * size + i] = true;
                }
            }
        }

        return pixels;
    }

    public boolean[] fill(int x, int y, int size) {
        boolean[] pixels = new boolean[size * size];

        for (int j = 0; j < size; j++) {
            for (int i = 0; i < size; i++) {
                if (y + j >= height || y + j < 0) continue;
                if (x + i >= width || x + i < 0) continue;
                pixels[j * size + i] = true;
            }
        }

        return pixels;
    }
}
