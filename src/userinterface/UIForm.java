package userinterface;

import java.awt.Graphics;

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
	 * The content of this UIForm.
	 */
	protected final DocumentCell formContent;

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
	 * Get the {@link DocumentCell} encapsulated in this {@code UIForm}.
	 *
	 * @return The content of this {@code UIForm}.
	 */
	public DocumentCell getFormContent() {
	    return formContent;
	}
}
