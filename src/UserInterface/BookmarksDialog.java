package UserInterface;

import java.awt.*;
import java.util.ArrayList;

public class BookmarksDialog extends GenericDialogScreen {

    /**
     * Initialise this Frame with the given parameters.
     *
     * @param width      : The width of this Frame.
     * @param height     : The height of this Frame.
     * @param currentUrl : The URL of the document currently loaded.
     * @throws IllegalDimensionException: When one of the dimensions of this Frame is negative
     */
    public BookmarksDialog(int width, int height, String currentUrl, BookmarksBar bookmarksBar, Browsr browsr) throws IllegalDimensionException {
        // Must cover the entire screen:
        super(0, 0, width, height, browsr);
        this.bookmarksBar = bookmarksBar;
        this.currentUrl = currentUrl;
        this.nameInput = new UITextInputField(offset, offset,100,textSize, "Name");
        this.urlInput = new UITextInputField(offset, offset,100,textSize, "URL");
        this.content = getForm();
    }

    public UIForm getForm() {
        return constructForm(currentUrl);
    }

    private UIForm constructForm(String currentUrl) {

        // Hard coded bookmarks dialog screen
        UITextField nameText = new UITextField(offset, offset,0,textSize, "Name");
        UITextField urlText = new UITextField(offset, offset,0,textSize, "URL");

        // URL input must be prefilled:
        urlInput.setText(currentUrl);

        ArrayList<ArrayList<DocumentCell>> innerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> innerTableRow1 = new ArrayList<>();
        innerTableRow1.add(nameText);
        innerTableRow1.add(nameInput);
        ArrayList<DocumentCell> innerTableRow2 = new ArrayList<>();
        innerTableRow2.add(urlText);
        innerTableRow2.add(urlInput);
        innerTableRows.add(innerTableRow1);
        innerTableRows.add(innerTableRow2);
        UITable innerTable = new UITable(offset, offset,0,0, innerTableRows);

        UIButton addButton = new UIButton(offset, offset,50,15, "Add Bookmark", "Add Bookmark");
        UIButton cancelButton = new UIButton(offset, offset,50,15, "Cancel", "Cancel");

        ArrayList<ArrayList<DocumentCell>> outerTableRows = new ArrayList<>();
        ArrayList<DocumentCell> outerTableRow2 = new ArrayList<>();
        outerTableRow2.add(innerTable);
        ArrayList<DocumentCell> outerTableRow3 = new ArrayList<>();
        outerTableRow3.add(addButton);
        outerTableRow3.add(cancelButton);
        outerTableRows.add(outerTableRow2);
        outerTableRows.add(outerTableRow3);

        UITable outerTable = new UITable(offset, offset,0,0, outerTableRows);

        return new UIForm(offset, offset, "bookmarksDialog", outerTable);
    }

    @Override
    public void Render(Graphics g) {
        this.content.Render(g);
    }

    @Override
    public void handleMouse(int id, int x, int y, int clickCount, int button, int modifiersEx) {
        String result = this.content.getHandleMouse(id, x, y, clickCount, button, modifiersEx);
        handleClickResult(result);
    }

    private void handleClickResult(String result) {
        if (result.equals(""))
            return;
        switch (result) {
            case "Add Bookmark" ->
                    bookmarksBar.addBookmark(nameInput.getText(), urlInput.getText());
            case "Cancel" ->  // nothing needs to be done here
                    System.out.println("Cancel pressed.");
            case "Save" ->
                    System.out.println("Save document not implemented yet.");
        }
        getBrowsr().setBrowsrLayout(BrowsrLayout.REGULAR);
    }

    @Override
    public void handleKey(int id, int keyCode, char keyChar, int modifiersEx) {
        this.content.handleKey(id, keyCode, keyChar, modifiersEx);
    }

    @Override
    public void handleResize(int newWindowWidth, int newWindowHeight) {
        this.content.handleResize(newWindowWidth, newWindowHeight);
    }

    /**
     * A variable to represent the content
     * that this {@code BookmarksDialog}
     * displays. The content is contained
     * in a {@link DocumentCell}.
     */
    private final UIForm content;

    /**
     * A string representation of the
     * URL of the document currently loaded.
     */
    private final String currentUrl;

    /**
     * The {@link BookmarksBar} associated
     * to this {@code BookmarksBarDialog}.
     */
    private final BookmarksBar bookmarksBar;

    /**
     * The input field which takes a name for
     * the to be created bookmark.
     */
    private final UITextInputField nameInput;

    /**
     * The input field which takes an URL in
     * string form for the to be created bookmark.
     */
    private final UITextInputField urlInput;

    /**
     * The text size of the text displayed
     * in this {@code BookmarksDialog}.
     */
    private final int textSize = 14;

    /**
     * The offset for the input, text fields and buttons
     * displayed in this {@code BookmarksDialog}.
     */
    private final int offset = 5;
}
