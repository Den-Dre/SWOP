package userinterface;

import java.util.ArrayList;

/**
 * A class to denote the graphical concept of a form extended with the 
 * functionality of a customizable action.
 * A {@code UIActionForm} extends a {@link UIForm} and 
 * contains a {@link domainlayer.ContentSpan} as contents 
 * and an associated {@code action} attribute to denote how 
 * to URL must be formed in combination with the values that 
 * are input by the user.
 */
public class UIActionForm extends UIForm {
    /**
     * Create a new UIActionForm-object.
     *
     * @param x          : The x coordinate of this {@code UIActionForm}.
     * @param y          : The y coordinate of this {@code UIActionForm}.
     * @param action     : The action attribute of the HTML-counterpart.
     * @param formContent: The content off the form. This should not directly
     *                    or indirectly contain other UIActionForm-objects.
     */
    public UIActionForm(int x, int y, String action, DocumentCell formContent) {
        super(x, y, formContent);
        this.action = action;
    }

    /**
     * Get the response from this UIActionForm of a given click.
     *
     * @param id: The id of the click
     * @param x: The x coordinate of the click
     * @param y: The y coordinate of the click
     * @param clickCount: The number of clicks that occurred.
     * @param button: Which mouse button was clicked.
     * @param modifier: Extra control key that was held during the click.
     * @return A representation of this UIActionForm's state encapsulated in a {@link ReturnMessage} object
     *          of the {@link ReturnMessage.Type} Form: containing the action
     *          attribute and name=value pairs of {@link UITextInputField}s.
     */
    @Override
    public ReturnMessage getHandleMouse(int id, int x, int y, int clickCount, int button, int modifier) {
        ReturnMessage response = this.getFormContent().getHandleMouse(id, x, y, clickCount, button, modifier);
        if (response.getType() == ReturnMessage.Type.Button && response.getContent().equals("submit")) {
                return handleSubmitPressed();
            }
        return response;
    }

    /**
     * Construct the string to be returned to the parent of this Form.
     *
     * This consists of the action, names and their values separated by spaces.
     * The name and values are separated by a "=" sign.
     *
     * @return String that represents the action and the state of this UIActionForm-object.
     */
    private ReturnMessage handleSubmitPressed() {
        ArrayList<String> namesAndValues = getFormContent().getNamesAndValues();
        return new ReturnMessage(ReturnMessage.Type.Form, action + "?", namesAndValues);
    }

    /**
     * Get the action associated with this {@code UIActionForm}.
     *
     * @return The form-action.
     */
    public String getAction() {
        return action;
    }

    /**
     * The action associated with this UIActionForm.
     * Corresponds to the action attribute of the HTML-counterpart.
     */
    private final String action;

}
