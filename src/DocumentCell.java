import java.awt.*;

public class DocumentCell extends Frame{
    public DocumentCell(int x, int y, int width, int height) {
        super(x, y, width, height);
    }

    @Override
    public void Render(Graphics g) {
        super.Render(g);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        super.handleMouse(id, x, y, clickCount, button, modifiersEx);
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        super.handleResize(newWindowWidth, newWindowHeight);
    }

    public boolean wasClicked(int x, int y) {
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        return "";
    }

    public int getMaxHeight() {
        return 1;
    }

    public int getMaxWidth() {
        return 1;
    }


}
