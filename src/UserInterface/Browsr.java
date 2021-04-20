package UserInterface;

import canvaswindow.CanvasWindow;
import domainmodel.UIController;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * A class to represent the UserInterface.Browsr window,
 * with an {@link AddressBar} and a {@link DocumentArea}.
 */
public class Browsr extends CanvasWindow {
    /**
     * Initializes this {@code Browsr} as CanvasWindow object:
     * the initial layout consists of: an {@link AddressBar},
     * a {@link BookmarksBar} and a {@link DocumentArea}
     *
     * @param title:
     *            The Window title
     */
    protected Browsr(String title) {
        super(title);
        this.layout = new RegularLayout();
        setUpRegularBrowsrLayout();
    }

    /**
     * This method is called in the constructor
     * of a {@code Browsr} object * to create a:
     *
     * <ul>
     *     <li>an {@link AddressBar} object where URL's are entered,</li>
     *     <li>a {@link BookmarksBar} object to display the bookmarks added by the user, and </li>
     *     <li>a {@link DocumentArea} object to display the content of the requested sites.</li>
     *     <li>a {@link UIController} which is linked to the above three objects. </li>
     * </ul>
     */
    private void setUpRegularBrowsrLayout() {
        try {
            // An integer variable to denote the height of the AddressBar
            // that is linked to this UserInterface.Browsr.
            int addressBarHeight = 20;

            //  An integer variable to denote the offset of the AddressBar
            //  that is linked to this UserInterface.Browsr.
            int addressBarOffset = 5;

            //  An integer variable to denote the offset of the BookmarksBar
            //  that is linked to this UserInterface.Browsr.
            int bookmarksBarOffset = 5;

            // An integer variable to denote the height of the BookmarksBar
            // that is linked to this UserInterface.Browsr.
            int bookmarksBarHeight = 20;

            AddressBar   = new AddressBar(addressBarOffset, addressBarOffset, 100, addressBarHeight, addressBarOffset);
            bookmarksBar = new BookmarksBar(bookmarksBarOffset, addressBarHeight + 2 * bookmarksBarOffset, 100, bookmarksBarHeight, bookmarksBarOffset);
            DocumentArea = new DocumentArea(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);
            controller = new UIController();
            //DocumentArea =  new Frame(0,addressBarHeight, 100,100);

            this.Frames.add(this.AddressBar);
            this.Frames.add(this.DocumentArea);
            this.Frames.add(this.bookmarksBar);

            AddressBar.setUiController(controller);
            DocumentArea.setController(controller);
            bookmarksBar.setUIController(controller);

            controller.addUrlListener(AddressBar);
            controller.addDocumentListener(DocumentArea);

            // For testing purposes
//            AddressBar.changeTextTo("https://people.cs.kuleuven.be/bart.jacobs/browsrtest.html");
        }
        catch(IllegalDimensionException e){
            System.out.print("Dimension error in frame while making browser!");
        }
    }

    /**
     * A method to describe the behaviour
     * when the method show is called.
     */
    @Override
    protected void handleShown() {
        repaint();
    }

