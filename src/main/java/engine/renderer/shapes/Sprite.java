package engine.renderer.shapes;

import engine.renderer.Renderer;
import engine.utilities.Color;
import engine.utilities.Point;

public class Sprite {
    private Image sprite;
    private int width;
    private int height;
    private int spriteWidth;
    private int spriteHeight;
    private int loopCounter = 0;
    private int loopLength;
    private int offsetCounter = 0;
    private int frameDelay;

    public Sprite(String path, int spriteWidth, int spriteHeight, int frameDelay) {
        sprite = new Image(path);
        width = sprite.getWidth();
        height = sprite.getWidth();
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.frameDelay = frameDelay;
        loopLength = width / spriteWidth;
    }

    public void draw(Renderer r, Point p) {
        for (int y = 0; y < spriteHeight; y++) {
            for (int x = (spriteWidth * loopCounter); x < spriteWidth + (spriteWidth * loopCounter); x++) {
                Point np = new Point(p.getX() + x - (spriteWidth * loopCounter),p.getY()+ y);
                r.drawPixel(np, new Color(sprite.getPixels()[y * sprite.getWidth() + x]));
            }
        }

        offsetCounter++;
        if (offsetCounter % frameDelay == 0) {
            loopCounter++;
            if (loopCounter == loopLength) loopCounter = 0;
        }
    }
}
