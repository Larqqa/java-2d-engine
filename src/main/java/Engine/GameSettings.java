package Engine;

public class GameSettings {
    private int width = 300;
    private int height = 300;
    private float scale = 2.0f;
    private String title = "New Game";
    private double fpscap = 1.0 / 60.0;

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public float getScale() {
        return scale;
    }

    public String getTitle() {
        return title;
    }

    public double getFpscap() {
        return fpscap;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setFpscap(double fpscap) {
        this.fpscap = fpscap;
    }
}
