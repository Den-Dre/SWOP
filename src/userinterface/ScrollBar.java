package userinterface;

import domainlayer.DocumentListener;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScrollBar extends Frame {
    private List<ScrollListener> scrollListeners = new ArrayList<>();
    private final static int thicknessOuterBar = 8;
    private final static int thicknessInnerBar = 4;
    private double fraction = 0.0;
    private int innerBarLength;
    private int length;
    private final boolean isHorizontal;
    private final int offset = 2;

    public ScrollBar(int x, int y, int length, boolean isHorizontal, double ratio, ScrollListener listener) {
        super(x, y, 0, 0);
        int width = thicknessOuterBar;
        int height = length;
        if (isHorizontal) {
            width = length;
            height = thicknessOuterBar;
        }
        setHeight(height);
        setWidth(width);
        innerBarLength = (int) Math.round(length/ratio);
        this.length = length;
        scrollListeners.add(listener);
        this.isHorizontal = isHorizontal;
    }

    @Override
    public void Render(Graphics g) {
        g.drawRect(getxPos()+offset, getyPos()-2, getWidth()-2*offset, getHeight()-1);
        g.setColor(Color.GRAY);
        g.fillRect((int) (getxPos()+2*offset+fraction*(length-innerBarLength)), getyPos(), innerBarLength-4*offset, thicknessInnerBar);
    }

    @Override
    public void setWidth(int newWidth) {
        super.setWidth(newWidth);
        if (isHorizontal)
            length = newWidth;
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (id != MouseEvent.MOUSE_CLICKED) return;
        if (!wasClicked(x, y)) return;
        if (button == MouseEvent.BUTTON1) {
            fraction += 0.1;
            if (fraction > 1.0)
                fraction = 1.0;
            moved();
        }
        else if (button == MouseEvent.BUTTON3) {
            fraction -= 0.1;
            if (fraction < 0.0)
                fraction = 0.0;
            moved();
        }
    }

    private void moved() {
        for (ScrollListener listener : scrollListeners) {
            listener.scrolled();
        }
    }

    public double getFraction() {
        return fraction;
    }

    public void setFraction(double fraction) {
        if (Double.isNaN(fraction)) return;
        this.fraction = fraction;
        moved();
    }

    public void ratioChanged(double newRatio) {
        if (newRatio < 1.0)
            newRatio = 1.0;
        innerBarLength = (int) Math.round(length/newRatio);
    }
}
