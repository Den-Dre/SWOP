package domainmodel;

import browsrhtml.ContentSpanBuilder;
import com.sun.jdi.request.ClassUnloadRequest;

import java.io.*;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A class to represent an abstract document.
 */
public class Document {

    /**
     * An {@link ArrayList} to hold all urlListeners.
     */
    private final List<DocumentListener> urlListeners = new ArrayList<>();

    /**
     * An {@link ArrayList} to hold all documentListeners.
     */
    private final List<DocumentListener> documentListeners = new ArrayList<>();

    private String urlString = "";
    private ContentSpan contentSpan = Document.getWelcomeDocument();

    // Example of a hyperlink that can be clicked
//    private String urlString = "https://people.cs.kuleuven.be/bart.jacobs/index.html";
//    private ContentSpan contentSpan; //= new HyperLink("browsrtest.html", new TextSpan("Welcome to UserInterface.Browsr! Click here to see our features!"));//new TextSpan("Welkom in UserInterface.Browsr!");
    

    /**
     * Initialize a new Document
     */
    public Document() { }

    /**
     * Initialize a new Document given a url, and two DocumentListeners representing the DocumentArea and AddressBar
     *
     * @param url
     *        The url for this document
     * @param doc
     *        The DocumentListener for this document
     * @param bar
     *        The addressbar listener for this document
     */
    public Document(String url, DocumentListener doc, DocumentListener bar) {
        this.urlString = url;
        this.urlListeners.add(bar);
        this.documentListeners.add(doc);
    }

    /**
     * Set the URL of this Document to the
     * provided string and alert the listeners.
     *
     * @param url:
     *           The url that should be set for this Document.
     */
    public void setUrlString(String url) {
        this.urlString = url;
        fireUrlChanged();
    }

    /**
     * Retrieve the URL-string of this document.
     *
     * @return urlString:
     *              The URL-string of this document.
     */
    public String getUrlString() {
        return this.urlString;
    }

    /**
     * Set the {@link ContentSpan} of this Document to
     * the provided content span.
     *
     * @param span:
     *             The {@link ContentSpan} that should be set for this Document.
     */
    public void changeContentSpan(ContentSpan span) {
        this.contentSpan = span;
        this.fireContentsChanged();
    }

    /**
     * Retrieve the {@link ContentSpan}
     * associated to this Document.
     *
     * @return contentSpan:
     *              The {@link ContentSpan} associated to this Document.
     */
    public ContentSpan getContentSpan() {
        return this.contentSpan;
    }


    /**
     * Adds a given URLListener to the list of urlListeners
     *
     * @param u
     *        The new UrlListener
     */
    public void addURLListener(DocumentListener u) {
        this.urlListeners.add(u);
        fireUrlChanged();
    }


    /**
     * Adds a given DocumentListener to the list of documentListeners
     *
     * @param d
     *        The new DocumentListener
     */
    public void addDocumentListener(DocumentListener d) {
        this.documentListeners.add(d);
        fireContentsChanged();
    }

    /**
     * Let the DocumentListeners know that the content has changed
     */
    private void fireContentsChanged() {
        for(DocumentListener u : documentListeners)
            u.contentChanged();
    }

    /**
     * Let the urlListeners know that the URL has been changed
     */
    private void fireUrlChanged(){
        for(DocumentListener u : urlListeners)
            u.contentChanged();
    }

    /**
     * Compose the document from a given url
     * and update the listeners accordingly.
     *
     * @param url: the url of the document that is to be composed.
     * @return a ContentSpan of the given {@code document}.
     * @throws IOException: If one of the parts of the code isn't code that is currently supported.
     */
    public ContentSpan composeDocument(URL url) throws IOException {
        return ContentSpanBuilder.buildContentSpan(url);
    }

    /**
     * Retrieve the contents of a docoument
     * that should be displayed when a malformed
     * URL is entered by the user.
     *
     * @return textSpan:
     *              The {@link ContentSpan} of the error document.
     */
    public static ContentSpan getErrorDocument() {
        return new TextSpan("Error: malformed URL.");
    }

    /**
     * A method to retrieve the welcome page of Browsr.
     *
     * @return contentSpan:
     *                  A {@link ContentSpan} that represents the welcome page.
     */
    public static ContentSpan getWelcomeDocument() {
        return new TextSpan("Welcome to Browsr!");
    }
}
