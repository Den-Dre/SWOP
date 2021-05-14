package domainlayer;

import java.util.HashMap;

/**
 * A class that keeps track of the
 * documents associated to the windows
 * in the UI-layer, as well as the
 * ContentFrame that currently has focus.
 */
public class DocumentKeeper {
    /**
     * Initialise this {@code DocumentKeeper}
     * with an empty hashmap that maps the
     * UI-layer objects to documents.
     */
    public DocumentKeeper(Document document) {
        this.document = document;
        this.panesToDocuments = new HashMap<>();
        this.currentlyFocusedPane = null;
    }

    public void addPaneDocument(String paneName) {
        this.panesToDocuments.put(paneName, document.getContentSpan());
        this.currentlyFocusedPane = paneName;
    }

    public void removePaneDocument(String paneName) {
        this.panesToDocuments.remove(paneName, document.getContentSpan());
    }

    public ContentSpan getPaneContentSpan(String paneName) {
        return this.panesToDocuments.get(paneName);
    }

    public Document getDocument() {
        return document;
    }

    private final Document document;

    private final HashMap<String, ContentSpan> panesToDocuments;

    private String currentlyFocusedPane;
}

