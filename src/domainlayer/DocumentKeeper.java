package domainlayer;

import userinterface.Pane;

import java.util.ArrayList;
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

    /**
     * Generate and add a new {@link Document} to this {@code DocumentsKeeper}.
     *
     * @return id: the id of the newly added {@link Document}.
     */
    public int addPaneDocument() {
        Document doc = new Document();
        panesToDocuments.put(++documentCounter, doc);
        return documentCounter;
    }
//
//
//    /**
//     * Generate and add a new {@link Document} to this {@code DocumentsKeeper}.
//     * The newly created {@code Document} has the same {@link ContentSpan} loaded
//     * as that {@code Document} object associated to the given id.
//     *
//     * @param siblingId: the id of the {@link Document} which the newly added {@code Document}
//     * will be based on.
//     * @return id: the id of the newly created {@code Document}.
//     */
//    public int addPaneDocument(int siblingId) {
//        // Create a new Document based on that of the sibling
//        Document doc = new Document();
//        doc.loadFromUrl(panesToDocuments.get(siblingId).getUrlString());
//
//        panesToDocuments.put(++documentCounter, doc);
//        System.out.println(panesToDocuments.toString());
//        return documentCounter;
//    }

    /**
     * Get the {@link ContentSpan} object associated to
     * the given {@code id}.
     *
     * @param id: The id of which the linked {@link ContentSpan} will be returned.
     * @return contentSpan: the {@code ContentSpan} associated to the given {@code id}.
     */
    public ContentSpan getPaneContentSpan(int id) {
        return panesToDocuments.get(id).getContentSpan();
    }

//    /**
//     * Get the number of {@link Document} objects that is
//     * currently added to this {@code DocumentKeeper}.
//     *
//     * @return documentCounter: the number of {@link Document} objects that is
//     *      currently added to this {@code DocumentKeeper}.
//     */
//    public int getDocumentCounter() {
//        return documentCounter;
//    }

    /**
     * Get the {@link Document} object associated to
     * the given {@code id}.
     *
     * @param id: The id of which the linked {@link Document} will be returned.
     * @return document: the {@code Document} associated to the given {@code id}.
     */
    public Document getDocument(int id) {
        return panesToDocuments.get(id);
    }

    /**
     * Similar to {@link DocumentKeeper#getDocument(int)} but returns
     * the currently selected {@code Document}.
     *
     * @return currentDocument: the currently selected Document.
     */
    public Document getCurrentDocument() {
        return panesToDocuments.get(currentDocumentId);
    }

    /**
     * Set the {@code id} of the {@link Document} corresponding
     * to the currently selected UI side pane.
     *
     * @param currentDocumentId: The id to be set for the currently selected document.
     */
    public void setCurrentDocumentId(int currentDocumentId) {
        this.currentDocumentId = currentDocumentId;
    }

    /**
     * Method to save a document to a file.
     *
     * @param fileName: the name to be given to the document to be saved.
     */
    public void saveDocument(String fileName) {
        if (fileName.equals(""))
            throw new IllegalArgumentException("Can't save page when no page is loaded");
        try {
            getCurrentDocument().saveDocument(fileName);
        } catch (Exception e) {
            System.out.println("Can't save this document!");
        }
    }

    /**
     * Load a {@link Document} from a given URL
     * in the pane with the given {@code id}.
     *
     * @param id: The id of the {@code Document} that should be updated with the given URL.
     * @param urlString: The URL of the page that should be loaded.
     */
    public void loadFromUrl(int id, String urlString) {
        panesToDocuments.get(id).loadFromUrl(urlString);
    }

    /**
     * Load the document using the given arguments associated with the form.
     *
     * @param id: The {@code id} of the {@link Document} whose {@link userinterface.UIForm}
     *          should be copied.
     * @param action The action associated with the form.
     * @param values An arraylist with the name-value pairs separated by "=".
     */
    public void loadDocumentFromForm(int id, String action, ArrayList<String> values) {
        Document doc = getDocument(id);
        doc.loadFromForm(action, values);
    }

    /**
     * Add a {@link DocumentListener} to the {@code Document} specified by the given {@code id}.
     *
     * @param id: the {@code id} corresponding to the {@code Document} to which the given listener should be added.
     * @param documentListener: The {@link DocumentListener} that should be added.
     */
    public void addDocumentListener(int id, DocumentListener documentListener) {
        panesToDocuments.get(id).addDocumentListener(documentListener);
    }

    /**
     * Similar to {@link DocumentKeeper#addDocumentListener(int, DocumentListener)}, but
     * load contents based on the {@code Document} corresponding to the supplied {@code copyId}.
     *
     * @param id: the {@code id} corresponding to the {@code Document} to which the given listener should be added.
     * @param documentListener: The {@link DocumentListener} that should be added.
     * @param copyId: The id of the {@code Document} on which this listener addition is based.
     */
    public void addDocumentListener(int id, DocumentListener documentListener, int copyId) {
        Document copyDoc = getDocument(copyId);
        Document newDoc = getDocument(id);
        newDoc.setCopyInfo(copyDoc.getUrlString(), copyDoc.isWelcomeDocumentLoaded());
        addDocumentListener(id, documentListener);
    }

    /**
     * Get the {@code id} of the currently loaded {@code Document}.
     *
     * @return id: the {@code id} of the currently loaded {@code Document}.
     */
    public int getCurrentDocumentId() {
        return currentDocumentId;
    }

    /**
     * Set the contents of the {@link Document} corresponding to
     * the given id to the contents of the {@link Document#getWelcomeDocument()}.
     *
     * @param id: the id of the {@code Document} whose contents should be set to the WelcomeDocument.
     */
    public void setWelcomeDocument(int id) {
        panesToDocuments.get(id).changeContentSpan(Document.getWelcomeDocument());
    }

    /**
     * A {@link HashMap} that maps {@code id}s (in the form of
     * {@link Integer}s) onto {@link Document} objects.
     */
    private final HashMap<Integer, Document> panesToDocuments;

    /**
     * A variable that keeps track of the
     * number of {@link Document}s in the
     * {@link DocumentKeeper#panesToDocuments}
     * mapping.
     */
    private int documentCounter = -1;

    /**
     * The Id of the {@link Pane} that is currently selected.
     */
    private int currentDocumentId;

}

