package engine.renderer;

import engine.utilities.Color;
import engine.Program;
import lombok.Setter;

import java.util.Arrays;

public class Renderer {
    @Setter static private int[] pixels;

    public static void clear() {
        Arrays.fill(pixels, Program.getClearColor());
    }

    public static void test(){
        for(int y = 0; y < Program.getHeight(); y++) {
            for(int x = 0; x < Program.getWidth(); x++) {
                Color color = new Color(
                    (double) x / Program.getWidth(),
                    (double) (Program.getHeight() - y) / Program.getHeight(),
                    255 * 0.25
                );

                pixels[y * Program.getWidth() + x] = color.colorToInt();
            }
        }
    }
}
