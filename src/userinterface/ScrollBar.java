package userinterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollBar extends Frame {
    private final static int thicknessOuterBar = 8;
    private final static int thicknessInnerBar = 4;
    private double fraction = 0.0;
    private int innerBarLength;
    private int length;
    private final int offset = 2;
    private final Orientation orientation;
    private int[] prevMouse;

    private final Color innerColorNormal = Color.gray;
    private final Color innerColorDragging = Color.blue;
    private Color currentColor = innerColorNormal;

    public ScrollBar(int x, int y, int length, boolean isHorizontal, double ratio, ScrollListener listener) {
        super(x, y, 0, 0);
        if (isHorizontal)
            orientation = new Horizontal();
        else
            orientation = new Vertical();
        orientation.init();
        innerBarLength = (int) Math.round(length/ratio);
        this.length = length;
        orientation.addListener(listener);
    }

    @Override
    public void Render(Graphics g) {
        orientation.Render(g);
    }

    public void setLength(int newLength) {
        length = newLength;
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        orientation.init();
        int[] currentMouse;
        if (id == MouseEvent.MOUSE_PRESSED) {
            if (!wasClicked(x, y)) return;
            prevMouse = new int[] {x, y};
            currentColor = innerColorDragging;
        }
        else if (id == MouseEvent.MOUSE_DRAGGED) {
            if (prevMouse == null) return;
            currentMouse = new int[] {x, y};
            orientation.dragged(currentMouse[0]-prevMouse[0], currentMouse[1]-prevMouse[1]);
            prevMouse = new int[] {x, y};
        }
        else if (id == MouseEvent.MOUSE_RELEASED) {
            prevMouse = null;
            currentColor = innerColorNormal;
        }
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        if (Double.isNaN(fraction)) return;
        if (fraction > 1.0)
            fraction = 1.0;
        if (fraction < 0.0)
            fraction = 0.0;
        this.fraction = fraction;
        orientation.moved();
    }

    public void ratioChanged(double newRatio) {
        if (newRatio < 1.0)
            newRatio = 1.0;
        innerBarLength = (int) Math.round(length/newRatio);
    }

    private abstract static class Orientation {
        final List<ScrollListener> scrollListeners = new ArrayList<>();
        void addListener(ScrollListener listener) { scrollListeners.add(listener);}
        abstract void Render(Graphics g);
        abstract void init();
        abstract void moved();
        abstract void dragged(int dx, int dy);
    }

    private class Horizontal extends Orientation {
        @Override
        void Render(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawRect(getxPos() + offset, getyPos() - 2, length - 2 * offset, thicknessOuterBar - 1);
            g.setColor(currentColor);
            g.fillRect((int) (getxPos() + 2 * offset + fraction * (length - innerBarLength)), getyPos(), innerBarLength - 4 * offset, thicknessInnerBar);
        }

        @Override
        void init() {
            setHeight(thicknessOuterBar);
            setWidth(length);
        }

        @Override
        void moved() {
            for (ScrollListener listener : scrollListeners) {
                listener.horizontalScrolled();
            }
        }

        @Override
        void dragged(int dx, int dy) {
            setFraction((double) dx / (length-innerBarLength) + fraction);
        }
    }

    private class Vertical extends Orientation {
        @Override
        void Render(Graphics g) {
            g.setColor(Color.BLACK);
            g.drawRect(getxPos()-2*offset, getyPos(), thicknessOuterBar, length-5);
            g.setColor(currentColor);
            g.fillRect(getxPos()-offset, (int) (getyPos() + offset + fraction * (length - innerBarLength)), thicknessInnerBar, innerBarLength - 4 * offset);
        }

        @Override
        void init() {
            setHeight(length);
            setWidth(thicknessOuterBar);
        }

        @Override
        void moved() {
            for (ScrollListener listener : scrollListeners) {
                listener.verticalScrolled();
            }
        }

        @Override
        void dragged(int dx, int dy) {
            setFraction((double) dy/(length - innerBarLength) + fraction);
        }
    }
}
