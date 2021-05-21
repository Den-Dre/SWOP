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
     * Initialise this Controller with a new
     * {@link Document} and {@link DocumentKeeper}.
     */
    public UIController() {
        this.bookmarksURLKeeper = new BookmarksURLKeeper();
        this.documentsKeeper = new DocumentKeeper();
    }

    /**
     * Combine the current url with the given href and
     * load the page corresponding to the resulting URL.
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
        documentsKeeper.loadDocumentFromForm(id, action, values);
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     */
    public void loadDocument(int id, String urlString) {
        documentsKeeper.loadFromUrl(id, urlString);
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
     * Retrieve the {@code id} of the currently loaded {@link Document}.
     *
     * @return id: the {@code id} of the currently loaded {@link Document}.
     */
    public int getCurrentDocumentId() {
        return documentsKeeper.getCurrentDocumentId();
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
    public void addDocumentListener(int id, DocumentListener d) {
        documentsKeeper.addDocumentListener(id, d);
    }

    /**
     * Similar to {@link DocumentKeeper#addDocumentListener(int, DocumentListener)}, but
     * load contents based on the {@code Document} corresponding to the supplied {@code copyId}.
     *
     * @param id: the {@code id} corresponding to the {@code Document} to which the given listener should be added.
     * @param documentListener: The {@link DocumentListener} that should be added.
     * @param copyId: The id of the {@code Document} on which this listener addition is based.
     */
    public void addDocumentListener(int id, DocumentListener documentListener, int copyId) {
        documentsKeeper.addDocumentListener(id, documentListener, copyId);
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

    /**
     * Generate and add a new {@link Document} to this {@code DocumentsKeeper}.
     *
     * @return id: the id of the newly added {@link Document}.
     */
    public int addPaneDocument() {
        return documentsKeeper.addPaneDocument();
    }

    /**
     * Get the {@link Document} object associated to
     * the given {@code id}.
     *
     * @param id: The id of which the linked {@link Document} will be returned.
     * @return document: the {@code Document} associated to the given {@code id}.
     */
    public Document getDocument(int id) {
        return documentsKeeper.getDocument(id);
    }

    /**
     * Method to save a document to a file.
     *
     * @param fileName: the name to be given to the document to be saved.
     */
    public void saveDocument(String fileName) throws IllegalArgumentException {
        documentsKeeper.saveDocument(fileName);
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

    /**
     * Set the contents of the {@link Document} corresponding to
     * the given id to the contents of the {@link Document#getWelcomeDocument()}.
     *
     * @param id: the id of the {@code Document} whose contents should be set to the WelcomeDocument.
     */
    public void setWelcomeDocument(int id) {
        documentsKeeper.setWelcomeDocument(id);
    }
}