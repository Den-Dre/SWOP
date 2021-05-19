package userinterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class to denote the graphical concept of a form.
 * A {@code UIform} contains a {@link domainlayer.ContentSpan}
 * as contents and an associated {@code action} attribute to
 * denote how to URL must be formed in combination with the
 * values that are input by the user.
 */
public class UIForm extends DocumentCell {
    /**
     * Create a new UIForm-object.
     *
     * @param x          : The x coordinate of this {@code UIForm}.
     * @param y          : The y coordinate of this {@code UIForm}.
     * @param action     : The action attribute of the HTML-counterpart.
     * @param formContent: The content off the form. This should not directly
     *                    or indirectly contain other UIForm-objects.
     */
    public UIForm(int x, int y, String action, DocumentCell formContent) {
        super(x, y, formContent.getMaxWidth(), formContent.getMaxHeight());
        this.action = action;
        this.formContent = formContent;
        this.formContent.setxPos(x);
        this.formContent.setyPos(y);
    }

    public UIForm(UIForm uiForm) {
        super(uiForm.getxPos(), uiForm.getyPos(), uiForm.getMaxWidth(), uiForm.getMaxHeight());
        this.action = uiForm.getAction();
        this.formContent = uiForm.formContent.deepCopy();
        this.formContent.setxPos(uiForm.getxPos());
        this.formContent.setyPos(uiForm.getyPos());
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
     * Get the response from this UIForm of a given click.
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occurred.
     * @param button: Which mouse button was clicked.
     * @param modifier: Extra control key that was held during the click.
     * @return A representation of this UIForm's state encapsulated in a {@link ReturnMessage} object
     *          of the {@link ReturnMessage.Type} Form: containing the action
     *          attribute and name=value pairs of {@link UITextInputField}s.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        ReturnMessage response = this.formContent.getHandleMouse(id, x, y, clickCount, button, modifier);
        if (response.getType() == ReturnMessage.Type.Button && response.getContent().equals("submit"))
            return handleSubmitPressed();
        return response;
    }

    /**
     * Construct the string to be returned to the parent of this Form.
     *
     * This consists of the action, names and their values separated by spaces.
     * The name and values are separated by a "=" sign.
     *
     * @return String that represents the action and the state of this UIForm-object.
     */
    private ReturnMessage handleSubmitPressed() {
        ArrayList<String> namesAndValues = formContent.getNamesAndValues();
        return new ReturnMessage(ReturnMessage.Type.Form, action + "?", namesAndValues);
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
     * Get the {@link DocumentCell} encapsulated in this {@code UIForm}.
     *
     * @return The content of this {@code UIForm}.
     */
    public DocumentCell getFormContent() {
        return formContent;
    }

    /**
     * Get the action associated with this {@code UIForm}.
     *
     * @return The form-action.
     */
    public String getAction() {
        return action;
    }

    /**
     * The action associated with this UIForm.
     * Corresponds to the action attribute of the HTML-counterpart.
     */
    private final String action;

    /**
     * The content of this UIForm.
     */
    private final DocumentCell formContent;

}
