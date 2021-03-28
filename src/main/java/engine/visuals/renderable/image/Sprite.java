package engine.visuals.renderable.image;

import engine.utilities.Color;

import java.util.ArrayList;

import static java.util.stream.Collectors.toCollection;

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

    private Sprite(ArrayList<Image> sprites,
                   int width, int height,
                   int spriteWidth, int spriteHeight,
                   int loopLength, int frameDelay,
                   int offsetCounter, int loopCounter) {
        this.sprites = sprites;
        this.width = width;
        this.height = height;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.loopLength = loopLength;
        this.frameDelay = frameDelay;
        this.offsetCounter = offsetCounter;
        this.loopCounter = loopCounter;
    }

    public Sprite scale(double xScale, double yScale) {
        return new Sprite(sprites.stream()
            .map(sprite -> sprite.scale(xScale, yScale))
            .collect(toCollection(ArrayList::new)),
                width, height,
                spriteWidth, spriteHeight,
                loopLength, frameDelay,
                offsetCounter, loopCounter
        );
    }

    public Sprite scale(double scale) {
        return scale(scale, scale);
    }

    public Sprite tint(Color color) {
        return new Sprite(sprites.stream()
            .map(sprite -> sprite.tint(color))
            .collect(toCollection(ArrayList::new)),
                width, height,
                spriteWidth, spriteHeight,
                loopLength, frameDelay,
                offsetCounter, loopCounter
        );
    }

    public Sprite rotate(double angle) {
        return new Sprite(sprites.stream()
            .map(sprite -> sprite.rotate(angle))
            .collect(toCollection(ArrayList::new)),
                width, height,
                spriteWidth, spriteHeight,
                loopLength, frameDelay,
                offsetCounter, loopCounter
        );
    }

    public Sprite shear(double xShear, double yShear) {
        return new Sprite(sprites.stream()
                .map(sprite -> sprite.shear(xShear, yShear))
                .collect(toCollection(ArrayList::new)),
                width, height,
                spriteWidth, spriteHeight,
                loopLength, frameDelay,
                offsetCounter, loopCounter
        );
    }

    public Sprite shear(double shear) {
        return shear(shear, shear);
    }

    public Sprite toOriginal() {
        return new Sprite(sprites.stream()
            .map(Image::toOriginal)
            .collect(toCollection(ArrayList::new)),
                width, height,
                spriteWidth, spriteHeight,
                loopLength, frameDelay,
                offsetCounter, loopCounter
        );
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
