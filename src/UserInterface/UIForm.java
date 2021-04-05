package UserInterface;

import java.awt.*;
import java.util.ArrayList;

public class UIForm extends DocumentCell{
    /**
     * Create a new UIForm-object.
     *
     * @param action The action attribute of the HTML-counterpart.
     * @param formContent The content off the form. This should not directly
     *                    or indirectly contain other UIForm-objects.
     */
    public UIForm(int x, int y, String action, DocumentCell formContent) {
        super(x, y, formContent.getMaxWidth(), formContent.getMaxHeight());
        this.action = action;
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
    public void Render(Graphics g) {
        this.formContent.Render(g);
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
     * @return A representation of this UIForm's state. (action and name=value of UIInputFields)
     */
    @Override
    public String getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        String response = this.formContent.getHandleMouse(id, x, y, clickCount, button, modifier);
        if (response.equals("submit")) {
            String link = handleSubmitPressed();
            System.out.println(link);
            return link;
        }

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
    private String handleSubmitPressed() {
        ArrayList<String> namesAndValues = formContent.getNamesAndValues();
        StringBuilder link = new StringBuilder(action + " ");
        for (String nameAndValue : namesAndValues) {
            link.append(nameAndValue);
            link.append(" ");
        }
        return link.toString();
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
