package domainlayer;

import java.util.ArrayList;

/**
 * A class to account for the "Controller" - GRASP principle.
 *
 * All calls that are made from the UI Layer
 * to the domain layer, pass through this
 * Controller. The Controller then redirects
 * these calls to the appropriate domain
 * layer classes. This lowers the coupling between
 * the domain and UI layer.
 */
public class UIController {

    /**
     * Initialise this Controller
     * with the given {@link Document}.
     */
    public UIController() {
        this.bookmarksURLKeeper = new BookmarksURLKeeper();
        this.documentsKeeper = new DocumentKeeper(new Document());
        // TODO replace calls to `document` field with calls to documentsKeeper (reduce chain of responsibilities).
    }


    /**
     * Combine the current url with the given href
     *
     * @param hrefString: the String representation of the href.
     */
    public void loadDocumentFromHref(int id, String hrefString) {
        documentsKeeper.getDocument(id).loadFromHref(hrefString);
    }

    /**
     * Load the document using the given arguments associated with the form.
     *
     * @param action The action associated with the form.
     * @param values An arraylist with the name-value pairs separated by "=".
     */
    public void loadDocumentFromForm(int id, String action, ArrayList<String> values) {
        Document doc = documentsKeeper.getDocument(id);
        doc.loadFromForm(action, values);
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     */
    public void loadDocument(int id, String urlString) {
        Document doc = documentsKeeper.getDocument(id);
        doc.loadFromUrl(urlString);
    }

    /**
     * Retrieve the {@link ContentSpan}
     * representation of the {@link Document}
     * that is linked to this Controller.
     *
     * @return contentSpan:
     *                  The ContentSpan representation of the Document linked to this Controller.
     */
    public ContentSpan getContentSpan(int id) {
        return documentsKeeper.getPaneContentSpan(id);
    }

    /**
     * Sets the document to a given document
     *
     * @param id
     *        The document for this UIController
     */
    public void setCurrentDocument(int id) {
        documentsKeeper.setCurrentDocumentId(id);
    }

    /**
     * Returns the current document of this UIController
     *
     * @return document:
     *              The {@link Document} linked to this UIController.
     *
     */
    public Document getCurrentDocument() {
        return documentsKeeper.getCurrentDocument();
    }

    /**
     * Retrieve the URL (in String representation)
     * of the {@link Document} that is
     * linked to this Controller.
     *
     * @return urlString:
     *              The String representation of the URL of the Document linked to this Controller.
     */
    public String getUrlString(int id) {
        return documentsKeeper.getDocument(id).getUrlString();
    }

    /**
     *Add a documentListener to the list of DocumentListeners of the controllers document
     *
     * @param d
     *        The new DocumentListener for the document
     */
    public void addDocumentListener(DocumentListener d) {
        getCurrentDocument().addDocumentListener(d);
    }

    /**
     * Add a documentListener to the list of urlListeners of the controllers document
     *
     * @param d
     *        The new documentListener for the document
     */
    public void addUrlListener(DocumentListener d) {
        getCurrentDocument().addURLListener(d);
    }

    /**
     * Get the Href value associated to this bookmark
     * from this {@code Controller}'s {@link BookmarksURLKeeper}.
     *
     * @param bookmarkName: the name of the bookmark to be retrieved.
     * @return href: The href value of the requested {@code bookmarkName}.
     */
    public String getURLFromBookmark(String bookmarkName) {
        return bookmarksURLKeeper.getHrefFromBookmark(bookmarkName);
    }

    // Temporary method for testing listeners
    /**
     * Set the URL of this {@code UIController}
     * to the given value.
     *
     * @param url: the URL to be set for this {@code UIController}.
     */
    public void changeURL(String url){
        getCurrentDocument().setUrlString(url);
    }

    /**
     * Add a {@code href} value associated to a bookmark
     * to the corresponding class in the domain layer.
     *
     * @param name: The name of the bookmark that's associated to the given {@code href} attribute.
     * @param href: The href of the bookmark that will be added to the domain layer.
     */
    public void addHref(String name, String href) {
        bookmarksURLKeeper.addBookmarksHref(name, href);
    }

    public int addPaneDocument() {
        return documentsKeeper.addPaneDocument();
    }

    public int duplicatePaneDocument(int siblingId) {
        return documentsKeeper.addPaneDocumentBasedOn(siblingId);
    }

    public int removePaneDocument(int id) {
        return documentsKeeper.removePaneDocument(id);
    }

    public ContentSpan getPaneDocument(int id) {
        return documentsKeeper.getPaneContentSpan(id);
    }

    /**
     * Method to save a document
     *
     * @param fileName: the name to be given to the document to be saved.
     */
    public void saveDocument(String fileName) throws IllegalArgumentException {
        if (fileName.equals(""))
            throw new IllegalArgumentException("Can't save page when no page is loaded");
        try {
            getCurrentDocument().saveDocument(fileName);
        } catch (Exception e) {
            System.out.println("Can't save this document!");
        }
    }

    /**
     * The {@link DocumentKeeper} that is linked
     * to this {@code Controller}.
     */
    private final DocumentKeeper documentsKeeper;

    /**
     * The {@link BookmarksURLKeeper} that is
     * linked to this {@code Controller}.
     */
    private final BookmarksURLKeeper bookmarksURLKeeper;
}
