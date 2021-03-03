import java.awt.*;
import java.awt.event.MouseEvent;

public class DocumentArea extends Frame{
    public DocumentArea(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void Render(Graphics g) {
        g.setColor(textColor);
        g.setFont(normalFont);
        g.drawString(text, 50,200);
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth-getxPos()) >= 0) setWidth(newWindowWidth-getxPos());
        if ((newWindowHeight-getyPos()) >= 0) setWidth(newWindowHeight-getyPos());
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (id != MouseEvent.MOUSE_CLICKED) return;
        if (button == MouseEvent.BUTTON1)
            text = "Linker-muisknop";
        if (button == MouseEvent.BUTTON3)
            text = "Rechter-muisknop";
    }


    // Font variables
    Font normalFont = new Font(Font.DIALOG_INPUT, Font.PLAIN, 20);
    FontMetrics metrics;
    int textheight;
    private final Color textColor = Color.BLACK;
    private String text = "";
}


