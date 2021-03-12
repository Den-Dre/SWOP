package UserInterface;

import domainmodel.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO: remove these comments when things are implemented as described by it.
 * -> This class (and the AddressBar) will need an extra field "Controller".
 * -> This var should be set in the main-Class with documentarea.setController(controller) and the same for AddressBar
 * -> Initially the documentarea is empty (?). When the lister alerts the documentarea.
 * It should then call this.controller.getContents(this.controller.getURL()) to fetch the document.
 * -> If there is a link pressed, the document should compose a new url using this.URL and the retrieved href.
 * It should then call this.controller.loadDocument(newUrl)
 */
public class DocumentArea extends Frame implements DocumentListener {
    public DocumentArea(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);

        // hard-coded Welcome document (for now!)
        ArrayList<ArrayList<DocumentCell>> rows = new ArrayList<ArrayList<DocumentCell>>();
      
        UITextField title = new UITextField(x, y, "Welcome to Browsr!".length(), 50, "Welcome to Browsr!");
        UITextField authors = new UITextField(x, y, width, 20, 
	  		"By yours truly: Andreas Hinderyckx, Martijn Leplae, Thibault Van Win and Jakob Heirwegh");
        UITextField swopper = new UITextField(x, y, width, 20, "1st iteration Software-Ontwerp 2020-2021");
        UITextField empty = new UITextField(x, y, width, 20, " ");
	      
        String href = "browsrtest.html";
        String text = "Click here to see what we can do!";
	      
        UIHyperlink hyperLink = new UIHyperlink(x, y, width, 20, href, text);
	      		
        ArrayList<DocumentCell> row1 = new ArrayList<DocumentCell>();
        ArrayList<DocumentCell> row2 = new ArrayList<DocumentCell>();
        ArrayList<DocumentCell> row3 = new ArrayList<DocumentCell>();
        ArrayList<DocumentCell> row4 = new ArrayList<DocumentCell>();
        ArrayList<DocumentCell> row5 = new ArrayList<DocumentCell>();
      
	    row1.add(title);
	    row2.add(authors);
	    row3.add(empty);
	    row4.add(hyperLink);
	    row5.add(swopper);
	    
	    rows.add(row1);
	    rows.add(row2);
	    rows.add(row3);
	    rows.add(row4);
	    rows.add(row5);
	    
