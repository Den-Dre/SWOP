package domainmodel;

import java.io.IOException;
import java.net.URL;

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
        try {
            URL newUrl = new URL(new URL(document.getUrlString()), hrefString);
            document.setUrlString(newUrl.toString());
            document.changeContentSpan(this.document.composeDocument(newUrl));
        } catch (Exception e) {
            document.changeContentSpan(Document.getErrorDocument());
        }
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     */
    public void loadDocument(String urlString) {
        try {
            URL newUrl = new URL(urlString);
            document.changeContentSpan(this.document.composeDocument(newUrl));
            document.setUrlString(urlString);
        } catch (Exception e) {
            document.changeContentSpan(Document.getErrorDocument());
            document.setUrlString(urlString);
        }
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

//    /**
//     * Returns a URL of the current document
//     *
//     * @return A URL-object of the document's url
//     */
//    public URL getUrl(){
//        return this.document.getUrl();
//    }


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

    // Temp for testing
    public void changeURL(String url){
        this.document.setUrlString(url);
    }

}
