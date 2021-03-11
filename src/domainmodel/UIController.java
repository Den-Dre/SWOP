package domainmodel;

import javax.print.Doc;
import javax.swing.text.AbstractDocument;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.UUID;

public class UIController {

    private Document document;

    public UIController() {
        this.document = new Document();
    }

//    /**
//     * The constructor gets passed string that represents the url
//     * the user has inputted in the AddressBar.
//     * The url should be passed as a string s.t. the exception of a
//     * malformed URL Object can be handled in the domain layer.
//     * We try to form a URL object, if this fails, the constructor
//     * is given a dummy URL to signify that a error page should be shown.
//     *
//     * //@param urlString: The string of the URL that the user has inputted.
//     */
//    public UIController(String urlString) {
//        try {
//            URL url = new URL(urlString);
//            this.document = new Document(url);
//        } catch (MalformedURLException e) {
//            URL errorUrl = null;
//            String path = "src" + File.separator + "domainmodel" + File.separator + "errorPage.html";
//            try {
//                errorUrl = new File(path).toURI().toURL();
//            } catch (MalformedURLException ignored) {}
////            URL errorUrl = getClass().getResource("errorPage.html");
//            this.document = new Document(errorUrl);
//        }
//    }

//    /**
//     * Load the document that is related to
//     * the provided String that represents a URL.
//     *
//     * @param urlString: the String representation of the URL of the document to be loaded.
//     * @return contentSpan: the ContentSpan representation of the requested Document.
//     */
//    public ContentSpan loadDocument2(String urlString) {
//        try {
//            return this.document.composeDocument(new URL(urlString));
//        } catch (IOException e) {
//            return new TextSpan("Error: malformed URL.");
//        }
//    }

    /**
     * Combine the current url with the given href
     * @param hrefString: the String representation of the href.
     */
    public void loadDocumentFromHref(String hrefString) {
        try {
            URL newUrl = new URL(new URL(document.getUrlString()), hrefString);
            document.changeContentSpan(this.document.composeDocument(newUrl));
            document.setUrlString(newUrl.toString());
        } catch (IOException | RuntimeException e) {
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
        } catch (IOException | RuntimeException e) {
            document.changeContentSpan(Document.getErrorDocument());
            document.setUrlString(urlString);
        }
    }

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
     *Add a documentListener to the list of urlListeners of the controllers document
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
