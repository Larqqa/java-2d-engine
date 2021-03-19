package engine.controls;

import engine.Program;
import engine.renderer.Renderer;

import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;
import java.util.Objects;

public final class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final HashMap<Integer, String> pressedButtons = new HashMap<>();
    private int mouseX;
    private int mouseY;
    private int wheelDirection;
    private int wheelAmount;
    private final Insets insets;
    private static Mouse instance;

    public Mouse(final Insets insets) {
        this.insets =  insets;
    }

    public static Mouse getInstance(Insets insets) {
        if(Objects.isNull(instance)) {
            instance = new Mouse(insets);
        }

        return instance;
    }

    public static Mouse getInstance() {
        if(Objects.isNull(instance)) return null;
        return instance;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        System.out.println("yeet");
        pressedButtons.put(mouseEvent.getButton(), "");
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        pressedButtons.remove(mouseEvent.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = (int) (mouseEvent.getX() / Program.getScale());
        mouseY = (int) ((mouseEvent.getY() - (insets.top - 1)) / Program.getScale());
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent mouseWheelEvent) {
        wheelDirection = mouseWheelEvent.getWheelRotation();
        wheelAmount += mouseWheelEvent.getUnitsToScroll();
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        mouseMoved(mouseEvent);
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {}

    @Override
    public void mouseExited(MouseEvent mouseEvent) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    public void decrementMouseWheel() {
        if (wheelAmount > 0) {
            wheelAmount -= 1;
        } else if (wheelAmount < 0) {
            wheelAmount += 1;
        } else {
            wheelDirection = 0;
        }
    }

    public HashMap<Integer, String> getPressedButtons() {
        return pressedButtons;
    }

    public boolean isButtonDown(int code) {
        return pressedButtons.containsKey(code);
    }

    public int getMouseX() {
        return mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public int getWheelDirection() {
        return wheelDirection;
    }

    public int getWheelAmount() {
        return wheelAmount;
    }
}