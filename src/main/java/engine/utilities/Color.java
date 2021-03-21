package engine.utilities;

public class Color {

    private double red;
    private double green;
    private double blue;
    private double alpha;

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

    public Color(final int color) {
        alpha = ((color >> 24) & 0xFF) / 255.0;
        red   = ((color >> 16) & 0xFF) / 255.0;
        green = ((color >> 8)  & 0xFF) / 255.0;
        blue  = ((color)       & 0xFF) / 255.0;
    }

    public int colorToInt() {
        return 0xFF000000
            | ((int)(255 * red)   << 16) & 0x00FF0000
            | ((int)(255 * green) << 8)  & 0x0000FF00
            |  (int)(255 * blue)         & 0x000000FF;
    }

    // https://en.wikipedia.org/wiki/Alpha_compositing#Alpha_blending
    public int alphaBlend(int color) {
        if (alpha == 1.0) {
            return colorToInt();
        }

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

    public double getRed() {
        return red;
    }

    public void setRed(double red) {
        this.red = red;
    }

    public double getGreen() {
        return green;
    }

    public void setGreen(double green) {
        this.green = green;
    }

    public double getBlue() {
        return blue;
    }

    public void setBlue(double blue) {
        this.blue = blue;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }
}
