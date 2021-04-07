package domainlayer;

/**
 * An interface for DocumentListener
 *
 * This interface should be implemented by
 * all classes that should be notified when
 * the content of the linked {@link Document}
 * changes.
 */
public interface DocumentListener {

    /**
     * Define what the class that implements
     * this {@link DocumentListener} Interface
     * should do when the contents of the
     * linked {@link Document} change.
     */
    void contentChanged();
}
