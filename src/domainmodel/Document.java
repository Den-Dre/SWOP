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

public class Document {
    private URL url;
    private List<DocumentListener> urlListeners = new ArrayList<>();
    private List<DocumentListener> documentListeners = new ArrayList<>();

    /**
     * Initialize a new Document given a url
     *
     * @param url
     *        The URL for this document
     */
    public Document(URL url) {
        this.url = url;
    }

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
    public Document(URL url, DocumentListener doc, DocumentListener bar) {
        this.url = url;
        this.urlListeners.add(bar);
        this.documentListeners.add(doc);
    }

    /**
     * Set the URl of the document to the given url
     *
     * @param url
     *        The url for this document
     */
    public void setUrl(URL url) {
        this.url = url;
        fireUrlChanged();
    }

    /**
     * Returns the given URL of the document
     *
     * @return a URL object
     */
    public URL getUrl() {
        return this.url;
    }

    /**
     * Adds a given URLListener to the list of urlListeners
     *
     * @param u
     *        The new UrlListener
     */
    public void addURLListener(DocumentListener u) {
        this.urlListeners.add(u);
    }

    /**
     * Removes a given URLListener from the list of urlListeners
     *
     * @param u
     *        The UrlListener to be removed
     */
    public void removeURLListener(DocumentListener u) {
        this.urlListeners.remove(u);
    }

    /**
     * Adds a given DocumentListener to the list of documentListeners
     *
     * @param d
     *        The new DocumentListener
     */
    public void addDocumentListener(DocumentListener d) {
        this.documentListeners.add(d);
    }

    /**
     * Removes a given DocumentListener from the list of documentListeners
     *
     * @param d
     *        The DocumentListener to be removed
     */
    public void removeDocumentListener(DocumentListener d) {
        this.documentListeners.remove(d);
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
     * Compose the document from a given string of code,
     * and update the listeners accordingly
     *
     * @param document: the HTML code of the document that is to be composed
     * @return a ContentSpan of the given {@code document}.
     */
    public ContentSpan composeDocument(String document) {
        // TODO: verify whether this code of the listeners should be here
        //fireUrlChanged(); for testing
        fireContentsChanged();

        return ContentSpanBuilder.buildContentSpan(document);
    }

    /**
     * Compose the document from a given url
     * and update the listeners accordingly.
     *
     * @param url: the url of the document that is to be composed.
     * @return a ContentSpan of the given {@code document}.
     */
    public ContentSpan composeDocument(URL url) throws IOException {
        // TODO: verify whether this code of the listeners should be here
        //fireUrlChanged(); for testing
        fireContentsChanged();

        return ContentSpanBuilder.buildContentSpan(url);
    }


    /**
     * Serialize the ContentSpan associated with this Document
     *
     * @return The content span in the form of a byte[] array
     * @throws IOException: if the stream can't be written
     */
    @Deprecated
   public byte[] getSerializedContentSpan(String doc) throws IOException {
        ContentSpan contentSpan = this.composeDocument(doc);

       // Create a bitstream to serialize the object
       // see: https://www.javahelps.com/2015/07/serialization-in-java.html
       byte[] stream = null;

       try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);) {
            oos.writeObject(contentSpan);
            stream = baos.toByteArray();
       } catch (IOException e) {
            e.printStackTrace();
       }
       return stream;

//        Alternative: make use of fileOutPutstream's and write serialized document to a file

//        Create the output stream (see: https://www.javatpoint.com/serialization-in-java)
//        FileOutputStream fOut = new FileOutputStream("documentStream.txt");
//        ObjectOutputStream out = new ObjectOutputStream(fOut);
//
//        out.writeObject(contentSpan);
//        out.flush();
//
//        // Close the stream
//        out.close();
    }

    public static ContentSpan getErrorDocument() {
        return new TextSpan("Error: malformed URL.");
    }
}
