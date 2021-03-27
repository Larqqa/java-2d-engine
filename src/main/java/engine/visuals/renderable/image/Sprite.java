package engine.visuals.renderable.image;

import engine.utilities.Color;

import java.util.ArrayList;

public class Sprite {
    private final ArrayList<Image> sprites;

    private final int width;
    private final int height;
    private final int spriteWidth;
    private final int spriteHeight;
    private final int loopLength;
    private final int frameDelay;
    private int offsetCounter = 0;
    private int loopCounter = 0;

    public Sprite(final String path, final int spriteWidth, final int spriteHeight, final int frameDelay) {
        Image spriteSheet = new Image(path);
        width = spriteSheet.getWidth();
        height = spriteSheet.getWidth();
        loopLength = width / spriteWidth;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.frameDelay = frameDelay;

        sprites = new ArrayList<>();
        for (int l = 0; l < loopLength; l++) {
            int[] pixels = new int[spriteWidth * spriteHeight];

            for (int y = 0; y < spriteHeight; y++) {
                for (int x = (spriteWidth * l); x < spriteWidth + (spriteWidth * l); x++) {
                    pixels[y * spriteWidth + (x - (spriteWidth * l))] = spriteSheet.getPixels()[y * width + x];
                }
            }

            sprites.add(new Image(pixels, spriteWidth, spriteHeight));
        }
    }

    public void scale(double scale) {
        for (Image sprite : sprites) {
            sprite.scale(scale);
        }
    }

    public void scale(double xScale, double yScale) {
        for (Image sprite : sprites) {
            sprite.scale(xScale, yScale);
        }
    }

    public void tint(Color color) {
        for (Image sprite : sprites) {
            sprite.tint(color);
        }
    }

    public void rotate(double angle) {
        for (Image sprite : sprites) {
            sprite.rotate(angle);
        }
    }

    public void incrementLoopCounter() {
        offsetCounter++;
        if (offsetCounter % frameDelay == 0) {
            loopCounter++;
            if (loopCounter == loopLength) loopCounter = 0;
        }
    }

    public int getSpriteWidth() {
        return spriteWidth;
    }

    public int getSpriteHeight() {
        return spriteHeight;
    }

    public int getLoopCounter() {
        return loopCounter;
    }

    public int getWidth() {
        return width;
    }

    public ArrayList<Image> getSprites() {
        return sprites;
    }
}
