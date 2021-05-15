package domainlayer;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
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
    }

    public int addPaneDocument() {
        documentCounter++;
        try {
            this.panesToDocuments.put(documentCounter, document.composeDocument(new URL(document.getUrlString())));
        } catch(IOException e) {
            this.panesToDocuments.put(documentCounter, Document.getErrorDocument());
        }
        System.out.println(panesToDocuments.toString());
        return documentCounter;
    }

    public int removePaneDocument(int id) {
        this.panesToDocuments.remove(id);
        return --documentCounter;
    }

    public ContentSpan getPaneContentSpan(int paneName) {
        return this.panesToDocuments.get(paneName);
    }

    public int getDocumentCounter() {
        return documentCounter;
    }

    public Document getDocument() {
        return document;
    }

    private final Document document;

    private final HashMap<Integer, ContentSpan> panesToDocuments;

    private int documentCounter = -1;
}

