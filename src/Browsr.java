import UserInterface.AddressBar;
import UserInterface.DocumentArea;
import UserInterface.Frame;
import canvaswindow.CanvasWindow;
import domainmodel.Document;
import domainmodel.UIController;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class to represent the Browsr window,
 * with an {@link AddressBar} and a {@link DocumentArea}.
 */
public class Browsr extends CanvasWindow {
    /**
     * Initializes a CanvasWindow object.
     *
     * @param title:
     *            The Window title
     */
    protected Browsr(String title) throws Exception {
        super(title);
        
        AddressBar = new AddressBar(addressBarOffset,addressBarOffset, 100, addressBarHeight, addressBarOffset);
        DocumentArea = new DocumentArea(addressBarOffset,addressBarHeight+2* addressBarOffset, 100,100);
//		DocumentArea =  new Frame(0,addressBarHeight, 100,100);

        this.Frames.add(this.AddressBar);
        this.Frames.add(this.DocumentArea);

        UIController controller = new UIController();
        AddressBar.setUiController(controller);
        DocumentArea.setController(controller);

        controller.addUrlListener(AddressBar);
        controller.addDocumentListener(DocumentArea);
    }

    String text = "Example Text";
    Font font = new Font(Font.DIALOG_INPUT, Font.BOLD, 40);
    FontMetrics metrics;
    int textWidth;

    /**
     * A method to describe the behaviour
     * when the method show is called.
     */
    @Override
    protected void handleShown() {
        metrics = getFontMetrics(font);
        textWidth = metrics.stringWidth(text);
        repaint();
    }

    /**
     * (Re)Render the elements of this Broswr window.
     *
     * @param g:
     *       The graphics that will be rendered.
     */
    @Override
    protected void paint(Graphics g) {
        for (UserInterface.Frame frame : Frames) {
            frame.Render(g);
        }
    }

    /**
     * Handle the resizing of this Browsr window
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
        for (Frame frame : Frames){
            frame.handleMouse(id, x, y, clickCount, button, modifiersEx);
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
        for (UserInterface.Frame frame : Frames){
            frame.handleKey(id, keyCode, keyChar, modifiersEx);
        }
        repaint();
    }

    /**
     * Main method. This creates a Browsr window.
     *
     * @param args:
     *            provided command line arguments.
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            try {
                new Browsr("Browsr").show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * An integer variable to denote the height of the AddressBar
     * that is linked to this Browsr.
     */
    private int addressBarHeight = 15;

    /**
     * An integer varible to denote the offset of the AddressBar
     * that is linked to this Browsr.
     */
    private int addressBarOffset = 5;

    /**
     * A variable that denotes the {@link AddressBar}
     * associated to this Browsr.
     */
//    private UserInterface.AddressBar AddressBar = new AddressBar(addressBarOffset, addressBarOffset, 100, addressBarHeight, addressBarOffset);
    private UserInterface.AddressBar AddressBar;

     /**
     * A variable that denotes the {@link DocumentArea}
     * associated to this Browsr.
     */
//    private DocumentArea DocumentArea = new DocumentArea(addressBarOffset,addressBarHeight+2* addressBarOffset, 100,100);
    private DocumentArea DocumentArea;

    /**
     * An {@link ArrayList} to hold all the
     * {@link Frame}'s associated to this Browsr.
     */
    private ArrayList<UserInterface.Frame> Frames = new ArrayList<>();
}