    /**
     * An abstract class which represents
     * the possible layouts of a {@code Browsr} object.
     *
     * Classes that extend this method, need to implement
     * a {@code Render}, {@code handleMouseEvent} and {@code handleKeyEvent} method,
     * to handle rendering, clicking and keyboard input of this {@code Browsr} object respectively.
     */
    protected static abstract class Layout {
        abstract void Render(Graphics g);
        abstract void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx);
        abstract void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx);
    }

    /**
     * A {@code Browsr Layout} that represents the
     * regular layout containing a an {@link AddressBar},
     * a {@link BookmarksBar} and a {@link DocumentArea}.
     */
    protected class RegularLayout extends Layout {

        /**
         * Render this {@code RegularLayout} of
         * this {@code Browsr} object.
         *
         * @param g:
         *      The graphics used to render this {@code RegularLayout}.
         */
        @Override
        void Render(Graphics g) {
            Frames.forEach(f -> f.Render(g));
        }

        /**
         * Handle a mouse click on this {@code RegularLayout}.
         *
         * @param id: The type of mouse activity.
         * @param x: The x coordinate of the mouse activity.
         * @param y: The y coordinate of the mouse activity.
         * @param clickCount: The number of clicks.
         * @param button: The mouse button that was clicked.
         * @param modifiersEx: The control keys that were held on the click.
         */
        @Override
        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
            Frames.forEach(f -> f.handleMouse(id, x, y, clickCount, button, modifiersEx));
        }

        /**
         * Handle keyboard input on this {@code RegularLayout}.
         *
         * Handle key presses on this {@code BrowsrLayout}.
         * This method does the right action when a key is pressed.
         *
         * @param id: The KeyEvent (Associated with type of KeyEvent).
         * @param keyCode: The KeyEvent code (Determines the involved key).
         * @param keyChar: The character representation of the involved key.
         * @param modifiersEx: Specifies other keys that were involved in the event.
         */
        @Override
        void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
            Frames.forEach(f -> f.handleKey(id, keyCode, keyChar, modifiersEx));
        }
    }

    protected class BookmarksDialogLayout extends Layout {

        /**
         * Render this {@code BookmarksDialogLayout} of
         * this {@code Browsr} object.
         *
         * @param g:
         *      The graphics used to render this {@code BookmarksDialogLayout}.
         */
        @Override
        void Render(Graphics g) {
            bookmarksDialog.Render(g);
        }

        /**
         * Handle a mouse click on this {@code BookmarksDialogLayout}.
         *
         * @param id: The type of mouse activity.
         * @param x: The x coordinate of the mouse activity.
         * @param y: The y coordinate of the mouse activity.
         * @param clickCount: The number of clicks.
         * @param button: The mouse button that was clicked.
         * @param modifiersEx: The control keys that were held on the click.
         */
        @Override
        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
            bookmarksDialog.handleMouse(id, x, y, clickCount, button, modifiersEx);
        }

        /**
         * Handle keyboard input on this {@code BrowsrLayout}.
         *
         * Handle key presses on this {@code BookmarksDialogLayout}.
         * This method does the right action when a key is pressed.
         *
         * @param id: The KeyEvent (Associated with type of KeyEvent).
         * @param keyCode: The KeyEvent code (Determines the involved key).
         * @param keyChar: The character representation of the involved key.
         * @param modifiersEx: Specifies other keys that were involved in the event.
         */
        @Override
        void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
            bookmarksDialog.handleKey(id, keyCode, keyChar, modifiersEx);
        }
    }

    protected class SaveDialogLayout extends Layout {
        // TODO

        /**
         * Render this {@code SaveDialogLayout} of
         * this {@code Browsr} object.
         *
         * @param g:
         *      The graphics used to render this {@code SaveDialogLayout}.
         */
        @Override
        void Render(Graphics g) {
            saveDialog.Render(g);
        }

        /**
         * Handle a mouse click on this {@code SaveDialogLayout}.
         *
         * @param id: The type of mouse activity.
         * @param x: The x coordinate of the mouse activity.
         * @param y: The y coordinate of the mouse activity.
         * @param clickCount: The number of clicks.
         * @param button: The mouse button that was clicked.
         * @param modifiersEx: The control keys that were held on the click.
         */
        @Override
        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
            saveDialog.handleMouse(id, x, y, clickCount, button, modifiersEx);
        }

        /**
         * Handle keyboard input on this {@code BrowsrLayout}.
         *
         * Handle key presses on this {@code SaveDialogLayout}.
         * This method does the right action when a key is pressed.
         *
         * @param id: The KeyEvent (Associated with type of KeyEvent).
         * @param keyCode: The KeyEvent code (Determines the involved key).
         * @param keyChar: The character representation of the involved key.
         * @param modifiersEx: Specifies other keys that were involved in the event.
         */
        @Override
        void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
            saveDialog.handleKey(id, keyCode, keyChar, modifiersEx);
        }
    }

    /**
     * (Re)Render the elements of this Broswr window.
     *
     * @param g:
     *       The graphics that will be rendered.
     */
    @Override
    protected void paint(Graphics g) {
        layout.Render(g);
    }

    /**
     * Handle the resizing of this UserInterface.Browsr window
     * and its sub windows.
     *
     * <p>
     * N.B.: without this method, {@code BookmakrBar} would be rendered with
     *       the given absolute width, and thus one would need to guess the
     *       correct initial size of the window. Using this method, widths are
     *       automatically adjusted: both at initialization and at runtime.
     *</p>
     */
    @Override
    protected void handleResize() {
        //ook laten weten aan de frames om zichzelf intern aan te passen!
        for (UserInterface.Frame frame : Frames) {
            frame.handleResize(this.getWidth(), this.getHeight());
        }
        repaint();
    }

    /**
     * Handle mouseEvents. Determine which part of this
     * BrowsrDocument was pressed and do the right actions.
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    @Override
    protected void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
       layout.handleMouseEvent(id, x, y, clickCount, button, modifiersEx);
        repaint();
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id: The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode: The KeyEvent code (Determines the involved key)
     * @param keyChar: The character representation of the involved key
     * @param modifiersEx: Specifies other keys that were involved in the event
     */
    @Override
    protected void handleKeyEvent(int id, int keyCode, char keyChar, int modifiersEx) {
        if (modifiersEx == KeyEvent.CTRL_DOWN_MASK) {  // KeyEven.CTRL_DOWN_MASK == 128 == CTRL
            if (keyCode == 68 && layout instanceof RegularLayout) // 68 == d
                handleBookmarksDialog();
            else if (keyCode == 83 && layout instanceof RegularLayout) // 83 == s
                handleSaveDialog();
            else if (keyCode == 86)
                handlePaste();
        }
        this.layout.handleKeyEvent(id, keyCode, keyChar, modifiersEx);
        repaint();
    }

    /**
     * A method that takes the required actions
     * when the key combination CTRL + D is
     * pressed by the user.
     */
    private void handleBookmarksDialog() {
        this.layout = new BookmarksDialogLayout();
        String currentUrl = this.getAddressBar().getURL();
        this.bookmarksDialog = new BookmarksDialog(this.getWidth(), this.getHeight(), currentUrl, bookmarksBar, this);
    }

    /**
     * A method that takes the required actions
     * when the key combination CTRL + S is
     * pressed by the user.
     */
    private void handleSaveDialog() {
        this.layout = new SaveDialogLayout();
        String currentUrl = this.getAddressBar().getURL();
        this.saveDialog = new SaveDialog(this.getWidth(), this.getHeight(), currentUrl, this);
    }

    /**
     * For testing purposes: paste contents
     * in the {@link AddressBar} using CTRL+v.
     */
    private void handlePaste() {
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        Transferable t = c.getContents(this);
        if (t == null)
            return;
        try {
            this.AddressBar.changeTextTo((String) t.getTransferData(DataFlavor.stringFlavor));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Change the {@link BrowsrLayout} of this {@code Browsr}
     * and re-render this {@code Browsr} object.
     *
     * @param layout: the new {@link BrowsrLayout} to be set.
     */
    public void setBrowsrLayout(Layout layout) {
        this.layout = layout;
    }

    /**
     * Retrieve the current {@link Layout} of this
     * {@code Browsr} object.
     *
     * @return layout: The current {@link Layout} of this {@code Browsr} object.
     */
    public Layout getBrowsrLayout() {
        return this.layout;
    }

    /**
     * Retrieve the current {@link BookmarksDialog} of this
     * {@code Browsr} object if it exists, otherwise {@code null} is returned.
     *
     * @return dialog: The current {@link BookmarksDialog} of this {@code Browsr} object
     *      if it exists, otherwise {@code null} is returned.
     */
    public BookmarksDialog getBookmarksDialog() {
        return this.bookmarksDialog;
    }

    /**
     * Retrieve the current {@link SaveDialog} of this
     * {@code Browsr} object if it exists, otherwise {@code null} is returned.
     *
     * @return dialog: The current {@link SaveDialog} of this {@code Browsr} object
     *      if it exists, otherwise {@code null} is returned.
     */
    public SaveDialog getSaveDialog() {
        return this.saveDialog;
    }

    /**
     * Main method. This creates a UserInterface.Browsr window.
     *
     * @param args:
     *            provided command line arguments.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Browsr("UserInterface.Browsr").show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * A variable that denotes the {@link AddressBar}
     * associated to this UserInterface.Browsr.
     */
    private UserInterface.AddressBar AddressBar;

     /**
     * A variable that denotes the {@link DocumentArea}
     * associated to this UserInterface.Browsr.
     */
    private DocumentArea DocumentArea;

    /**
     * A variable that denotes the {@link UIController}
     * associated to this UserInterface.Browsr.
     */
    private UIController controller;

    /**
     * An {@link ArrayList} to hold all the
     * {@link Frame}'s associated to this UserInterface.Browsr.
     */
    private final ArrayList<UserInterface.Frame> Frames = new ArrayList<>();
    
    /**
     * @return the {@link AddressBar} of this {@link Browsr}, for testing/debug purposes
     */
	public AddressBar getAddressBar() {
		return this.AddressBar;
	}
	
    /**
     * @return the {@link DocumentArea} of this {@link Browsr}, for testing/debug purposes
     */
	public DocumentArea getDocumentArea() {
		return this.DocumentArea;
	}

	public UIController getController() {
	    return this.controller;
    }

    /**
     * A variable that denotes the current
     * {@link Layout} of this {@code UserInterface.Browsr}.
     */
    private Layout layout;

    /**
     * A variable that denotes the {@link BookmarksDialogLayout}
     * associated to this UserInterface.Browsr.
     */
    private BookmarksDialog bookmarksDialog;

    /**
     * A variable that denotes the {@link BookmarksDialogLayout}
     * associated to this UserInterface.Browsr.
     */
    private SaveDialog saveDialog;

    /**
     * A variable that denotes the {@link BookmarksBar}
     * associated to this UserInterface.Browsr.
     */
    private BookmarksBar bookmarksBar;
}
