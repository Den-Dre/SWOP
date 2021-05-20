package userinterface;

import java.awt.Graphics;
import java.util.ArrayList;

/**
 * A class to denote the graphical concept of a form.
 * A {@code UIActionForm} contains a {@link domainlayer.ContentSpan} as contents.
 */
public class UIForm extends DocumentCell{
	
    /**
     * Create a new UIForm-object.
     *
     * @param x          : The x coordinate of this {@code UIForm}.
     * @param y          : The y coordinate of this {@code UIForm}.
     * @param formContent: The content off the form. This should not directly
     *                    or indirectly contain other UIForm-objects.
     */
	public UIForm(int x, int y, DocumentCell formContent) throws IllegalDimensionException {
		super(x, y, formContent.getMaxWidth(), formContent.getMaxHeight());
        this.formContent = formContent;
        this.formContent.setxPos(x);
        this.formContent.setyPos(y);
	}
	

	/**
	 * Renders this UIForm.
	 *
	 * @param g: The graphics to be rendered with.
	 */
	@Override
	public void render(Graphics g) {
	    this.formContent.render(g);
	}

	/**
	 * Send the given KeyEvent to this UIForm's content.
	 *
	 * @param id: The KeyEvent (Associated with type of KeyEvent)
	 * @param keyCode: The KeyEvent code (Determines the involved key)
	 * @param keyChar: The character representation of the involved key
	 * @param modifiersEx: Specifies other keys that were involved in the event
	 */
	@Override
	public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
	    this.formContent.handleKey(id, keyCode, keyChar, modifiersEx);
	}

	/**
	 * Get the maximum height of this UIForm.
	 *
	 * @return maximum height the UIForm content.
	 */
	@Override
	public int getMaxHeight() {
	    return formContent.getMaxHeight();
	}

	/**
	 * Get the maximum width of this UIForm.
	 *
	 * @return maximum width the UIForm content.
	 */
	@Override
	public int getMaxWidth() {
	    return formContent.getMaxWidth();
	}

	/**
     * If the new window dimensions are legal, this UIForm gets resized.
     * It also resizes its content.
     * 
     * @param newWindowWidth	: The new window width of this {@link LeafPane}
     * @param newWindowHeight	: The new window height of this {@link LeafPane}
     */
    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        formContent.handleResize(parentWidth, parentHeight);
    }

    /**
     * Set the y-position of this UIForm.
     *
     * Also sets the y-position of the content.
     *
     * @param yPos: The desired y-position.
     */
    @Override
    public void setyPos(int yPos) {
        super.setyPos(yPos);
        formContent.setyPos(yPos);
    }

    /**
     * Set the x-position of this UIForm.
     *
     * Also sets the x-position of the content.
     *
     * @param xPos: The desired x-position.
     */
    @Override
    public void setxPos(int xPos) {
        super.setxPos(xPos);
        formContent.setxPos(xPos);
    }

    /**
     * Set the x offset of this UIForm.
     *
     * Also sets the x offset of the content.
     *
     * @param xOffset: The desired xOffset.
     */
    @Override
    public void setxOffset(int xOffset) {
        super.setxOffset(xOffset);
        formContent.setxOffset(xOffset);
    }

    /**
     * Set the y offset of this UIForm.
     *
     * Also sets the y offset of the content.
     *
     * @param yOffset: The desired yOffset.
     */
    @Override
    public void setyOffset(int yOffset) {
        super.setyOffset(yOffset);
        formContent.setyOffset(yOffset);
    }

    /**
     * Set the x reference of this UIForm.
     *
     * Also sets the x reference of the content.
     *
     * @param xReference: The desired xReference.
     */
    @Override
    public void setxReference(int xReference) {
        super.setxReference(xReference);
        formContent.setxReference(xReference);
    }

    /**
     * Set the y reference of this UIForm.
     *
     * Also sets the y reference of the content.
     *
     * @param yReference: The desired yReference.
     */
    @Override
    public void setyReference(int yReference) {
        super.setyReference(yReference);
        formContent.setyReference(yReference);
    }

    /**
     * Set the width of the parent of this UIForm.
     *
     * Also sets the width of the parent of the content.
     *
     * @param parentWidth: The desired width.
     */
    @Override
    public void setParentWidth(int parentWidth) {
        super.setParentWidth(parentWidth);
        formContent.setParentWidth(parentWidth);
    }

    /**
     * Set the height of the parent of this UIForm.
     *
     * Also sets the height of the parent of the content.
     *
     * @param parentHeight: The desired height.
     */    
    @Override
    public void setParentHeight(int parentHeight) {
        super.setParentHeight(parentHeight);
        formContent.setParentHeight(parentHeight);
    }

    /**
     * The content of this UIForm.
     */
    private final DocumentCell formContent;

	/**
	 * Get the {@link DocumentCell} encapsulated in this {@code UIForm}.
	 *
	 * @return The content of this {@code UIForm}.
	 */
	public DocumentCell getFormContent() {
	    return formContent;
	}
}
