package domainmodel;

/**
 * An interface for DocumentListener
 */
public interface DocumentListener {

    /**
     * Define what the class that implements this {@link DocumentListener}
     * Interface should do when the contents of the {@link Document} change.
     */
    void contentChanged();

    //void urlChanged();
}
