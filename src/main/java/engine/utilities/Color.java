package engine.utilities;

import lombok.Getter;
import lombok.Setter;

public class Color {
    @Getter @Setter private double red;
    @Getter @Setter private double green;
    @Getter @Setter private double blue;
    @Getter @Setter private double alpha;

    public Color(final double red, final double green, final double blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = 1.0;
    }

    public Color(final double red, final double green, final double blue, final double alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public int colorToInt() {
        return 0xFF000000
            | ((int)(255 * red)   << 16) & 0x00FF0000
            | ((int)(255 * green) << 8)  & 0x0000FF00
            |  (int)(255 * blue)         & 0x000000FF;
    }

    // https://en.wikipedia.org/wiki/Alpha_compositing#Alpha_blending
    public int alphaBlend(int color) {
        double secondAlpha = ((color >> 24) & 0xFF) / 255.0;
        double secondRed   = ((color >> 16) & 0xFF) / 255.0;
        double secondGreen = ((color >> 8)  & 0xFF) / 255.0;
        double secondBlue  = ((color)       & 0xFF) / 255.0;

        double alphaBlend = (secondAlpha * (1 - alpha));
        double newAlpha = alpha + alphaBlend;

        double newRed   = (red   * alpha + secondRed   * alphaBlend) / newAlpha;
        double newGreen = (green * alpha + secondGreen * alphaBlend) / newAlpha;
        double newBlue  = (blue  * alpha + secondBlue  * alphaBlend) / newAlpha;

        return ((int)(255 * newAlpha) << 24) & 0xFF000000
             | ((int)(255 * newRed)   << 16) & 0x00FF0000
             | ((int)(255 * newGreen) << 8)  & 0x0000FF00
             |  (int)(255 * newBlue)         & 0x000000FF;
    }
}
