package domainmodel;

import java.io.*;
import java.net.URL;

public class UIController {

    private Document document;

    public UIController(URL url) {
        this.document = new Document(url);
    }

    protected Serializable loadDocument(String document) throws IOException {
        return this.document.getSerializedContentSpan(document);
    }

    protected Document getContents() throws IOException, ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream("documentStream.txt"));
        Document doc = (Document) in.readObject();
        in.close();
        return doc;
    }
}
