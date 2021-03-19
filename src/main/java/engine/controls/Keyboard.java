package engine.controls;

import engine.renderer.Renderer;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Objects;

public final class Keyboard implements KeyListener {

    private final HashMap<Integer, String> pressedKeys = new HashMap<>();
    private static Keyboard instance;

    public static Keyboard getInstance() {
        if(Objects.isNull(instance)) {
            instance = new Keyboard();
        }
        return instance;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        pressedKeys.put(e.getKeyCode(), KeyEvent.getKeyText(e.getKeyCode()));
    }

    @Override
    public void keyReleased(KeyEvent e) {
        pressedKeys.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    public boolean isKeyDown(int code) {
        return pressedKeys.containsKey(code);
    }

    public boolean isKeyDown(String key) {
        return pressedKeys.containsValue(key);
    }

    public HashMap<Integer, String> getPressedKeys() {
        return pressedKeys;
    }
}
