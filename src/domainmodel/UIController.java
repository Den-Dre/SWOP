package domainmodel;

import javax.print.Doc;
import java.io.*;
import java.net.URL;

public class UIController {

    // Made public because we need to access the addListener method
    private Document document;

    /**
     * Initializes a new UIController given a url
     *
     * @param url
     *        The url of the document inside the controller
     */
    public UIController(URL url) {
        this.document = new Document(url);
    }

    /**
     * Changes the url of the document to a given url
     * Mainly used for testing
     *
     * @param url
     *        The new url of the document
     */
    public void changeUrl(URL url){
        this.document.setUrl(url);
    }

    /**
     * TODO
     *
     * @param document
     * @return
     * @throws IOException
     */
    protected Serializable loadDocument(String document) throws IOException {
        return this.document.getSerializedContentSpan(document);
    }

    /**
     * TODO
     *
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Document getContents() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("documentStream.txt"));
        Document doc = (Document) in.readObject();
        in.close();
        return doc;
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

    /**
     * Returns a URL of the current document
     *
     * @return A URL-object of the document's url
     */
    public URL getUrl(){
        return this.document.getUrl();
    }

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
}
