package UserInterface;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.lang.ModuleLayer.Controller;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import domainmodel.*;

/*
-> This class (and the AddressBar) will need an extra field "Controller".
-> This var should be set in the main-Class with documentarea.setController(controller) and the same for AddressBar
-> Initially the documentarea is empty (?). When the lister alerts the documentarea.
It should then call this.controller.getContents(this.controller.getURL()) to fetch the document.
-> If there is a link pressed, the document should compose a new url using this.URL and the retrieved href.
It should then call this.controller.loadDocument(newUrl)
 */

public class DocumentArea extends Frame {
    public DocumentArea(int x, int y, int width, int height) throws Exception {
        super(x, y, width, height);

//        ContentSpan content = this.controller.getContentSpan();
//
//        this.content = translateToUIElements(content); // here we need to feed the 'real' content once implemented
    }
    
    /**
     * Translates the contentSpan from the domainmodel into the simplified UI-representation objects.
     * Distinction is made between domain-classes Table, HyperLink and TextSpan
     * @param contents
     * @return a DocumentCell derived class that can be rendered on screen
     * @throws Exception 
     */
    private DocumentCell translateToUIElements(ContentSpan contents) throws Exception {    	
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
     * @param content
     * @return a UITable with the translated elements
     * @throws Exception 
     */
    private UITable translateTable(Table content) throws Exception {
    	// get sub elements
    	ArrayList<ArrayList <DocumentCell>> UIrows = new ArrayList<ArrayList <DocumentCell>>();
    	// draw the table
    	List<TableRow> rows = content.getRows();
    	for (TableRow row : rows)
    		UIrows.add(translateRow(row));
    	
    	return new UITable(this.getxPos(), this.getyPos(), this.getWidth(), this.getHeight(), UIrows);
    }
    
    /**
     * Translates a Cell from the domainmodel into the simplified UI-representation
     * @param content
     * @return a DocumentCell with translated elements
     * @throws Exception 
     */
    private DocumentCell translateCell(TableCell content) throws Exception {
    	// get sub-elements    	
    	ContentSpan cellContent = content.getContent();
    	return translateToUIElements(cellContent); 
    }
    
    /**
     * Translates a Row from the domainmodel into the simplified UI-representation
     * @param content
     * @return an ArrayList<DocumentCell> with translated elements
     * @throws Exception 
     */
    private ArrayList<DocumentCell> translateRow(TableRow content) throws Exception {
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
     * @throws Exception 
     */
    private DocumentCell translateHL(HyperLink content) throws Exception {    	
    	// get arguments
    	String href = content.getHref();
    	String text = content.getTextSpan().getText();
    	// return UIHyperlink with arguments
    	return new UIHyperlink(getxPos(), getyPos(), getWidth(), textSize, href, text);
    }
    
    /**
     * Translates a TextSpan from the domainmodel into the simplified UI-representation
     * @param content
     * @return a UITextField with translated elements
     * @throws Exception 
     */
    private DocumentCell translateTextSpan(TextSpan content) throws Exception {
    	System.out.println("TEXT: " + content.getText());
    	return new UITextField(getxPos(), getyPos(), getWidth(), textSize, content.getText());
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

    /**
     * Notify the DocumentArea that the contents have been changed
     */
    public void contentChanged(){
        try{
            //Document newDoc = this.controller.getContents();
            //this.content = mapToDocumentcells(newDoc);
        }
        catch(Exception e){
            System.out.print(e);
        }
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


    private UIController controller;

    private String Url = "";

    private final int textSize = 14;

    private DocumentCell content;
}


