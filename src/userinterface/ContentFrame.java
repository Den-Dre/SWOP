package userinterface;

import domainlayer.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent the portion of Broswr that renders the document
 */
public class ContentFrame extends AbstractFrame implements DocumentListener {
    /**
     * Construct a {@code ContentFrame} with the given parameters.
     *
     * @param x      : The x coordinate of the {@code ContentFrame}.
     * @param y      : The y coordinate of the {@code ContentFrame}.
     * @param width  : The width of the {@code ContentFrame}.
     * @param height : The height of the {@code ContentFrame}.
     * @throws IllegalDimensionException : When one of the dimensions is negative.
     */
    public ContentFrame(int x, int y, int width, int height) throws IllegalDimensionException {
        super(x, y, width, height);
//        DocumentCellDecorator decoratedDocCell = new HorizontalScrollBarDecorator(new VerticalScrollBarDecorator(getContent()));
//        setContent(decoratedDocCell);

//        // hard-coded Welcome document (for now!)
//        ArrayList<ArrayList<DocumentCell>> rows = new ArrayList<>();
//
//        UITextField title = new UITextField(x, y, "Welcome to UserInterface.Browsr!".length(), 50, "Welcome to UserInterface.Browsr!");
//        UITextField authors = new UITextField(x, y, width, 20,
//	  		"By yours truly: Andreas Hinderyckx, Martijn Leplae, Thibault Van Win and Jakob Heirwegh");
//        UITextField swopper = new UITextField(x, y, width, 20, "1st iteration Software-Ontwerp 2020-2021");
//        UITextField empty = new UITextField(x, y, width, 20, " ");
//
//        String href = "browsrtest.html";
//        String text = "Click here to see what we can do!";
//
//        UIHyperlink hyperLink = new UIHyperlink(x, y, width, 20, href, text);
//
//        ArrayList<DocumentCell> row1 = new ArrayList<>();
//        ArrayList<DocumentCell> row2 = new ArrayList<>();
//        ArrayList<DocumentCell> row3 = new ArrayList<>();
//        ArrayList<DocumentCell> row4 = new ArrayList<>();
//        ArrayList<DocumentCell> row5 = new ArrayList<>();
//
//	    row1.add(title);
//	    row2.add(authors);
//	    row3.add(empty);
//	    row4.add(hyperLink);
//	    row5.add(swopper);
//
//	    rows.add(row1);
//	    rows.add(row2);
//	    rows.add(row3);
//	    rows.add(row4);
//	    rows.add(row5);
    }

    public ContentFrame(ContentFrame frame) {
        super(frame.getxPos(), frame.getyPos(), frame.getWidth(), frame.getHeight());
        this.content = frame.content.deepCopy();
        this.controller = frame.controller;
    }

    /**
     * Create a deep copy of this {@code AbstractFrame} object.
     *
     * @return copy: a deep copied version of this {@code AbstractFrame}
     * object which thus does not point to the original object.
     */
    @Override
    protected ContentFrame deepCopy() {
        return new ContentFrame(this);
    }

    /**
     * Translates the contentSpan from the domainmodel into the simplified UI-representation objects.
     * Distinction is made between domain-classes Table, HyperLink, TextSpan, TextInputField, Form and SubmitButton
     *
     * @param contents: The contents to be translated to UI elements.
     * @return a DocumentCell derived class that can be rendered on screen
     */
    private DocumentCell translateToUIElements(ContentSpan contents) {
        DocumentCell newUIContents = null;

        if (contents instanceof Table)
            newUIContents = translateTable((Table) contents);
        else if (contents instanceof HyperLink)
            newUIContents = translateHL((HyperLink) contents);
        else if (contents instanceof TextSpan)
            newUIContents = translateTextSpan((TextSpan) contents);
        else if (contents instanceof TextInputField)
        	newUIContents = translateTextInputField((TextInputField) contents);
        else if (contents instanceof Form)
        	newUIContents = translateForm((Form) contents);
        else if (contents instanceof SubmitButton)
        	newUIContents = translateSubmitButton();
        else
            System.out.println("unknown domainmodel representation class: " + contents.getClass().getCanonicalName());
        return newUIContents;
    }

