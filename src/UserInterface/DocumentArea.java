package UserInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/*
-> This class (and the AddressBar) will need an extra field "Controller".
-> This var should be set in the main-Class with documentarea.setController(controller) and the same for AddressBar
-> Initially the documentarea is empty (?). When the lister alerts the documentarea.
It should then call this.controller.getContents(this.controller.getURL()) to fetch the document.
-> If there is a link pressed, the document should compose a new url using this.URL and the retrieved href.
It should then call this.controller.loadDocument(newUrl)
 */

public class DocumentArea extends Frame {
    public DocumentArea(int x, int y, int width, int height) {
        super(x, y, width, height);
        // => This is for debugging purposes:
//        UITextField textField3 = new UITextField(x, y, width, textSize, "Tabel2");
//        UITextField textField4 = new UITextField(x, y, width, textSize, "lorem-ipsum");
//        UIHyperlink link2 = new UIHyperlink(x,y, width, textSize, "/klikhier/hoofdpagina","klik hier");
//        ArrayList<DocumentCell> row3 = new ArrayList<>();
//        ArrayList<DocumentCell> row4 = new ArrayList<>();
//        ArrayList<ArrayList<DocumentCell>> rows2 = new ArrayList<>();
//        row3.add(textField3);
//        row3.add(textField4);
//        row4.add(link2);
//        rows2.add(row3);
//        rows2.add(row4);
//        UITable table = new UITable(x,y,width, height, rows2);
//
//        UITextField textField = new UITextField(x, y, width, textSize, "teststring");
//        UITextField textField2 = new UITextField(x, y, width, textSize, "hallo");
//        UIHyperlink link = new UIHyperlink(x,y, width, textSize, "/spam/win-actie", "Win €1000!");
//        ArrayList<DocumentCell> row = new ArrayList<>();
//        ArrayList<DocumentCell> row2 = new ArrayList<>();
//        row.add(textField2);
//        row.add(link);
//        row2.add(textField);
//        row2.add(table);
//        ArrayList<ArrayList<DocumentCell>> rows = new ArrayList<>();
//        rows.add(row);
//        rows.add(row2);
//
//        content = new UITable(x,y,width, height, rows);
        // ===============================
        UITextField textField5 = new UITextField(x, y, width, textSize, "text");
        content = textField5;


    }

    @Override
    /*
    Renders the content. The content renders its sub-content recursively if existent
     */
    public void Render(Graphics g) {
        content.Render(g);
        g.setColor(Color.green);
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /*
    If the new window dimensions are legal, the UserInterface.DocumentArea gets resized.
    It also resizes its content.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth - getxPos()) >= 0) setWidth(newWindowWidth - getxPos());
        if ((newWindowHeight - getyPos()) >= 0) setHeight(newWindowHeight - getyPos());
        content.handleResize(newWindowWidth, newWindowHeight);
    }

    @Override
    /*
    What to do when mouse is pressed?
    -> Was it on me?
    -> Was it the right button?
    -> Is it the right MouseEvent?
    => If all yes, send the click to content. The result of this could be a href String or the Empty String.
    => Handle the result accordingly
     */
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (!wasClicked(x, y)) return;
        if (button != MouseEvent.BUTTON1) return;
        if (id != MouseEvent.MOUSE_CLICKED) return;
        String result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        linkPressed(result);
    }

    /*
    Returns true if and oly if (x,y) is in this UserInterface.DocumentArea.
     */
    private boolean wasClicked(int x, int y) {
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    /*
    This method looks if the given string is a valid link.
    If so, do the right actions.
     */
    private void linkPressed(String link) {
        if (link.equals("")) return;
        // What to do when a link is pressed?
        System.out.println("hyperlink pressed!!");
        System.out.println(link);
        // controller.loadDocument(this.makeNewUrl(link))
    }

    public void setContent(DocumentCell content) {
        this.content = content;
    }

    public DocumentCell getContent() {
        return this.content;
    }


    // private UIController controller;

    private String Url = "";

    private final int textSize = 14;

    private DocumentCell content;
}


