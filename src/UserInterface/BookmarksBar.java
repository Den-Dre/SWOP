package UserInterface;

import domainmodel.UIController;

import java.awt.*;
import java.util.ArrayList;

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
    public BookmarksBar(int x, int y, int width, int height, int offset) throws IllegalDimensionException {
        super(x, y, width, height);
        this.yCoordinate = y;
        this.height = height;
        this.offset = offset;
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
        handleResize(getWidth(), height);
    }

    /**
     * Calculate the x and y coordinates of where
     * a newly added URL should be placed.
     *
     * @return coords:
     *      an int[2] array: int[0] is the x-coordinate, int[1] the y-coordinate.
     */
    private int[] getNextPosition() {
        int x = getxPos() + offset + textHyperLinks.stream().mapToInt(UITextHyperlink::getMaxWidth).map(t -> t + bookmarkSeperationDistance).sum();
        int y = yCoordinate + height / 4;
        return new int[]{x, y};
    }

    /**
     * Render this {@code BookmarksBar}.
     * @param g: The graphics to be rendered.
     */
    @Override
    public void Render(Graphics g) {
        g.setColor(Color.GRAY);
        g.drawLine(getxPos() + offset, getyPos() + height, getWidth() - offset, getyPos() + height);
//        g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
        this.textHyperLinks.forEach(t -> t.Render(g));
    }

    /**
     * Handle a mouseclick on this {@code BookmarksBar}.
     *
     * @param id: the type of mouse activity.
     * @param x: the x coordinate of the mouse activity.
     * @param y: the y coordinate of the mouse activity.
     * @param clickCount: the number of clicks.
     * @param button: the mouse button that was clicked.
     * @param modifiersEx: the control keys that were held on the click.
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        String result;
        for (UITextHyperlink textHyperlink : textHyperLinks) {
            result = textHyperlink.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
            if (!(result.equals(""))) {
                loadTextHyperlink(result);
                return;
            }
        }
    }

    /**
     * This method handles resizes.
     * It makes sure the addressBar is adjusted in width when the window shrinks or grows.
     * It does not change its height (e.g. look at Firefox).
     *
     * <p>N.B.: without this method, {@code BookmakrBar} would be rendered with
     *          the given absolute width, and thus one would need to guess the
     *          correct initial size of the window. Using this mehtod, widths are
     *          automatically adjusted: both at initialisation and at runtime.</p>
     *
     * @param newWindowHeight: parameter containing the new window-height
     * @param newWindowWidth: parameter containing the new window-width
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.setWidth(newWindowWidth - 2*offset);
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
        String url = controller.getURLFromBookmark(linkName);
        controller.loadDocument(url);
    }

    /**
     * The {@code UIController} associated to this {@code BookmarksBar}.
     */
    private UIController controller;

    /**
     * A list of all the bookmarks that are
     * currently present in this {@code BookmarksBar}.
     */
    private final ArrayList<UITextHyperlink> textHyperLinks = new ArrayList<>();

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
    private final int bookmarkSeperationDistance = 5;

    /**
     * An integer variable representing
     * the offset of this {@code BookmarksBar}
     * measured from the left-hand side of the
     * current window {@link Browsr} window.
     */
    private final int offset;

    /**
     * An integer variable representing
     * the height of this {@code BookmarksBar}.
     */
    private final int height;
}