    /**
     * Translates a Table from the domainmodel into the simplified UI-representation
     *
     * @param content: The content to be translated to UI elements.
     * @return a UITable with the translated elements
     */
    private DocumentCell translateTable(Table content) {
    	try {
            // get sub elements
            ArrayList<ArrayList<DocumentCell>> UIrows = new ArrayList<>();
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
     * 
     * @param content: The content to be translated to UI elements
     * @return a DocumentCell with translated elements
     */
    private DocumentCell translateCell(TableCell content) {
    	// get sub-elements    	
    	ContentSpan cellContent = content.getContent();
    	return translateToUIElements(cellContent); 
    }
    
    /**
     * Translates a Row from the domainmodel into the simplified UI-representation
     * 
     * @param content: The content to be translated to UI elements
     * @return an ArrayList<DocumentCell> with translated elements
     */
    private ArrayList<DocumentCell> translateRow(TableRow content) {
		// get sub elements
    	ArrayList<DocumentCell> row = new ArrayList<>();
    	List<TableCell> cells = content.getCells();
    	// draw the row
    	for (TableCell cell : cells) {
    		row.add(translateCell(cell)); 
    	}
    	return row;
    }
    
    /**
     * Translates a HyperLink from the domainmodel into the simplified UI-representation
     * 
     * @param content: The content to be translated to UI elements
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
     * Translates a Form from the domainmodel into the simplified UI-representation
     * 
     * @param content: The content to be translated to UI elements.
     * @return  a UIForm with translated elements
     */
    private DocumentCell translateForm(Form content) {
    	DocumentCell formContentsTranslated = translateToUIElements(content.getContent());
    	return new UIForm(getxPos(), getyPos(), content.getAction(), formContentsTranslated);
    }
    
    /**
     * Translates a {@link TextInputField} from the domainmodel into the simplified UI-representation
     * The created {@link UITextInputField} should also have a horizontal scroll bar and is decorated as such.
     * 
     * @param content: The content to be translated to UI elements.
     * @return a UITextInputField with translated elements
     */
    private DocumentCell translateTextInputField(TextInputField content) {
    	return new HorizontalScrollBarDecorator(new UITextInputField(getxPos(), getyPos(), 100, textSize, content.getName()));
    }
    
    /**
     * Translates a SubmitButton from the domainmodel into the simplified UI-representation
     *  
     * @return a UIButton with translated elements
     */
    private DocumentCell translateSubmitButton() {
    	return new UIButton(getxPos(), getyPos(), 50, 15, "Submit", "submit");
    }    
    
    /**
     * Renders the content. The content renders its sub-content recursively if existent
     * @param g: The graphics to be rendered
     */
    @Override
    public void render(Graphics g) {
        content.render(g);
        g.setColor(Color.green);
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * If the new window dimensions are legal, the UserInterface.ContentFrame gets resized.
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
        if (button != MouseEvent.BUTTON1) return;
        // if (id != MouseEvent.MOUSE_CLICKED) return;
        ReturnMessage result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        linkPressed(result);
    }

    /**
     * Returns true if and only if (x,y) is in this UserInterface.ContentFrame.
     *
     * @param x: The x coordinate to check
     * @param y: the y coordinate to check
     */
    private boolean wasClicked(int x, int y) {
//    	System.out.println("DocArea: on: "+x+","+y);
//    	System.out.println("getX: "+this.getxPos()+", getY: "+this.getyPos());
//    	System.out.println("width: "+this.getWidth()+", height: "+this.getHeight());
        return x >= this.getxPos() && x <= (this.getxPos() + this.getWidth()) && y >= this.getyPos() && y <= (this.getyPos() + this.getHeight());
    }

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        content.handleKey(id, keyCode, keyChar, modifiersEx);
    }

    /**
     * This method looks if the given string is a valid link.
     * If so, do the right actions.
     *
     * @param link: The string of the link to be checked
    */
    private void linkPressed(ReturnMessage link){
        switch (link.getType()) {
            case Empty:
                return;
            case Hyperlink:
                controller.loadDocumentFromHref(link.getContent());
                break;
            case Form:
                controller.loadDocumentFromForm(link.getContent(), link.getContentList());
                break;
        }
    }


    /**
     * Notify the LeafPane that the contents have been changed
     */
    public void contentChanged() {
        try{
            ContentSpan newContentSpan = controller.getContentSpan();
            DocumentCell newContents = translateToUIElements(newContentSpan);
            setContent(newContents);
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Set the content of this ContentFrame
     * to the provided {@link ContentSpan}.
     *
     * The content to be set is wrapped in a
     * {@link UITable} for easy decoration using the
     * {@link HorizontalScrollBarDecorator} and
     * {@link VerticalScrollBarDecorator} decorators.
     *
     * @param content:
     *               The content that should be set.
     */
    public void setContent(DocumentCell content) {
        this.content = new HorizontalScrollBarDecorator(new VerticalScrollBarDecorator(content));
    }

    /**
     * Retrieve the contents of this ContentFrame.
     *
     * @return contentSpan:
     *              A {@link ContentSpan} that denotes the contents of this ContentFrame.
     */
    public DocumentCell getContent() {
        return this.content;
    }

    /**
     * Set the ContentFrame's controller to a given controller
     *
     * @param controller
     *        The new controller
     */
    public void setController(UIController controller) {
        this.controller = controller;
    }

    /**
     * The {@link UIController} related to this ContentFrame
      */
    public UIController controller;

    /**
     * The text size of the {@code Url} of this ContentFrame.
     */
    private final int textSize = 14;

    /**
     * The content that is represented by this ContentFrame.
     */
    private DocumentCell content;
}


