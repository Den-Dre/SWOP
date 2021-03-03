package domainmodel;

import java.io.Serializable;
import java.net.URL;

public class UIController {

    private Document document;

    public UIController() {
        this.document = new Document();
    }

    protected Serializable loadDocument(URL url) {
        return this.document.composeDocument(url);
    }

    protected Serializable getContents() {
        return this.document.serializeContentSpan();
    }
}
