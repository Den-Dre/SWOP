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
        super(x, y, 0, 0);
        this.action = action;
        this.formContent = formContent;
    }

    @Override
    public void Render(Graphics g) {
        this.formContent.Render(g);
    }

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

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.formContent.handleKey(id, keyCode, keyChar, modifiersEx);
    }

    @Override
    public int getMaxHeight() {
        return formContent.getMaxHeight();
    }

    @Override
    public int getMaxWidth() {
        return formContent.getMaxWidth();
    }

    @Override
    public void setyPos(int yPos) {
        formContent.setyPos(yPos);
    }

    @Override
    public void setxPos(int xPos) {
        formContent.setxPos(xPos);
    }

    private String action;
    private DocumentCell formContent;









}
