package UserInterface;

import javax.swing.plaf.IconUIResource;
import java.util.ArrayList;

public class SaveDialog extends GenericDialogScreen {

    /**
     * Initialise this Frame with the given parameters.
     *
     * @param x      : The x coordinate of this Frame.
     * @param y      : The y coordinate of this Frame.
     * @param width  : The width of this Frame
     * @param height : The height of this Frame
     * @param browsr : The {@link Browsr} object linked to this {@code SaveDialog}.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public SaveDialog(int x, int y, int width, int height, Browsr browsr, String currentUrl) throws IllegalDimensionException {
        super(x, y, width, height, browsr, currentUrl);
        int offset = getOffset();

        this.nameInput = new UITextInputField(offset, offset, 100, getTextSize(), "Name");
        this.content = getForm();
    }

    @Override
    public UIForm getForm() {
        return constructForm(getCurrentUrl());
    }

    UIForm constructForm(String currentUrl) {

        int offset = getOffset();
        int textSize = getTextSize();

        // Hard coded bookmarks dialog screen
        UITextField nameText = new UITextField(offset, offset,0,textSize, "Name");
        UITextField urlText = new UITextField(offset, offset,0,textSize, "URL");

        UITextField formTitle = new UITextField(offset, offset, 0,textSize, "Save As");

        ArrayList<ArrayList<DocumentCell>> innerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> innerTableRow1 = new ArrayList<>();
        innerTableRow1.add(nameText);
        innerTableRow1.add(nameInput);
        innerTableRows.add(innerTableRow1);
        UITable innerTable = new UITable(offset, offset,0,0, innerTableRows);

        UIButton saveButton = new UIButton(offset, offset,50,15, "Add Bookmark", "Save");
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
    private final UIForm content;
}

