package domainmodel;

import javax.print.Doc;
import javax.swing.text.AbstractDocument;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;

public class UIController {


    private Document document;

    /**
     * The constructor gets passed string that represents the url
     * the user has inputted in the AddressBar.
     * The url should be passed as a string s.t. the exception of a
     * malformed URL Object can be handled in the domain layer.
     * We try to form a URL object, if this fails, the constructor
     * is given a dummy URL to signify that a error page should be shown.
     *
     * @param urlString: The string of the URL that the user has inputted.
     */
    public UIController(String urlString) {
        try {
            URL url = new URL(urlString);
            this.document = new Document(url);
        } catch (MalformedURLException e) {
            URL errorUrl = null;
            String path = "src" + File.separator + "domainmodel" + File.separator + "errorPage.html";
            try {
                errorUrl = new File(path).toURI().toURL();
            } catch (MalformedURLException ignored) {}
//            URL errorUrl = getClass().getResource("errorPage.html");
            this.document = new Document(errorUrl);
        }
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     * @return contentSpan: the ContentSpan representation of the requested Document.
     */
    public ContentSpan loadDocument(String urlString) {
        try {
            return this.document.composeDocument(new URL(urlString));
        } catch (IOException e) {
            return new TextSpan("Error: malformed URL.");
        }
    }
}
