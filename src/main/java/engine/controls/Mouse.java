package engine.controls;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.HashMap;

public class Mouse implements MouseListener, MouseMotionListener, MouseWheelListener {

    private final HashMap<Integer, String> pressedButtons = new HashMap<>();
    private int mouseX;
    private int mouseY;
    private int wheelDirection;
    private int wheelAmount;
    private final double scale;

    public Mouse(final double scale) {
        this.scale = scale;
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        pressedButtons.put(mouseEvent.getButton(), "");
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        pressedButtons.remove(mouseEvent.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        mouseX = (int) (mouseEvent.getX() / scale);
        mouseY = (int) (mouseEvent.getY() / scale);
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