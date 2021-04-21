package domainmodel;

import java.net.URL;
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
     * The {@link Document} that is linked
     * to this Controller.
     */
    private Document document;

    /**
     * Initialise this Controller
     * with the given {@link Document}.
     */
    public UIController() {
        this.document = new Document();
    }


    /**
     * Combine the current url with the given href
     *
     * @param hrefString: the String representation of the href.
     */
    public void loadDocumentFromHref(String hrefString) {
        document.loadFromHref(hrefString);
    }

    /**
     * Load the document using the given arguments associated with the form.
     *
     * @param action The action associated with the form.
     * @param values An arraylist with the name-value pairs separated by "=".
     */
    public void loadDocumentFromForm(String action, ArrayList<String> values) {
        document.loadFromForm(action, values);
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     */
    public void loadDocument(String urlString) {
        document.loadFromUrl(urlString);
    }

    /**
     * Retrieve the {@link ContentSpan}
     * representation of the {@link Document}
     * that is linked to this Controller.
     *
     * @return contentSpan:
     *                  The ContentSpan representation of the Document linked to this Controller.
     */
    public ContentSpan getContentSpan() {
        return this.document.getContentSpan();
    }


    /**
     * Sets the document to a given document
     *
     * @param doc
     *        The document for this UIController
     */
    public void setDocument(Document doc){
        this.document = doc;
    }

    /**
     * Returns the current document of this UIController
     *
     * @return document:
     *              The {@link Document} linked to this UIController.
     *
     */
    public Document getDocument() {
        return document;
    }


    /**
     * Retrieve the URL (in String representation)
     * of the {@link Document} that is
     * linked to this Controller.
     *
     * @return urlString:
     *              The String representation of the URL of the Document linked to this Controller.
     */
    public String getUrlString() { return this.document.getUrlString();}

    /**
     *Add a documentListener to the list of DocumentListeners of the controllers document
     *
     * @param d
     *        The new DocumentListener for the document
     */
    public void addDocumentListener(DocumentListener d) {
        this.document.addDocumentListener(d);
    }

    /**
     * Add a documentListener to the list of urlListeners of the controllers document
     *
     * @param d
     *        The new documentListener for the document
     */
    public void addUrlListener(DocumentListener d) {
        this.document.addURLListener(d);
    }

    // Temporary method for testing listeners
    public void changeURL(String url){
        this.document.setUrlString(url);
    }


    /**
     * Method to save a document
     */
    public void saveDocument(String fileName){
        try {
            this.document.saveDocument(fileName);
        } catch (Exception e) {
            System.out.println("Can't save this document!");
        }
    }

}
