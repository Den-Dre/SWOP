import java.awt.*;

public class Frame {

    public Frame(int x, int y, int width, int height){
        if (x < 0) x = 0;
        if (y < 0) y = 0;
        if (width < 0) width = 0;
        if (height < 0) height = 0;

        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
        this.hasFocus = false;
    }

    public void Render(Graphics g) { }

    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx){ }

    // moet hier ook niet zijn geimplementeerd. In Adressbar kan er hier bvb op
    // ingegaan worden als de adressbar al dan niet focus heeft
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx){ }

    public void handleResize(int newWindowWidth, int newWindowHeight){ }

    protected void toggleFocus(boolean newState){
        this.hasFocus = newState;
    }

    private int xPos;
    private int yPos;
    protected boolean hasFocus;
    private int width;
    private int height;
    private Color backgroundColor = Color.WHITE;

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int newWidth){
        this.width = newWidth;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int newHeight){
        this.height = newHeight;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}
