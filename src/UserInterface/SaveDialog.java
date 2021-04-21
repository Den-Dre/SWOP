package UserInterface;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class SaveDialog extends GenericDialogScreen {

    /**
     * Initialise this Frame with the given parameters.
     *
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @param browsr : The {@link Browsr} object linked to this {@code SaveDialog}.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public SaveDialog(int width, int height, String currentUrl, Browsr browsr ) throws IllegalDimensionException {
        super(0, 0, width, height, browsr, currentUrl);
        int offset = getOffset();

        this.nameInput = new UITextInputField(offset, offset, 100, getTextSize(), "Name");
        this.form = getForm();
    }


    /**
     * Construct the hardcoded layout of this {@code SaveDialog}.
     *
     * @param currentUrl:
     *           The {@link String} representation of the URL currently entered in the {@link AddressBar}.
     * @return new UIForm: a {@link UIForm} object that represents the form of this {@code SaveDialog}.
     */
    UIForm constructForm(String currentUrl) {

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

        return new UIForm(offset, offset, "bookmarksDialog", outerTable);
    }

    @Override
    public void Render(Graphics g) {
        this.form.Render(g);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        ReturnMessage result = form.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        String name = this.nameInput.getText();
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
        return this.nameInput.getText();
    }

    /**
     * A {@link UITextInputField} to query the name
     * the user  wants to give to the URL being saved.
     */
    private final UITextInputField nameInput;


    /**
     * A variable to represent the content
     * that this {@code SaveDialog}
     * displays. The content is contained
     * in a {@link DocumentCell}.
     */
    private final UIForm form;
}

