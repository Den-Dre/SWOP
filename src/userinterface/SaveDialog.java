package userinterface;

import java.awt.*;
import java.util.ArrayList;

/**
 * A class to represent a dialog that
 * is shown when the user wants to savej
 * a page loaded in the Browsr application.
 *
 * <p>
 *     The dialog asks for a Name and URL value
 *     of the page to be saved, along with providing
 *     two {@link UIButton}'s: one to save the page
 *     and one to cancel the saving action.
 * </p>
 */
public class SaveDialog extends GenericDialogScreen {


    /**
     * Initialise this AbstractFrame with the given parameters.
     *
     * @param width     : The width of this AbstractFrame
     * @param height    : The height of this AbstractFrame
     * @param currentUrl: The URL of the page currently displayed in the associated {@link Browsr} object.
     * @param browsr    : The {@link Browsr} object linked to this {@code SaveDialog}.
     * @throws IllegalDimensionException: When one of the dimensions of this AbstractFrame is negative
     */
    public SaveDialog(int width, int height, String currentUrl, Browsr browsr ) throws IllegalDimensionException {
        super(0, 0, width, height, browsr, currentUrl);
        int offset = getOffset();

        this.nameInput = new UITextInputField(offset, offset, 100, getTextSize(), "Name");
        this.nameInputContents = (UITextInputField) nameInput;
        this.form = getForm(currentUrl);
    }

    public SaveDialog(SaveDialog dialog) {
        super(0, 0, dialog.getWidth(), dialog.getHeight(), dialog.getBrowsr(), dialog.getCurrentUrl());
        int offset = dialog.getOffset();

        this.nameInput = new UITextInputField(offset, offset, 100, dialog.getTextSize(), "Name");
        this.nameInputContents = nameInput;
        this.form = getForm(dialog.getCurrentUrl());
    }


    /**
     * Construct the hardcoded layout of this {@code SaveDialog}.
     *
     * @param currentUrl:
     *           The {@link String} representation of the URL currently entered in the {@link AddressBar}.
     * @return new UIForm: a {@link UIForm} object that represents the form of this {@code SaveDialog}.
     */
//    UIForm constructForm(String currentUrl) {
    public UIForm getForm(String currentUrl) {

        int offset = getOffset();
        int textSize = getTextSize();

        // Hard coded save dialog screen
        UITextField formTitle = new UITextField(offset, offset, 0,textSize, "Save As");
        UITextField nameText = new UITextField(offset, offset,0,textSize, "File name");

        ArrayList<ArrayList<DocumentCell>> innerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> innerTableRow1 = new ArrayList<>();
        innerTableRow1.add(nameText);
        innerTableRow1.add(nameInput);
        innerTableRows.add(innerTableRow1);
        UITable innerTable = new UITable(offset, offset,0,0, innerTableRows);

        UIButton saveButton = new UIButton(offset, offset,50,15, "Save", "Save");
        UIButton cancelButton = new UIButton(offset, offset,50,15, "Cancel", "Cancel");

        ArrayList<ArrayList<DocumentCell>> outerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> outerTableRow1 = new ArrayList<>();
        outerTableRow1.add(formTitle);
        ArrayList<DocumentCell> outerTableRow2 = new ArrayList<>();
        outerTableRow2.add(innerTable);
        ArrayList<DocumentCell> outerTableRow3 = new ArrayList<>();
        outerTableRow3.add(saveButton);
        outerTableRow3.add(cancelButton);
        outerTableRows.add(outerTableRow1);
        outerTableRows.add(outerTableRow2);
        outerTableRows.add(outerTableRow3);

        UITable outerTable = new UITable(offset, offset,0,0, outerTableRows);
        outerTable.handleResize(getBrowsr().getWidth(),getBrowsr().getHeight());

        return new UIForm(offset, offset, "bookmarksDialog", outerTable);
    }

    /**
     * render the contents of this {@code SaveDialog}.
     * @param g: The graphics to be rendered.
     */
    @Override
    public void render(Graphics g) {
        this.form.render(g);
    }

    /**
     * Handle mouse clicks on the content of this {@code SaveDialog}.
     *
     * @param id: The type of mouse activity
     * @param x: The x coordinate of the mouse activity
     * @param y: The y coordinate of the mouse activity
     * @param clickCount: The number of clicks
     * @param button: The mouse button that was clicked
     * @param modifiersEx: The control keys that were held on the click
     */
    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        ReturnMessage result = form.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        String name = this.nameInputContents.getText();
        try {
            handleClickResult(result, name);
        } catch (IllegalArgumentException e) {
                System.out.println("Can't save document with empty file name.");
            }
    }

    /**
     * Help method to handle mouse clicks on this {@code SaveDialog}.
     *
     * @param result:
     *              the string returned by the clicked element of the content of this {@code SaveDialog}.
     */
    protected void handleClickResult(ReturnMessage result, String name) {
        if (result.getType() != ReturnMessage.Type.Button)
            return;
        switch (result.getContent()) {
            case "Save" ->
                    getBrowsr().getController().saveDocument(name);
            case "Cancel" ->  // nothing needs to be done here
                    System.out.println("Cancel pressed.");
        }

        // Set the BrowsrLayout of the linked Browsr back to the regular Layout.
        Browsr browsr = getBrowsr();
        browsr.setBrowsrLayout(browsr.new RegularLayout());
    }

    /**
     * Return the contents of the name
     * {@link UITextInputField} of this
     * {@code SaveDialog}.
     *
      @return text: the contents of the name {@link UITextInputField} of this {@code SaveDialog}.
     */
    public String getNameInput() {
        return this.nameInputContents.getText();
    }

    /**
     * A {@link UITextInputField} to query the name
     * the user  wants to give to the URL being saved.
     */
    private final UITextInputField nameInput;

    private final UITextInputField nameInputContents;

    /**
     * A variable to represent the content
     * that this {@code SaveDialog}
     * displays. The content is contained
     * in a {@link DocumentCell}.
     */
    private final UIForm form;
}

