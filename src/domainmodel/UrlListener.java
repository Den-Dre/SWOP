package domainmodel;

import java.net.URL;

/**
 * Interface for UrlListeners
 */
public interface UrlListener extends DocumentListener {

    /**
     * Define what the class that implements this {@link UrlListener}
     * Interface should do when the contents of the {@code AddressBar} change.
     */
    void URLChanged(URL url);
}
