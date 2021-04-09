package UserInterface;

import domainmodel.UIController;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.Arrays;

/* TODO
    - Fix width of BookmarksBar
 */

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

    /**
     * Set the {@link UIController} object associated
     * to this {@code Bookmarksbar}.
     *
     * @param controller: The {@link UIController} to be set.
     */
    public void setUIController(UIController controller) {
        this.controller = controller;
    }

    /**
     * Add a bookmark - represented as a {@link UITextHyperlink} -
     * to this {@code BookmarksBar}.
     *
     * @param name: The name of the bookmark that will be added.
     * @param url: The URL of the bookmark that will be added.
     */
    public void addBookmark(String name, String url) {
        int[] coordinates = getNextPosition();
        UITextHyperlink textHyperlink = new UITextHyperlink(coordinates[0], coordinates[1], 0, 10, name);
        textHyperLinks.add(textHyperlink);
        this.controller.addHref(name, url);
    }

    /**
     * Get the x and y coordinates of where
     * a newly * added URL should be placed.
     *
     * @return coords:
     *      an int[2] array: int[0] is the x coordinate, int[1[ the y coordinate
     */
    private int[] getNextPosition() {
        int x = textHyperLinks.stream().mapToInt(UITextHyperlink::getMaxWidth).map(t -> t + xSpacing).sum();
        return new int[]{x, this.yCoordinate};
    }

    /**
     * Render this {@code BookmarksBar}.
     * @param g: The graphics to be rendered.
     */
    @Override
    public void Render(Graphics g) {
//        Graphics2D g2 = (Graphics2D) g;
//        Shape upperLine = new Line2D.Double(0, this.getyPos(), this.getWidth(), this.getyPos());
//        Shape lowerLine = new Line2D.Double(0, this.getyPos() + this.getHeight(), this.getWidth(), this.getyPos() + this.getHeight());
//        g2.draw(upperLine);
//        g2.draw(lowerLine);
        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
        this.textHyperLinks.forEach(t -> t.Render(g));
    }

    /**
     * Handle a mouseclick on this {@code BookmarksBar}.
     *
     * @param id: The type of mouse activity.
     * @param x: The x coordinate of the mouse activity.
     * @param y: The y coordinate of the mouse activity.
     * @param clickCount: The number of clicks.
     * @param button: The mouse button that was clicked.
     * @param modifiersEx: The control keys that were held on the click.
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        String result;
        for (UITextHyperlink textHyperlink : textHyperLinks) {
            result = textHyperlink.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
            if (!(result.equals(""))) {
                loadTextHyperlink(result);
                break;
            }
        }
    }

    /**
     * Load a {@link UITextHyperlink} located in this
     * {@code BookmarksBar} by retrieving its associated
     * {@code href}-value out of the domain layer.
     *
     * @param linkName: the name of the link that needs to be loaded.
     */
    private void loadTextHyperlink(String linkName) {
        // TODO: This seems really redundant. Maybe we should change our approach of storing bookmarks.
        String href = controller.getHrefFromBookmark(linkName);
        controller.loadDocumentFromHref(href);
    }

    /**
     * The {@code UIController} associated to this {@code BookmarksBar}.
     */
    private UIController controller;

    /**
     * A list of all the bookmarks that are
     * currently present in this {@code BookmarksBar}.
     */
    private ArrayList<UITextHyperlink> textHyperLinks = new ArrayList<>();

    /**
     * The y coordinate of this {@code BookmarksBar}
     * with the top left corner as (0, 0).
     */
    private final int yCoordinate;

    /**
     * How much spacing there should
     * be left in between the rendered
     * bookmarks.
     */
    private final int xSpacing = 3;
}

