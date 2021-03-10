import UserInterface.AddressBar;
import UserInterface.DocumentArea;
import UserInterface.Frame;
import canvaswindow.CanvasWindow;

import java.awt.*;
import java.util.ArrayList;

public class Browsr extends CanvasWindow {
    /**
     * Initializes a CanvasWindow object.
     *
     * @param title Window title
     */
    protected Browsr(String title) {
        super(title);
        this.Frames.add(this.AddressBar);
        this.Frames.add(this.DocumentArea);

    }

    String text = "Example Text";
    Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 40);
    FontMetrics metrics;
    int textWidth;

    @Override
    protected void handleShown() {
        metrics = getFontMetrics(font);
        textWidth = metrics.stringWidth(text);
        repaint();
    }

    @Override
    protected void paint(Graphics g) {
        for (UserInterface.Frame frame : Frames) {
            frame.Render(g);
        }
    }

    @Override
    protected void handleResize() {
        //ook laten weten aan de frames om zichzelf intern aan te passen!
        for (UserInterface.Frame frame : Frames) {
            frame.handleResize(this.getWidth(), this.getHeight());
        }
        repaint();
    }


    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        for (Frame frame : Frames){
            frame.handleMouse(id, x, y, clickCount, button, modifiersEx);
        }
        repaint();
    }


    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        for (UserInterface.Frame frame : Frames){
            frame.handleKey(id, keyCode, keyChar, modifiersEx);
        }
        repaint();
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new Browsr("Browsr").show();
        });
    }

    private int addressBarHeight = 35;
    private int offset = 5;
    private UserInterface.AddressBar AddressBar = new AddressBar(offset, offset, 100, addressBarHeight, offset);
    private DocumentArea DocumentArea = new DocumentArea(offset,addressBarHeight+2* offset, 100,100);
    private ArrayList<UserInterface.Frame> Frames = new ArrayList<>();
}
