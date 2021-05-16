package domainlayer;

import userinterface.Pane;

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
    public DocumentKeeper() {
        this.panesToDocuments = new HashMap<>();
    }

    public int addPaneDocument() {
        Document doc = new Document();
        panesToDocuments.put(++documentCounter, doc);
        System.out.println(panesToDocuments.toString());
        return documentCounter;
    }

    public int addPaneDocumentBasedOn(int siblingId) {
        // Create a new Document based on that of the sibling
        Document doc = new Document();
        doc.loadFromUrl(panesToDocuments.get(siblingId).getUrlString());

        panesToDocuments.put(++documentCounter, doc);
        System.out.println(panesToDocuments.toString());
        return documentCounter;
    }

    public int removePaneDocument(int id) {
        panesToDocuments.remove(id);
        return --documentCounter;
    }

    public ContentSpan getPaneContentSpan(int id) {
        return panesToDocuments.get(id).getContentSpan();
    }

    public int getDocumentCounter() {
        return documentCounter;
    }

    public Document getDocument(int id) {
        return panesToDocuments.get(id);
    }

    public Document getCurrentDocument() {
        return panesToDocuments.get(currentDocumentId);
    }

    public void setCurrentDocumentId(int currentDocumentId) {
        this.currentDocumentId = currentDocumentId;
        System.out.println("[Nb. of panes: " + panesToDocuments.size() + "]");
    }

    public int getCurrentDocumentId() {
        return currentDocumentId;
    }

    private final HashMap<Integer, Document> panesToDocuments;

    private int documentCounter = -1;

    /**
     * The Id of the {@link Pane} that is currently selected.
     */
    private int currentDocumentId;
}

