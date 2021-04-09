package UserInterface;

import domainmodel.UIController;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

public class BookmarksBar extends Frame {
    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public BookmarksBar(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
        this.yCoordinate = y;
    }

    public void setUIController(UIController controller) {
        this.controller = controller;
    }

    public void loadBookmark(String bookmarkName) {
        String href = controller.getHrefFromBookmark(bookmarkName);

    }

    public void addBookmark(String name, String url) {
        int[] coordinates = getNextPosition();
        UITextHyperlink textHyperlink = new UITextHyperlink(coordinates[0], coordinates[1], 0, 10, name);
        textHyperLinks.add(textHyperlink);
        this.controller.addHref(name, url);
    }

    private int[] getNextPosition() {
        int x = textHyperLinks.stream().mapToInt(UITextHyperlink::getMaxWidth).map(t -> t + xSpacing).sum();
        return new int[]{x, this.yCoordinate};
    }

    @Override
    public void Render(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        Shape upperLine = new Line2D.Double(0, this.getyPos(), this.getWidth(), this.getyPos());
//        Shape lowerLine = new Line2D.Double(0, this.getyPos() + this.getHeight(), this.getWidth(), this.getyPos() + this.getHeight());
//        g2.draw(upperLine);
//        g2.draw(lowerLine);
//        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
        this.textHyperLinks.forEach(t -> t.Render(g));
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        String result = "";
        for (UITextHyperlink textHyperlink : textHyperLinks) {
            result = textHyperlink.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
            if (!(result.equals(""))) {
                controller.loadDocumentFromHref(result);
                break;
            }
        }
    }

    /**
     * The {@code UIController} associated to this {@code BookmarksBar}.
     */
    private UIController controller;

    private ArrayList<UITextHyperlink> textHyperLinks = new ArrayList<>();

    private final int yCoordinate;

    private final int xSpacing = 3;
}

