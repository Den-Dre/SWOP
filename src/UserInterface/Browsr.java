package UserInterface;

import canvaswindow.CanvasWindow;
import domainmodel.UIController;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

/**
 * A class to represent the UserInterface.Browsr window,
 * with an {@link AddressBar} and a {@link DocumentArea}.
 */
public class Browsr extends CanvasWindow {
    /**
     * Initializes a CanvasWindow object.
     *
     * @param title:
     *            The Window title
     */
    protected Browsr(String title) {
        super(title);
        this.layout = BrowsrLayout.REGULAR;
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
            bookmarksBar = new BookmarksBar(bookmarksBarOffset, addressBarHeight + 2 * bookmarksBarOffset, 100, bookmarksBarHeight);
            DocumentArea = new DocumentArea(addressBarOffset, 2 * (addressBarHeight + 2 * addressBarOffset), 100, 100);
            //DocumentArea =  new Frame(0,addressBarHeight, 100,100);

            this.Frames.add(this.AddressBar);
            this.Frames.add(this.DocumentArea);
            this.Frames.add(this.bookmarksBar);

            UIController controller = new UIController();
            AddressBar.setUiController(controller);
            DocumentArea.setController(controller);
            bookmarksBar.setUIController(controller);

            controller.addUrlListener(AddressBar);
            controller.addDocumentListener(DocumentArea);
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

//    private static abstract class Layout {
//        abstract void Render(Graphics g);
//        abstract void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx);
//    }
//
//    private class Regular extends Layout {
//
//        @Override
//        void Render(Graphics g) {
//            Frames.forEach(f -> f.Render(g));
//        }
//
//        @Override
//        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
//            Frames.forEach(f -> f.handleMouse(id, x, y, clickCount, button, modifiersEx));
//        }
//    }
//
//    private class BookmarksDialog extends Layout {
//
//        @Override
//        void Render(Graphics g) {
//            bookmarksDialog.Render(g);
//        }
//
//        @Override
//        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
//            bookmarksDialog.handleMouseEvent(id, x, y, clickCount, button, modifiersEx);
//        }
//    }
//
//    private class SaveDialog extends Layout {
//
//        @Override
//        void Render(Graphics g) {
//            //
//        }
//
//        @Override
//        void handleMouseEvent(int id, int x, int y, int clickCount, int button, int modifiersEx) {
//            //
//        }
//    }

    /**
     * (Re)Render the elements of this Broswr window.
     *
     * @param g:
     *       The graphics that will be rendered.
     */
    @Override
    protected void paint(Graphics g) {
        switch (this.layout) {
            case REGULAR -> {
                for (UserInterface.Frame frame : Frames)
                    frame.Render(g);
            }

            case BOOKMARKS_DIALOG ->
                    this.bookmarksDialog.Render(g);

            // TODO
            case SAVE_DIALOG ->
                    System.out.println("Not implemented.");
        }
    }

    /**
     * Handle the resizing of this UserInterface.Browsr window
     * and its sub windows.
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
        switch (this.layout) {
            case REGULAR ->
                    Frames.forEach(f -> f.handleMouse(id, x, y, clickCount, button, modifiersEx));
            case BOOKMARKS_DIALOG ->
                    this.bookmarksDialog.handleMouse(id, x, y, clickCount, button, modifiersEx);
            case SAVE_DIALOG ->
                    System.out.println("Click functionality for Save Dialog not yet implemented.");
        }
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
            if (keyCode == 68) // 68 == d
                handleBookmarksDialog();
            else if (keyCode == 83) // 83 == s
                System.out.println("Save dialog not implemented yet");
        }

        switch (layout) {
            case REGULAR ->
                    Frames.forEach(f -> f.handleKey(id, keyCode, keyChar, modifiersEx));
            case BOOKMARKS_DIALOG ->
                    bookmarksDialog.handleKey(id, keyCode, keyChar, modifiersEx);
            case SAVE_DIALOG ->
                    // TODO
                    System.out.println("Save dialog not implemented yet.");
        }
        repaint();
    }

    /**
     * A method that takes the required actions
     * when the key combination CTRL + D is
     * pressed by the user.
     */
    private void handleBookmarksDialog() {
        this.layout = BrowsrLayout.BOOKMARKS_DIALOG;
        String currentUrl = this.getAddressBar().getURL();
        this.bookmarksDialog = new BookmarksDialog(this.getWidth(), this.getHeight(), currentUrl, bookmarksBar, this);
    }

    /**
     * Change the {@link BrowsrLayout} of this {@code Browsr}
     * and re-render this {@code Browsr} object.
     *
     * @param layout: the new {@link BrowsrLayout} to be set.
     */
    public void setBrowsrLayout(BrowsrLayout layout) {
        this.layout = layout;
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
//    private UserInterface.AddressBar AddressBar = new AddressBar(addressBarOffset, addressBarOffset, 100, addressBarHeight, addressBarOffset);
    private UserInterface.AddressBar AddressBar;

     /**
     * A variable that denotes the {@link DocumentArea}
     * associated to this UserInterface.Browsr.
     */
    private DocumentArea DocumentArea;

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

    /**
     * A variable that denotes the current
     * {@link BrowsrLayout} of this {@code UserInterface.Browsr}.
     */
    private BrowsrLayout layout;

    /**
     * A variable that denotes the {@link BookmarksDialog}
     * associated to this UserInterface.Browsr.
     */
    private BookmarksDialog bookmarksDialog = null;

    /**
     * A variable that denotes the {@link BookmarksBar}
     * associated to this UserInterface.Browsr.
     */
    private BookmarksBar bookmarksBar;
}
