import javax.print.Doc;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DocumentArea extends Frame {
    public DocumentArea(int x, int y, int width, int height) {
        super(x, y, width, height);
        UITextField textField = new UITextField(x, y, width, textHeight, "teststring");
        UIHyperlink link = new UIHyperlink(x,y, width, textHeight, "www.internet.com");
        content = textField;
    }

    @Override
    public void Render(Graphics g) {
        content.Render(g);
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth - getxPos()) >= 0) setWidth(newWindowWidth - getxPos());
        if ((newWindowHeight - getyPos()) >= 0) setWidth(newWindowHeight - getyPos());
        content.handleResize(newWindowWidth, newWindowHeight);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (!wasClicked(x, y)) return;
        if (id != MouseEvent.MOUSE_CLICKED) return;
        String result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        linkPressed(result);
    }

    private boolean wasClicked(int x, int y) {
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    private void linkPressed(String link) {
        if (link.equals("")) return;
        // What to do when a link is pressed?
        System.out.println("hyperlink pressed!!");
        System.out.println(link);
    }

    private String URL = "";

    private int textHeight = 15;

    private DocumentCell content;
}


