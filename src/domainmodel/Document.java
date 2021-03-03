package domainmodel;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Document {
    private String urlString;
    private List<DocumentListener> urlListeners = new ArrayList<>();
    private List<DocumentListener> documentListeners = new ArrayList<>();

    protected void addURLListener(URL u) {
        this.urlListeners.add(u);
    }

    protected void removeURLListener(String u) {
        this.urlListeners.remove(u);
    }

    protected void addDocumentListener(DocumentListener d) {
        this.documentListeners.add(d);
    }

    protected void removeDocumentListener(DocumentListener d) {
        this.documentListeners.remove(d);
    }

    public Serializable composeDocument(URL url) {
        // TODO: load document from lexer and validator
        for (DocumentListener dl : this.documentListeners) {
            dl.contentChanged();
        }

        for (DocumentListener u: urlListeners) {
            u.urlChanged();
        }
        return null;
    }

    public Serializable serializeContentSpan() {
        return null;
    }

}
