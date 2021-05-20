package userinterface;

import domainlayer.*;

import javax.print.Doc;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent frames containing content to be displayed in browsr,
 * extended from {@link AbstractFrame}.
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
    }

//    /**
//     * Initialize a {@code ContentFrame} with the given {@code ContentFrame} parameters
//     * and an {@code siblingId} as a reference for which page should be loaded.
//     *
//     * @param x        : The x coordinate of the {@code ContentFrame}.
//     * @param y        : The y coordinate of the {@code ContentFrame}.
//     * @param width    : The width of the {@code ContentFrame}.
//     * @param height   : The height of the {@code ContentFrame}.
//     * @param siblingId: The id of another {@code Pane} object whose page should be copied.
//     */
//    public ContentFrame(int x, int y, int width, int height, int siblingId) {
//        super(x, y, width, height);
//        this.id = controller.duplicatePaneDocument(siblingId);
//    }

    /**
     * Translates the contentSpan from the Domain-model into the simplified UI-representation objects.
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
     * Translates a Table from the Domain-model into the simplified UI-representation
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
     * Translates a Cell from the Domain-model into the simplified UI-representation
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
     * Translates a Row from the Domain-model into the simplified UI-representation
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
     * Translates a HyperLink from the Domain-model into the simplified UI-representation
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
     * Translates a TextSpan from the Domain-model into the simplified UI-representation
     *
     * @param content: The content to be translated to UI elements.
     * @return a UITextField with translated elements
     */
    private DocumentCell translateTextSpan(TextSpan content)  {
        try {
//            System.out.println("TEXT: " + content.getText());
            return new UITextField(getxPos(), getyPos(), getWidth(), textSize, content.getText());
        }
        catch(IllegalDimensionException e){
            System.out.print("Invalid UITextField dimensions");
            return null;
        }
    }
    
    /**
     * Translates a Form from the Domain-model into the simplified UI-representation
     * 
     * @param content: The content to be translated to UI elements.
     * @return  a UIForm with translated elements
     */
    private DocumentCell translateForm(Form content) {
    	DocumentCell formContentsTranslated = translateToUIElements(content.getContent());
    	return new UIActionForm(getxPos(), getyPos(), content.getAction(), formContentsTranslated);
    }
    
    /**
     * Translates a {@link TextInputField} from the Domain-model into the simplified UI-representation
     * The created {@link UITextInputField} should also have a horizontal scroll bar and is decorated as such.
     * 
     * @param content: The content to be translated to UI elements.
     * @return a UITextInputField with translated elements
     */
    private DocumentCell translateTextInputField(TextInputField content) {
    	return new UITextInputField(getxPos(), getyPos(), 100, textSize, content.getName());
    }
    
    /**
     * Translates a SubmitButton from the Domain-model into the simplified UI-representation
     *  
     * @return a UIButton with translated elements
     */
    private DocumentCell translateSubmitButton() {
    	return new UIButton(getxPos(), getyPos(), 50, 15, "Submit", "submit");
    }    
    
    /**
     * Renders the content. The content renders its sub-content recursively if existent.
     * 
     * @param g: The graphics to be rendered
     */
    @Override
    public void render(Graphics g) {
        content.render(g);
        System.out.println("[" + content + " Rendered content at: (" + getxPos() + ", " + getyPos() + ")]");
//        g.setColor(Color.green);
        //g.drawRect(getxPos(), getyPos(), getWidth(), getHeight());
    }

    /**
     * If the new window dimensions are legal, the UserInterface.ContentFrame gets resized.
     * It also resizes its content.
     * 
     * @param newWindowWidth	: The new window width of this {@link LeafPane}
     * @param newWindowHeight	: The new window height of this {@link LeafPane}
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        setWidth(newWindowWidth);
        setHeight(newWindowHeight);
        setParentWidth(newWindowWidth);
        setParentHeight(newWindowHeight);
        if (content != null) {
            content.setWidth(newWindowWidth);
            content.setHeight(newWindowHeight);
            content.handleResize(getWidth(), getHeight());
            content.setParentWidth(getWidth());
            content.setParentHeight(getHeight());
        }

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
     * 
     * @param id          : The type of mouse activity
     * @param x           : The x coordinate of the mouse activity
     * @param y           : The y coordinate of the mouse activity
     * @param clickCount  : The number of clicks
     * @param button      : The mouse button that was clicked
     * @param modifiersEx : The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        if (button != MouseEvent.BUTTON1 && id != MouseEvent.MOUSE_DRAGGED) return;
        // if (id != MouseEvent.MOUSE_CLICKED) return;
        ReturnMessage result = content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        linkPressed(result);
    }

    /**
     * Handle key presses. This method does the right action when a key is pressed.
     *
     * @param id          : The KeyEvent (Associated with type of KeyEvent)
     * @param keyCode     : The KeyEvent code (Determines the involved key)
     * @param keyChar     : The character representation of the involved key
     * @param modifiersEx : Specifies other keys that were involved in the event
     */
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
                controller.loadDocumentFromHref(id, link.getContent());
                break;
            case Form:
                controller.loadDocumentFromForm(id, link.getContent(), link.getContentList());
                break;
        }
    }


    /**
     * Notify the {@link LeafPane} that the contents have been changed
     */
    public void contentChanged() {
        try {
            ContentSpan newContentSpan = controller.getContentSpan(id);
            DocumentCell translatedContentSpan = translateToUIElements(newContentSpan);
            setContent(translatedContentSpan);
        }
        catch(Exception e) {
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
        this.content.setParentWidth(getWidth());
        this.content.setParentHeight(getHeight());
        this.content.setxReference(getxPos());
        this.content.setyReference(getyPos());
    }

    /**
     * Set the contents of this {@code ContentFrame}
     * to the Welcome Document.
     */
    protected void setWelcomeDocument() {
        setContent(translateToUIElements(Document.getWelcomeDocument()));
        controller.setWelcomeDocument(id);
    }

    /**
     * Retrieve the contents of this {@code ContentFrame}.
     *
     * @return contentSpan:
     *              A {@link ContentSpan} that denotes the contents of this {@code ContentFrame}.
     */
    public DocumentCell getContent() {
        return this.content;
    }

    /**
     * Retrieve the {@link UIController} object associated to this {@code ContentFrame}
     *
     * @return controller: the {@link UIController} object associated to this {@code ContentFrame}.
     */
    public UIController getController() {
        return controller;
    }

    /**
     * Set this {@code ContentFrame}'s controller to a given {@link domainlayer.UIController} object.
     *
     * @param controller : The new {@link domainlayer.UIController} object
     */
    public void setController(UIController controller) {
        this.controller = controller;
    }

//    /**
//     * Get the id associated to the {@link LeafPane} of this {@code ContentFrame}.
//     *
//     * @return id: the id associated to the {@link LeafPane} of this {@code ContentFrame}.
//     */
//    public int getId() {
//        return this.id;
//    }
    
    /**
     * Set the id of this {@code ContentFrame}.
     * 
     * @param id : the new id of this {@code ContentFrame}.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Set the x position of this {@code ContentFrame} and its {@code content} to the given value (if existent).
     *
     * @param xPos :
     *             The value this {@code ContentFrame}'s and its {@code content}'s x position should be set to.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        if (content != null) {
            content.setxPos(xPos);
            content.setxReference(xPos);
        }
    }

    /**
     * Set the y position of this {@code ContentFrame} and its {@code content} to the given value (if existent).
     *
     * @param yPos :
     *             The value this {@code ContentFrame}'s and its {@code content}'s y position should be set to.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        if (content != null) {
            content.setyPos(yPos);
            content.setyReference(yPos);
        }
    }

    /**
     * Set the width of the parent of this {@code ContentFrame} and the width of the parent of its {@code content}
     * to the given value (if existent).
     * 
     * @param parentWidth :
     * 						The value the parent of this {@code ContentFrame}'s and the parent of its 
     * 						{@code content}'s width should be set to.
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        if (content != null)
            content.setParentWidth(parentWidth);
    }

    /**
     * Set the height of the parent of this {@code ContentFrame} and the width of the parent of its {@code content}
     * to the given value (if existent).
     * 
     * @param parentHeight :
     * 						The value the parent of this {@code ContentFrame}'s and the parent of its 
     * 						{@code content}'s height should be set to.
     */
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        if (content != null)
            content.setParentHeight(parentHeight);
    }

    /**
     * The {@link UIController} related to this {@code ContentFrame}.
      */
    public UIController controller;

    /**
     * The text size of the {@code Url} of this {@code ContentFrame}.
     */
    private final int textSize = 14;

    /**
     * The content of type {@link DocumentCell} that is represented by this {@code ContentFrame}.
     */
    private DocumentCell content;

    /**
     * The id of the {@link LeafPane} associated to this {@code ContentFrame}.
     * Initialize the {@code id} with -1 to indicate it hasn't been assigned.
     */
    private int id = -1;
}


