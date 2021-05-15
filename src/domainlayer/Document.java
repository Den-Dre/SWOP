package domainlayer;

import browsrhtml.BrowsrDocumentValidator;
import browsrhtml.ContentSpanBuilder;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
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
     * Combine the current url with the given href
     *
     * @param href: the String representation of the href.
     */
    public void loadFromHref(String href) {
        try {
            URL newUrl = new URL(new URL(getUrlString()), href);
            setUrlString(newUrl.toString());
            changeContentSpan(composeDocument(newUrl));
        } catch (Exception e) {
            changeContentSpan(Document.getErrorDocument());
        }
    }

    /**
     * Load a new document by combining the current url with the given action and values.
     *
     * @param action The action associated with the form.
     * @param values The values to be passed into the url separated with '='.
     */
    public void loadFromForm(String action, ArrayList<String> values) {
        try {
            URL newUrl = new URL(new URL(getUrlString()), action + getEncodedValues(values));
            setUrlString(newUrl.toString());
            changeContentSpan(composeDocument(newUrl));
        } catch (Exception e) {
            changeContentSpan(Document.getErrorDocument());
        }
    }

    /**
     * Encode the values into the correct format.
     *
     * @param list An arraylist in which each element corresponds to a name-value pair separated with the '=' character.
     * @return An encoded version of the given name-value pairs separated by '&'.
     */
    private String getEncodedValues(ArrayList<String> list) {
        StringBuilder values = new StringBuilder("");
        for (String nameValue :  list) {
            String[] nameAndValue = nameValue.split("=");
            values.append(nameAndValue[0]);
            values.append("=");
            values.append(URLEncoder.encode(nameAndValue[1]));
            values.append("&");
        }
        values.setLength(values.length() - 1); // remove last '&'
        return values.toString();
    }

    /**
     * Load the document that is related to
     * the provided String that represents a URL.
     *
     * @param urlString: the String representation of the URL of the document to be loaded.
     */
    public void loadFromUrl(String urlString) {
        try {
            URL newUrl = new URL(urlString);
            changeContentSpan(composeDocument(newUrl));
            setUrlString(urlString);
        } catch (Exception e) {
            changeContentSpan(Document.getErrorDocument());
            setUrlString(urlString);
        }
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
     * @throws java.io.IOException: If one of the parts of the code isn't code that is currently supported.
     */
    public ContentSpan composeDocument(URL url) throws IOException {
        return ContentSpanBuilder.buildContentSpan(url);
    }

    /**
     * Retrieve the contents of a document
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

    /**
     * A method to save a document to a file
     * Inspiration from: https://stackoverflow.com/questions/17440236/getting-java-to-save-a-html-file
     *
     * @param fileName: The name that will be given to the saved file.
     * @throws IOException: if the current document is the Welcome document
     */
    public void saveDocument(String fileName) throws Exception {
        if (contentSpan instanceof TextSpan && ((TextSpan) contentSpan).getText().equals(((TextSpan) getWelcomeDocument()).getText())) {
            // We should only save a document when that document is currently *also* displayed in the DocumentArea
            // Thus, if there's a URL typed in the AddressBar, but the Welcome Document is still displayed in the DocumentArea,
            // no document should be saved.
            throw new Exception("Can't get the source code of a local Document.");
        } else {
            String url = this.getUrlString();
            URL tmpUrl = new URL(url);
            URLConnection con = tmpUrl.openConnection();
            InputStream stream = con.getInputStream();

            BufferedReader buffer = new BufferedReader(new InputStreamReader(stream));
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(fileName + ".html"));

            // First we download the HTML-code to verify whether it's valid
            String line;
            StringBuilder lines = new StringBuilder();
            while((line = buffer.readLine()) != null)
                lines.append(line);
            String document = lines.toString();
            
            System.out.println(document);
            
            // Check whether the downloaded document only consists of HTML-code
            // that our Browsr can parse.
            BrowsrDocumentValidator.assertIsValidBrowsrDocument(document);

            // Only if it is, we save the document to a file
            outputWriter.write(document);

            // Write out final remaining bytes that are still in buffer
            outputWriter.flush();
            outputWriter.close();
            buffer.close();
        }
    }
}
