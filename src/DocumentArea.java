import UserInterface.DocumentCell;
import UserInterface.UIHyperlink;
import UserInterface.UITable;
import UserInterface.UITextField;
import UserInterface.Frame;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class DocumentArea extends Frame {
    public DocumentArea(int x, int y, int width, int height) throws Exception {
        super(x, y, width, height);

        // => This is for debugging purposes:
        int textSize = 20;
        UITextField textField3 = new UITextField(x, y, width, textSize, "Tabel2");
        UITextField textField4 = new UITextField(x, y, width, textSize, "lorem-ipsum");
        UIHyperlink link2 = new UIHyperlink(x,y, width, textSize, "www.bart.com", "Bart");
        ArrayList<DocumentCell> row3 = new ArrayList<>();
        ArrayList<DocumentCell> row4 = new ArrayList<>();
        ArrayList<ArrayList<DocumentCell>> rows2 = new ArrayList<>();
        row3.add(textField3);
        row3.add(textField4);
        row4.add(link2);
        rows2.add(row3);
        rows2.add(row4);
        UITable table = new UITable(x,y,width, height, rows2);



        UITextField textField = new UITextField(x, y, width, textSize, "teststring");
        UITextField textField2 = new UITextField(x, y, width, textSize, "hallo");
        UIHyperlink link = new UIHyperlink(x,y, width, textSize, "www.internet.com", "Internet");
        ArrayList<DocumentCell> row = new ArrayList<>();
        ArrayList<DocumentCell> row2 = new ArrayList<>();
        row.add(textField2);
        row.add(link);
        row2.add(textField);
        row2.add(table);
        ArrayList<ArrayList<DocumentCell>> rows = new ArrayList<>();
        rows.add(row);
        rows.add(row2);

        content = new UITable(x,y,width, height, rows);
        // ===============================
    }

    /**
     * Renders the content. The content renders its sub-content recursively if existent
     */
    @Override
    public void Render(Graphics g) {
        content.Render(g);
    }

    /**
     * If the new window dimensions are legal, the DocumentArea gets resized.
     * It also resizes its content.
     *
     * @param newWindowWidth: The new window width that needs to be set.
     * @param newWindowHeight: The new window height that needs to be set.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth - getxPos()) >= 0) setWidth(newWindowWidth - getxPos());
        if ((newWindowHeight - getyPos()) >= 0) setWidth(newWindowHeight - getyPos());
        content.handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * What to do when mouse is pressed?
     * -> Was it on me?
     * -> Was it the right button?
     * -> Is it the right MouseEvent?
     * => If all yes, send the click to content. The result of this could be a href String or the Empty String.
     * => Handle the result accordingly
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (!wasClicked(x, y)) return;
        if (button != MouseEvent.BUTTON1) return;
        if (id != MouseEvent.MOUSE_CLICKED) return;
        String result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        linkPressed(result);
    }

    /**
     * Returns true if and oly if (x,y) is in this DocumentArea.
     *
     * @param x: The x coordinate of the mouse activity.
     * @param y  The y coordinate of the mouse activity.
     * @return bool: True iff the click occurred inside this DocumentArea.
     */
    private boolean wasClicked(int x, int y) {
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    /**
     * This method looks if the given string is a valid link.
     * If so, do the right actions.
     *
     * @param link: String representation of the link that was pressed.
     */
    private void linkPressed(String link) {
        if (link.equals("")) return;
        // TODO: What to do when a link is pressed?
        System.out.println("hyperlink pressed!!");
        System.out.println(link);
    }


    private final String URL = "";
    private DocumentCell content;
}