	    this.content = new UITable(x, y, width, height, rows);
    }
    
    /**
     * Translates the contentSpan from the domainmodel into the simplified UI-representation objects.
     * Distinction is made between domain-classes Table, HyperLink and TextSpan
     *
     * @param contents: The contents to be translated to UI elements.
     * @return a DocumentCell derived class that can be rendered on screen
     */
    private DocumentCell translateToUIElements(ContentSpan contents) {
    	final String packageName = "domainmodel";
    	DocumentCell newUIContents = null;
    	
    	switch (contents.getClass().getCanonicalName()) {
    	case (packageName+".Table") -> { newUIContents = translateTable((Table) contents);}
    	case (packageName+".HyperLink") -> { newUIContents = translateHL((HyperLink) contents);}
    	case (packageName+".TextSpan") -> { newUIContents = translateTextSpan((TextSpan) contents);}
    	default -> { 
    		System.out.println("unknown domainmodel representation class: "+contents.getClass().getCanonicalName());
    	}
    	}
    	return newUIContents;
    }
    
    /**
     * Translates a Table from the domainmodel into the simplified UI-representation
     *
     * @param content: The content to be translated to UI elements.
     * @return a UITable with the translated elements
     */
    private UITable translateTable(Table content) {
    	try {
            // get sub elements
            ArrayList<ArrayList<DocumentCell>> UIrows = new ArrayList<ArrayList<DocumentCell>>();
            // draw the table
            List<TableRow> rows = content.getRows();
            for (TableRow row : rows)
                UIrows.add(translateRow(row));

            return new UITable(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight(), UIrows);
        }
    	catch(IllegalDimensionException e){
    	    System.out.print("Invalid UITable dimensions");
    	    return null;
        }
    }
    
    /**
     * Translates a Cell from the domainmodel into the simplified UI-representation
     * @param content
     * @return a DocumentCell with translated elements
     */
    private DocumentCell translateCell(TableCell content) {
    	// get sub-elements    	
    	ContentSpan cellContent = content.getContent();
    	return translateToUIElements(cellContent); 
    }
    
    /**
     * Translates a Row from the domainmodel into the simplified UI-representation
     * @param content
     * @return an ArrayList<DocumentCell> with translated elements
     */
    private ArrayList<DocumentCell> translateRow(TableRow content) {
		// get sub elements
    	ArrayList<DocumentCell> row = new ArrayList<DocumentCell>();
    	List<TableCell> cells = content.getCells();
    	// draw the row
    	for (TableCell cell : cells) {
    		row.add(translateCell(cell)); 
    	}
    	return row;
    }
    
    /**
     * Translates a HyperLink from the domainmodel into the simplified UI-representation
     * @param content
     * @return a UIHyperlink with translated elements
     */
    private DocumentCell translateHL(HyperLink content) {
    	try {
            // get arguments
            String href = content.getHref();
            String text = content.getTextSpan().getText();
            // return UIHyperlink with arguments
            return new UIHyperlink(getxPos(), getyPos(), getWidth(), textSize, href, text);
        }
    	catch(IllegalDimensionException e){
    	    System.out.print("Invalid UIHyperLink dimensions");
    	    return null;
        }
    }
    
    /**
     * Translates a TextSpan from the domainmodel into the simplified UI-representation
     *
     * @param content: The content to be translated to UI elements.
     * @return a UITextField with translated elements
     */
    private DocumentCell translateTextSpan(TextSpan content)  {
        try {
            System.out.println("TEXT: " + content.getText());
            return new UITextField(getxPos(), getyPos(), getWidth(), textSize, content.getText());
        }
        catch(IllegalDimensionException e){
            System.out.print("Invalid UITextField dimensions");
            return null;
        }
    }


    /**
     * Renders the content. The content renders its sub-content recursively if existent
     * @param g
     */
    @Override
    public void Render(Graphics g) {
        content.Render(g);
        g.setColor(Color.green);
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * If the new window dimensions are legal, the UserInterface.DocumentArea gets resized.
     * It also resizes its content.
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        if ((newWindowWidth - getxPos()) >= 0) setWidth(newWindowWidth - getxPos());
        if ((newWindowHeight - getyPos()) >= 0) setHeight(newWindowHeight - getyPos());
        if (content != null)
            content.handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * What to do when mouse is pressed? Check the following things:
     *  <ul>
     *       <li> -> Was it on me? </li>
     *       <li> -> Was it the right button? </li>
     *       <li> -> Is it the right MouseEvent? </li>
     *       <li> => Handle the result accordingly </li>
     * </ul>
     * => If all yes, send the click to content. The result of this could be a href String or the Empty String.
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (!wasClicked(x, y)) return;
        if (button != MouseEvent.BUTTON1) return;
        if (id != MouseEvent.MOUSE_CLICKED) return;
        System.out.println("content: "+content);
        System.out.println(content.getxPos()+","+content.getyPos());
        String result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        System.out.println("result: '"+result+"' on "+this.getClass());
        linkPressed(result);
    }

    /**
     * Returns true if and only if (x,y) is in this UserInterface.DocumentArea.
     */
    private boolean wasClicked(int x, int y) {
    	System.out.println("DocArea: on: "+x+","+y);
    	System.out.println("getX: "+this.getxPos()+", getY: "+this.getyPos());
    	System.out.println("width: "+this.getWidth()+", height: "+this.getHeight());
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    /**
     * Notify the DocumentArea that the contents have been changed
     */
    public void contentChanged(){
        try{
            ContentSpan newContentSpan = controller.getContentSpan();
            this.setContent(this.translateToUIElements(newContentSpan));
        }
        catch(Exception e){
            System.out.print(e);
        }
    }

    /**
     * This method looks if the given string is a valid link.
     * If so, do the right actions.
    */
    private void linkPressed(String link){
        if (link.equals("")) return;
        // What to do when a link is pressed?
        controller.loadDocumentFromHref(link);
    }

    /**
     * Set the content of this DocumentArea
     * to the provided {@link ContentSpan}.
     *
     * @param content:
     *               The content that should be set.
     */
    public void setContent(DocumentCell content) {
        this.content = content;
    }

    /**
     * Retrieve the contents of this DocumentArea.
     *
     * @return contentSpan:
     *              A {@link ContentSpan} that denotes the contents of this DocumentArea.
     */
    public DocumentCell getContent() {
        return this.content;
    }

    /**
     * Set the DocumentArea's controller to a given controller
     *
     * @param controller
     *        The new controller
     */
    public void setController(UIController controller) {
        this.controller = controller;
    }

    /**
     * The {@link UIController} related to this DocumentArea
      */
    public UIController controller;

    /**
     * The string representation of the URL that links to this DocumentArea.
     */
    private String Url = "";

    /**
     * The text size of the {@code Url} of this DocumentArea.
     */
    private final int textSize = 14;

    /**
     * The content that is represented by this DocumentArea.
     */
    private DocumentCell content;
}


