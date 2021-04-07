package UserInterface;

import java.awt.*;

public class GenericDialogScreen extends Frame {
    private final UIForm content;

    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public GenericDialogScreen(int x, int y, int width, int height, UIForm form) throws IllegalDimensionException {
        super(x, y, width, height);
        this.content = form;
    }

    public void Render(Graphics g) {
        this.content.render();
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        this.content.handleMouse(id, x, y, clickCount, button, modifiersEx);
    }

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.content.handleKey(id, keyChar, keyChar, modifiersEx);
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth - getxPos()) >= 0) setWidth(newWindowWidth - getxPos());
        if ((newWindowHeight - getyPos()) >= 0) setHeight(newWindowHeight - getyPos());
        if (this.content != null)
            content.handleResize(newWindowWidth, newWindowHeight);
    }
}
