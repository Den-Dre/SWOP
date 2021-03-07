package domainmodel;

/**
 * Interface for UrlListeners
 */
public interface UrlListener extends DocumentListener {

    /**
     * Let the object know that the Url has been changed
     */
    void URLChanged();
}
